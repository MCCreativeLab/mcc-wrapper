package de.verdox.mccreativelab.classgenerator.codegen;

import de.verdox.mccreativelab.classgenerator.codegen.expressions.*;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks.*;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedTypeVariable;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.MutableClassType;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.tools.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassBuilder extends MutableClassType implements JavaDocElement<ClassBuilder> {
    public static final Logger LOGGER = Logger.getLogger(ClassBuilder.class.getName());
    private String classModifier;
    private ClassHeader classHeader = ClassHeader.CLASS;
    private String classSuffix;
    private final List<Method> methods = new LinkedList<>();
    private final List<Constructor> constructors = new LinkedList<>();
    private final Set<Import> imports = new HashSet<>();
    private final List<Field> fields = new LinkedList<>();
    private final List<EnumEntry> enumEntries = new LinkedList<>();
    private final Set<ClassBuilder> includedClasses = new HashSet<>();
    private final Map<String, CapturedType<?, ?>> uniqueNames = new HashMap<>();
    private boolean isInnerClass;
    private ClassBuilder parent;
    private int depth = 0;
    private JavaDocExpression javaDoc;

    public ClassBuilder() {
        super(null, null);
    }

    public ClassBuilder withPackage(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public ClassType<?> asCapturedClassType() {
        return this;
    }

    public void markAsInnerClass(ClassBuilder parent) {
        if (this.equals(parent)) {
            throw new IllegalArgumentException("Classes cannot be their declaring parent");
        }
        Objects.requireNonNull(getClassName(), "Class has no name yet.");
        Objects.requireNonNull(getPackageName(), "Class has no package yet.");
        this.parent = parent;
        isInnerClass = true;
        this.depth = parent.depth + 1;
        this.declaringClass = CapturedParameterizedType.from(parent);
    }

    @Deprecated
    public ClassBuilder withField(String modifier, CapturedType<?, ?> type, String fieldName, CodeExpression initValue) {
        Field field = new Field(modifier, fieldName, type).withInitValue(initValue);
        return withField(field);
    }

    @Deprecated
    public ClassBuilder withField(String modifier, CapturedType<?, ?> type, String fieldName, String initValue) {
        Field field = new Field(modifier, fieldName, type).withInitValue(initValue);
        return withField(field);
    }

    public ClassBuilder withField(Field field) {
        LOGGER.log(Level.FINER, "Including field " + field);
        fields.add(field);

        includeImport(field.getType());
        return this;
    }

    public ClassBuilder withClassGeneric(CapturedTypeVariable genericType) {
        withTypeVariable(genericType);
        return this;
    }

    public ClassBuilder withHeader(String modifier, ClassHeader classHeader, String className, String suffix) {
        if (classHeader.equals(ClassHeader.RECORD))
            throw new IllegalArgumentException("For record type use the withRecordHeader() function.");
        this.classModifier = modifier;
        this.classHeader = classHeader;
        this.className = className;
        this.classSuffix = suffix;
        return this;
    }

    public ClassBuilder withRecordHeader(String modifier, String className, String suffix, Parameter... parameters) {
        this.classModifier = modifier;
        this.classHeader = ClassHeader.RECORD;
        this.className = className;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            stringBuilder.append(parameter.getType());
            if (i < parameters.length - 1)
                stringBuilder.append(", ");
        }
        stringBuilder.append(")");
        stringBuilder.append(" ").append(suffix);
        this.classSuffix = stringBuilder.toString();
        return this;
    }

    public ClassBuilder implementsClasses(CapturedParameterizedType... classTypes) {
        withNoInterfaces();
        for (CapturedParameterizedType classType : classTypes) {
            includeImport(classType);
            withInterface(classType.getRawType());
        }
        return this;
    }

    public ClassBuilder withSuperType(CapturedParameterizedType classType) {
        includeImport(classType);
        withSuperClass(classType);
        return this;
    }

    public ClassBuilder withEnumEntry(String name, CodeExpression... parameterValues) {
        enumEntries.add(new EnumEntry(name, parameterValues));
        return this;
    }

    public ClassBuilder withMethod(Method method) {
        LOGGER.log(Level.FINER, "Including method " + method);
        methods.add(method);

        if (method.type() != null) {
            includeImport(method.type());
        }
        for (Parameter parameter : method.parameters()) {
            includeImport(parameter.getType());
        }
        return this;
    }

    public ClassBuilder withConstructor(Constructor constructor) {
        LOGGER.log(Level.FINER, "Including constructor constructor " + constructor);
        constructor.type(CapturedParameterizedType.from(asCapturedClassType()));
        constructors.add(constructor);

        for (Parameter parameter : constructor.parameters())
            includeImport(parameter.getType());
        return this;
    }

    public ClassBuilder includeInnerClass(ClassBuilder classBuilder) {
        if (this.equals(classBuilder)) {
            throw new IllegalArgumentException("A class builder cannot be its own declaring parent");
        }
        classBuilder.markAsInnerClass(this);
        includedClasses.add(classBuilder);
        return this;
    }

    public ClassBuilder includeImport(CapturedType<?, ?> capturedType) {
        Objects.requireNonNull(capturedType);
        if (capturedType.getRawType().getPackageName() == null || capturedType.getRawType().getClassName() == null) {
            return this;
        }

        if (this.isInnerClass && this.parent != null) {
            this.parent.includeImport(capturedType);
            return this;
        }

        if (capturedType.getRawType().getPackageName().equals(packageName)) {
            return this;
        }

        if (capturedType.getRawType().getDeclaringClass() != null) {
            return includeImport(capturedType.getRawType().getDeclaringClass());
        }


        //LOGGER.log(Level.INFO, "Including import " + capturedType.getRawType().getPackageName() + "." + capturedType.getRawType().getFullClassName() + " (" + capturedType.getClass() + ")");

        Set<ClassType<?>> typesToImport = new HashSet<>();
        capturedType.collectTypesOnImport(typesToImport);

        //TODO: If a class with the same name but different package is imported while there already exists a class with that name -> We need to notify the Classbuilder to write the types with package in front
        for (ClassType<?> classType : typesToImport) {
            if (classType.getDeclaringClass() != null) {
                includeImport(classType.getDeclaringClass());
                continue;
            }
            if (imports.contains(new Import(classType))) {
                continue;
            }
            imports.add(new Import(classType));
            LOGGER.log(Level.FINE, "Including import: " + classType.getRawType().getPackageName() + "." + classType.getRawType().getFullClassName() + " (" + classType.getClassName() + ", " + classType.hashCode() + ")");
        }
        return this;
    }

    public ClassBuilder includeImport(Type type) {
        return includeImport(CapturedType.from(type));
    }

    public void buildClassFileString(StringBuilder code) {
        if (this.className == null || this.classHeader == null)
            return;
        // We build the class file in reverse because some of the write calls create new imports.
        // Since the imports are at the top of the file they are built last.
        StringBuilder includedClassesBuilder = new StringBuilder();
        for (ClassBuilder includedClass : includedClasses) {
            includedClass.buildClassFileString(includedClassesBuilder);
        }

        CodeLineBuilder enumEntryBuilder = new CodeLineBuilder(this, depth);
        for (EnumEntry enumEntry : enumEntries) {
            enumEntry.write(enumEntryBuilder);
        }
        enumEntryBuilder.appendAndNewLine(";");

        CodeLineBuilder constructorBuilder = new CodeLineBuilder(this, depth);
        for (Constructor constructor : constructors) {
            constructor.write(constructorBuilder);
        }

        CodeLineBuilder methodsBuilder = new CodeLineBuilder(this, depth);
        for (Method method : methods) {
            method.write(methodsBuilder);
        }

        CodeLineBuilder fieldsBuilder = new CodeLineBuilder(this, depth);
        for (Field field : fields)
            field.write(fieldsBuilder);

        CodeLineBuilder classTitleBuilder = buildClassTitle();

        CodeLineBuilder importBuilder = new CodeLineBuilder(this, depth);
        if (!isInnerClass && parent == null) {
            for (Import anImport : imports) {
                if (anImport.classDescription() == null) {
                    continue;
                }
                if (anImport.classDescription().isPrimitive()) {
                    continue;
                }
                if (anImport.classDescription().equals(this.asCapturedClassType())) {
                    continue;
                }
                if (anImport.classDescription().getDeclaringClass() != null) {
                    continue;
                }
                anImport.write(importBuilder);
            }
        }

        CodeLineBuilder packageBuilder = new CodeLineBuilder(this, depth);
        if (packageName != null && !isInnerClass)
            packageBuilder.append("package ").append(packageName).append(";\n");

        code.append(packageBuilder);
        code.append(importBuilder);
        CodeLineBuilder javaDocBuilder = new CodeLineBuilder(this, depth);
        if (javaDoc != null) {
            code.append("\n");
            javaDocBuilder.append(javaDoc);
        }

        code.append(javaDocBuilder);

        code.append("\n");
        code.append(classTitleBuilder);


        if (classHeader.equals(ClassHeader.ENUM)) {
            code.append(enumEntryBuilder);
        }

        code.append(fieldsBuilder);
        if (!classHeader.equals(ClassHeader.INTERFACE)) {
            code.append(constructorBuilder);
        }
        code.append(methodsBuilder);
        code.append(includedClassesBuilder);
        CodeLineBuilder lastLine = new CodeLineBuilder(this, depth);
        lastLine.appendAndNewLine("}");
        code.append(lastLine);
    }

    private @NotNull CodeLineBuilder buildClassTitle() {
        CodeLineBuilder classTitleBuilder = new CodeLineBuilder(this, depth);
        if (classModifier != null)
            classTitleBuilder.append(classModifier);
        classTitleBuilder.append(" ");
        classTitleBuilder.append(classHeader.getHeader());
        classTitleBuilder.append(" ");
        classTitleBuilder.append(className);


        classTitleBuilder.append(" ");
        if (!getTypeVariables().isEmpty()) {
            classTitleBuilder.append("<");
            for (CapturedTypeVariable typeVariable : getTypeVariables()) {
                typeVariable.write(classTitleBuilder);
            }
            classTitleBuilder.append(">");
            classTitleBuilder.append(" ");
        }

        if (getSuperClass() != null && ClassHeader.CLASS.equals(classHeader)) {
            classTitleBuilder.append("extends ");
            classTitleBuilder.append(getSuperClass());
            classTitleBuilder.append(" ");
        }

        if (!getInterfaces().isEmpty()) {
            if (ClassHeader.INTERFACE.equals(classHeader)) {
                classTitleBuilder.append("extends ");
            } else {
                classTitleBuilder.append("implements ");
            }
            for (int i = 0; i < getInterfaces().size(); i++) {
                CapturedParameterizedType implementedType = getInterfaces().get(i);
                classTitleBuilder.append(implementedType);
                if (i < getInterfaces().size() - 1)
                    classTitleBuilder.append(", ");
            }
            classTitleBuilder.append(" ");
        }
        if (classSuffix != null)
            classTitleBuilder.append(classSuffix);
        classTitleBuilder.append(" {");
        classTitleBuilder.appendAndNewLine("");
        classTitleBuilder.appendAndNewLine("");
        return classTitleBuilder;
    }

    public void writeClassFile(String fileName) throws IOException {
        writeClassFile(new File(fileName));
    }

    public void writeClassFile(File sourceDir) throws IOException {
        Objects.requireNonNull(className, "No class name specified");
        Objects.requireNonNull(packageName, "No package name specified for " + className);
        File file = new File(sourceDir.getPath() + "/" + packageName.replace(".", "/") + "/" + className + ".java");
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            StringBuilder code = new StringBuilder();
            buildClassFileString(code);
            writer.write(code.toString());
/*            boolean couldCompile = compileJavaFile(file.getAbsolutePath());
            LOGGER.log(Level.FINER, "Created java file " + file);
            if (!couldCompile) {
                LOGGER.warning("Created a java file that does not compile: " + file);
            }*/
        }
    }

    @Override
    protected ClassType<Void> copy(boolean mutable) {
        ClassBuilder classBuilder = new ClassBuilder();
        classBuilder.packageName = this.packageName;
        classBuilder.classModifier = this.classModifier;
        classBuilder.classHeader = this.classHeader;
        classBuilder.className = this.className;
        classBuilder.classSuffix = this.classSuffix;
        classBuilder.methods.addAll(this.methods);
        classBuilder.constructors.addAll(this.constructors);
        classBuilder.fields.addAll(this.fields);
        classBuilder.enumEntries.addAll(this.enumEntries);
        classBuilder.includedClasses.addAll(this.includedClasses);
        classBuilder.uniqueNames.putAll(this.uniqueNames);
        classBuilder.isInnerClass = this.isInnerClass;
        classBuilder.parent = this.parent;
        classBuilder.depth = this.depth;
        classBuilder.javaDoc = this.javaDoc;

        classBuilder.declaringClass = copyType(ClassType::getDeclaringClass, mutable);
        classBuilder.superClass = copyType(ClassType::getSuperClass, mutable);
        classBuilder.typeVariables = copyCollection(ClassType::getTypeVariables, mutable);
        classBuilder.interfaces = copyCollection(ClassType::getInterfaces, mutable);

        classBuilder.isSynthetic = this.isSynthetic;
        classBuilder.isPrimitive = this.isPrimitive;
        classBuilder.isEnum = this.isEnum;
        classBuilder.isRecord = this.isRecord;
        classBuilder.isAnonymous = this.isAnonymous;
        classBuilder.isArray = this.isArray;
        classBuilder.isInterface = this.isInterface;
        return this;
    }

    /**
     * @param type Checks if this types name was written anywhere in the class builder. If no it returns true. Else the class builder wants it to be written with full package name
     * @return true if the type was already written in the class, and it's the same type.
     */
    public boolean canWriteSimpleName(ClassType<?> type) {
        boolean canWriteSimple = uniqueNames.containsKey(type.getFullClassName()) && uniqueNames.get(type.getFullClassName()).getRawType().getPackageName().equals(type.getPackageName());
        if (canWriteSimple) {
            //imports.remove(new Import(type));
        }
        return canWriteSimple;
    }

    public void logUniqueName(ClassType<?> type) {
        if (!uniqueNames.containsKey(type.getFullClassName())) {
            uniqueNames.put(type.getFullClassName(), type);
        }
    }

    @Override
    public ClassBuilder javaDoc(JavaDocExpression javaDoc) {
        this.javaDoc = javaDoc;
        return this;
    }

    @Override
    public JavaDocExpression javaDoc() {
        return javaDoc;
    }


    public enum ClassHeader {
        INTERFACE("interface"),
        CLASS("class"),
        RECORD("record"),
        ENUM("enum"),
        ;
        private final String header;

        ClassHeader(String header) {
            this.header = header;
        }

        public String getHeader() {
            return header;
        }
    }

    public String getClassModifier() {
        return classModifier;
    }

    public ClassHeader getClassHeader() {
        return classHeader;
    }

    public String getClassSuffix() {
        return classSuffix;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public List<Constructor> getConstructors() {
        return constructors;
    }

    public Set<Import> getImports() {
        return Set.copyOf(imports);
    }

    public List<Field> getFields() {
        return List.copyOf(fields);
    }

    public List<EnumEntry> getEnumEntries() {
        return List.copyOf(enumEntries);
    }

    public Set<ClassBuilder> getIncludedClasses() {
        return Set.copyOf(includedClasses);
    }

    public boolean isInnerClass() {
        return isInnerClass;
    }

    @Override
    public boolean isEnum() {
        return getClassHeader().equals(ClassHeader.ENUM);
    }

    @Override
    public boolean isInterface() {
        return getClassHeader().equals(ClassHeader.INTERFACE);
    }

    @Override
    public boolean isRecord() {
        return getClassHeader().equals(ClassHeader.RECORD);
    }

    public ClassBuilder getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassBuilder that = (ClassBuilder) o;
        return Objects.equals(packageName, that.packageName) && Objects.equals(classModifier, that.classModifier) && Objects.equals(parent, that.parent) && classHeader == that.classHeader && Objects.equals(className, that.className) && Objects.equals(classSuffix, that.classSuffix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName, classModifier, classHeader, className, classSuffix, parent);
    }

    private static boolean compileJavaFile(String filePath) {
        // Erstellen eines JavaCompiler-Objekts
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            System.out.println("Kein Java-Compiler gefunden. Stellen Sie sicher, dass JDK installiert ist.");
            return false;
        }

        // Standarddiagnose-Sammler für Compiler-Ausgabe
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        // Dateimanager für die Eingabedatei
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        try {
            // Datei in ein JavaFileObject konvertieren
            Iterable<? extends JavaFileObject> compilationUnits =
                    fileManager.getJavaFileObjectsFromStrings(java.util.Collections.singletonList(filePath));

            // Kompilation starten
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);

            boolean success = task.call();

            // Diagnosen ausgeben, falls vorhanden
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.out.println(diagnostic.getKind() + ": " + diagnostic.getMessage(null));
            }

            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fileManager.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected ClassType<Void> createImmutableClone() {
        return this;
    }
}
