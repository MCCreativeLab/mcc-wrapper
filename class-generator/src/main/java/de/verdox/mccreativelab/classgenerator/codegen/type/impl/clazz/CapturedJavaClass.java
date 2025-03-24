package de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz;

import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedTypeVariable;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CapturedJavaClass extends ClassType<Class<?>> {
    public CapturedJavaClass(Class<?> handle) {
        super(Objects.requireNonNull(handle));
    }

    @Override
    public String getClassName() {
        return handle.getSimpleName();
    }

    @Override
    public String getPackageName() {
        return handle.getPackageName();
    }

    @Override
    @Nullable
    public CapturedParameterizedType getDeclaringClass() {
        if (handle.getDeclaringClass() != null) {
            return CapturedParameterizedType.from(handle.getDeclaringClass());
        }
        return null;
    }

    @Override
    @Nullable
    public CapturedParameterizedType getSuperClass() {
        if (handle.getGenericSuperclass() != null) {
            return CapturedParameterizedType.from(handle.getGenericSuperclass());
        }
        return null;
    }

    @Override
    public List<CapturedTypeVariable> getTypeVariables() {
        return Arrays.stream(handle.getTypeParameters()).map(CapturedTypeVariable::from).toList();
    }

    @Override
    public List<CapturedParameterizedType> getInterfaces() {
        return Arrays.stream(handle.getGenericInterfaces()).map(CapturedParameterizedType::from).toList();
    }

    @Override
    public boolean isSynthetic() {
        return handle.isSynthetic();
    }

    @Override
    public boolean isPrimitive() {
        return handle.isPrimitive();
    }

    @Override
    public boolean isEnum() {
        return handle.isEnum();
    }

    @Override
    public boolean isRecord() {
        return handle.isRecord();
    }

    @Override
    public boolean isAnonymous() {
        return handle.isAnonymousClass();
    }

    @Override
    public boolean isArray() {
        return handle.isArray();
    }

    @Override
    public boolean isInterface() {
        return handle.isInterface();
    }

    @Override
    protected ClassType<Class<?>> createImmutableClone() {
        return new CapturedJavaClass(handle);
    }
}
