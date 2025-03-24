package de.verdox.mccreativelab.classgenerator.codegen.type.impl;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.CapturedJavaClass;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CapturedParameterizedType extends CapturedType<CapturedParameterizedType, ParameterizedType> {
    public static CapturedParameterizedType from(Class<?> staticClass) {
        return from(new CapturedJavaClass(staticClass));
    }

    public static CapturedParameterizedType from(ClassType<?> staticClass) {
        CapturedParameterizedType capturedParameterizedType = new CapturedParameterizedType();
        capturedParameterizedType.rawType = staticClass;
        return capturedParameterizedType;
    }

    public static CapturedParameterizedType from(ParameterizedType parameterizedType) {
        return new CapturedParameterizedType(parameterizedType);
    }

    public static CapturedParameterizedType from(Type type) {
        if (type instanceof Class<?> clazz)
            return from(clazz);
        else if (type instanceof ParameterizedType parameterizedType)
            return from(parameterizedType);
        else
            throw new IllegalStateException("Cannot convert " + type.getClass() + " to a " + CapturedParameterizedType.class.getSimpleName());
    }

    @Override
    protected CapturedParameterizedType copy(boolean mutable) {
        CapturedParameterizedType other = new CapturedParameterizedType();
        other.owner = copyType(type -> type.owner, mutable);
        other.rawType = Objects.requireNonNull(copyType(type -> type.rawType, mutable));
        other.typeArguments = copyCollection(type -> type.typeArguments, mutable);
        return other;
    }

    @Nullable
    private CapturedType<?, ?> owner;
    private ClassType<?> rawType;
    private List<CapturedType<?, ?>> typeArguments = new LinkedList<>();

    protected CapturedParameterizedType(ParameterizedType type) {
        if (type.getOwnerType() != null) {
            this.owner = from(type.getOwnerType());
        }
        this.rawType = Objects.requireNonNull((ClassType<?>) ClassType.from(type.getRawType()), "rawType of captured parameterized type " + type + " cannot be null");
        CAPTURED_REGISTRY.linkToRealType(type, this);
        this.typeArguments = capture(type.getActualTypeArguments());
    }

    protected CapturedParameterizedType() {
    }

    public @Nullable CapturedType<?, ?> getOwner() {
        return owner;
    }

    @Override
    public ClassType<?> getRawType() {
        return rawType;
    }

    public List<CapturedType<?, ?>> getTypeArguments() {
        return typeArguments;
    }

    @Override
    public void collectTypesOnImport(Set<ClassType<?>> imports) {
        rawType.collectTypesOnImport(imports);
        if (owner != null) {
            owner.collectTypesOnImport(imports);
        }
        for (CapturedType<?, ?> typeArgument : typeArguments) {
            typeArgument.collectTypesOnImport(imports);
        }
    }

    public CapturedParameterizedType withOwner(CapturedType<?, ?> owner) {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.owner = owner);
    }

    public CapturedParameterizedType withRawType(ClassType<?> rawType) {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.rawType = rawType);
    }

    public CapturedParameterizedType withNoExplicitTypes() {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.typeArguments.clear());
    }

    public CapturedParameterizedType withExplicitTypes(List<CapturedType<?, ?>> bounds, boolean clear) {
        return mutableCopy().edit(capturedTypeVariable -> {
            if (clear) {
                capturedTypeVariable.typeArguments.clear();
            }
            capturedTypeVariable.typeArguments.addAll(bounds);
        });
    }

    public CapturedParameterizedType withExplicitType(int index, CapturedType<?, ?> bound) {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.typeArguments.set(index, bound));
    }

    public CapturedParameterizedType withExplicitType(CapturedType<?, ?> bound) {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.typeArguments.add(bound));
    }

    @Override
    public void write(CodeLineBuilder codeLineBuilder) {
        rawType.writeWithoutGenerics(codeLineBuilder);
        if (!typeArguments.isEmpty()) {
            codeLineBuilder.append("<");
            writeCollectionOfTypes(typeArguments, codeLineBuilder);
            codeLineBuilder.append(">");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapturedParameterizedType that = (CapturedParameterizedType) o;
        return Objects.equals(owner, that.owner) && toString().equals(that.toString());
    }
}
