package de.verdox.mccreativelab.classgenerator.codegen.type.impl;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.vserializer.SerializableField;
import de.verdox.vserializer.generic.Serializer;
import de.verdox.vserializer.generic.SerializerBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Represents a statically typed class type definition
 */
public class CapturedClassType extends CapturedType<CapturedClassType, Class<?>> {
    public static CapturedClassType create(String className, String packageName) {
        return new CapturedClassType().withClassName(className).withPackageName(packageName);
    }
/*
    public static final Serializer<CapturedClassType> SERIALIZER = SerializerBuilder.create("class", CapturedClassType.class)
            .constructor(
                    new SerializableField<>("className", Serializer.Primitive.STRING, CapturedClassType::getClassName),
                    new SerializableField<>("packageName", Serializer.Primitive.STRING, CapturedClassType::getPackageName),
                    new SerializableField<>("declaringClass", CapturedType.SERIALIZER, CapturedClassType::getDeclaringClass),
                    new SerializableField<>("superClass", CapturedType.SERIALIZER, CapturedClassType::getSuperClass),
                    new SerializableField<>("typeVariables", Serializer.Collection.create(CapturedTypeVariable.SERIALIZER, LinkedList::new), CapturedClassType::getTypeVariables),
                    new SerializableField<>("interfaces", Serializer.Collection.create(CapturedType.SERIALIZER, LinkedList::new), CapturedClassType::getInterfaces),
                    (className, packageName, declaringClass, superClass, capturedTypeVariables, interfaces) -> new CapturedClassType(superClass, interfaces, (CapturedClassType) declaringClass, className, packageName, capturedTypeVariables)
            )
            .build();*/

    private CapturedClassType(@Nullable CapturedType<?, ?> superClass, List<CapturedType<?, ?>> interfaces, @Nullable CapturedClassType declaringClass, String className, String packageName, List<CapturedTypeVariable> typeVariables) {
        super(null);
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.declaringClass = declaringClass;
        this.className = className;
        this.packageName = packageName;
        this.typeVariables = typeVariables;
    }

    @Override
    protected CapturedClassType copy(boolean mutable) {
        CapturedClassType other = new CapturedClassType();
        other.className = this.className;
        other.packageName = this.packageName;
        other.declaringClass = copyType(classType -> classType.declaringClass, mutable);
        other.superClass = copyType(classType -> classType.superClass, mutable);
        other.typeVariables = copyCollection(classType -> classType.typeVariables, mutable);
        other.interfaces = copyCollection(classType -> classType.interfaces, mutable);
        return other;
    }

    @Nullable
    private CapturedType<?, ?> superClass;
    private List<CapturedType<?, ?>> interfaces;
    @Nullable
    private CapturedClassType declaringClass;
    private String className;
    private String packageName;
    private List<CapturedTypeVariable> typeVariables;

    protected CapturedClassType(Class<?> type, @Nullable CapturedClassType declaringClass) {
        super(type);
        this.declaringClass = declaringClass;
        this.className = type.getSimpleName();
        this.packageName = type.getPackageName();
        this.typeVariables = Arrays.stream(type.getTypeParameters()).map(CapturedTypeVariable::new).toList();
        if (type.getGenericSuperclass() != null) {
            this.superClass = from(type.getGenericSuperclass());
        }
        this.interfaces = capture(type.getGenericInterfaces());
    }

    protected CapturedClassType(Class<?> type) {
        this(type, type.getDeclaringClass() != null ? new CapturedClassType(type.getDeclaringClass()) : null);
    }

    protected CapturedClassType() {
        super(null);
    }

    public String getClassName() {
        return className;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<CapturedTypeVariable> getTypeVariables() {
        return typeVariables;
    }

    public @Nullable CapturedType<?, ?> getSuperClass() {
        return superClass;
    }

    public @Nullable CapturedClassType getDeclaringClass() {
        return declaringClass;
    }

    public List<CapturedType<?, ?>> getInterfaces() {
        return interfaces;
    }

    public CapturedClassType withClassName(String name) {
        return mutableCopy().edit(type -> type.className = name);
    }

    public CapturedClassType withPackageName(String name) {
        return mutableCopy().edit(type -> type.packageName = name);
    }

    public CapturedClassType withNoTypeVariables() {
        return mutableCopy().edit(type -> type.typeVariables.clear());
    }

    public CapturedClassType withTypeVariable(int index, CapturedTypeVariable capturedTypeVariable) {
        return mutableCopy().edit(type -> type.typeVariables.set(index, capturedTypeVariable));
    }

    public CapturedClassType withTypeVariable(CapturedTypeVariable capturedTypeVariable) {
        return mutableCopy().edit(type -> type.typeVariables.add(capturedTypeVariable));
    }

    public CapturedClassType withNoInterfaces() {
        return mutableCopy().edit(type -> type.interfaces.clear());
    }

    public CapturedClassType withInterface(int index, CapturedClassType classType) {
        return mutableCopy().edit(type -> type.interfaces.set(index, classType));
    }

    public CapturedClassType withInterface(CapturedClassType classType) {
        return mutableCopy().edit(type -> type.interfaces.add(classType));
    }

    public CapturedClassType withSuperClass(CapturedClassType capturedTypeVariable) {
        return mutableCopy().edit(type -> type.superClass = capturedTypeVariable);
    }

    public boolean isAssignableFrom(CapturedClassType classType) {
        if (classType.equals(this)) {
            return true;
        }
        if (classType.superClass instanceof CapturedClassType superClassType && superClassType.isAssignableFrom(classType)) {
            return true;
        }
        for (CapturedType<?, ?> anInterface : classType.interfaces) {
            if (anInterface instanceof CapturedClassType interfaceClass && interfaceClass.isAssignableFrom(classType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void collectTypesOnImport(Set<CapturedClassType> imports) {
        imports.add(Objects.requireNonNullElse(this.declaringClass, this));
        for (CapturedTypeVariable typeVariable : typeVariables) {
            typeVariable.collectTypesOnImport(imports);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapturedClassType that = (CapturedClassType) o;
        return Objects.equals(superClass, that.superClass) && Objects.equals(interfaces, that.interfaces) && Objects.equals(declaringClass, that.declaringClass) && Objects.equals(className, that.className) && Objects.equals(packageName, that.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(superClass, interfaces, declaringClass, className, packageName);
    }

    @Override
    public void write(CodeLineBuilder codeLineBuilder) {
        writeWithoutGenerics(codeLineBuilder);
        if (!typeVariables.isEmpty()) {
            codeLineBuilder.append("<");
            writeCollectionOfTypes(typeVariables, codeLineBuilder);
            codeLineBuilder.append(">");
        }
    }

    public void writeWithoutGenerics(CodeLineBuilder codeLineBuilder) {
        if (declaringClass != null) {
            codeLineBuilder.append(declaringClass.className).append(".");
        }
        codeLineBuilder.append(className);
    }
}
