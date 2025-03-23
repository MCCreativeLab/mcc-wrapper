package de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz;

import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedTypeVariable;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class ClassType<T> extends CapturedType<ClassType<T>, T> {

    public static ClassType<?> from(Class<?> type) {
        return new CapturedJavaClass(type);
    }

    @Deprecated
    public static ClassType<?> from(ClassBuilder classBuilder) {
        return classBuilder;
    }

    public static MutableClassType create(String className, String packageName) {
        return new MutableClassType(className, packageName);
    }

    protected final T handle;

    public ClassType(T handle) {
        this.handle = handle;
    }

    public String getFullClassName() {
        if (getDeclaringClass() != null) {
            return getDeclaringClass().getRawType().getFullClassName() + "." + getClassName();
        }
        return getClassName();
    }

    @Override
    public void write(CodeLineBuilder codeLineBuilder) {
        writeWithoutGenerics(codeLineBuilder);
        if (!getTypeVariables().isEmpty()) {
            codeLineBuilder.append("<");
            writeCollectionOfTypes(getTypeVariables(), codeLineBuilder);
            codeLineBuilder.append(">");
        }
    }

    public void writeWithoutGenerics(CodeLineBuilder codeLineBuilder) {
        if (codeLineBuilder.getClassBuilder() != null) {
            codeLineBuilder.getClassBuilder().logUniqueName(this);
        }
        if (codeLineBuilder.getClassBuilder() != null && !codeLineBuilder.getClassBuilder().canWriteSimpleName(this) && !isPrimitive()) {
            codeLineBuilder.append(getPackageName()).append(".");
        }
        if (getDeclaringClass() != null) {
            codeLineBuilder.append(getDeclaringClass().getRawType().getClassName()).append(".");
        }
        codeLineBuilder.append(getClassName());
    }

    public MutableClassType withClassName(String name) {
        return new MutableClassType(this).withClassName(name);
    }

    public MutableClassType withPackageName(String name) {
        return new MutableClassType(this).withPackageName(name);
    }

    public MutableClassType withNoTypeVariables() {
        return new MutableClassType(this).withNoTypeVariables();
    }

    public MutableClassType withTypeVariables(List<CapturedTypeVariable> typeVariables, boolean clear) {
        return new MutableClassType(this).withTypeVariables(typeVariables, clear);
    }

    public MutableClassType withTypeVariable(int index, CapturedTypeVariable capturedTypeVariable) {
        return new MutableClassType(this).withTypeVariable(index, capturedTypeVariable);
    }

    public MutableClassType withTypeVariable(CapturedTypeVariable capturedTypeVariable) {
        return new MutableClassType(this).withTypeVariable(capturedTypeVariable);
    }

    public MutableClassType withNoInterfaces() {
        return new MutableClassType(this).withNoInterfaces();
    }

    public MutableClassType withInterface(int index, ClassType<?> classType) {
        return new MutableClassType(this).withInterface(index, classType);
    }

    public MutableClassType withInterface(ClassType<?> classType) {
        return new MutableClassType(this).withInterface(classType);
    }

    public MutableClassType withSuperClass(CapturedParameterizedType capturedTypeVariable) {
        return new MutableClassType(this).withSuperClass(capturedTypeVariable);
    }

    public abstract String getClassName();

    public abstract String getPackageName();

    @Nullable
    public abstract CapturedParameterizedType getDeclaringClass();

    @Nullable
    public abstract CapturedParameterizedType getSuperClass();

    public abstract List<CapturedTypeVariable> getTypeVariables();

    public abstract List<CapturedParameterizedType> getInterfaces();

    public abstract boolean isSynthetic();

    public abstract boolean isPrimitive();

    public abstract boolean isEnum();

    public abstract boolean isRecord();

    public abstract boolean isAnonymous();

    public abstract boolean isArray();

    public abstract boolean isInterface();

    @Override
    public ClassType<?> getRawType() {
        return this;
    }

    protected T getHandle() {
        return handle;
    }

    @Override
    protected ClassType copy(boolean mutable) {
        if (mutable) {
            return new MutableClassType(this);
        }
        return createImmutableClone();
    }

    protected abstract ClassType<T> createImmutableClone();

    @Override
    public void collectTypesOnImport(Set<ClassType<?>> imports) {
        if (this.getDeclaringClass() != null) {
            this.getDeclaringClass().collectTypesOnImport(imports);
            return;
        }
        imports.add(this);
        for (CapturedTypeVariable typeVariable : getTypeVariables()) {
            typeVariable.collectTypesOnImport(imports);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassType<?> classType = (ClassType<?>) o;
        return Objects.equals(handle, classType.handle);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(handle);
    }

    public String debugString() {
        return getPackageName() + "." + getFullClassName() + " (" + getClass().getSimpleName() + ", " + hashCode() + ")";
    }
}
