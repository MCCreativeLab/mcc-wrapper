package de.verdox.mccreativelab.classgenerator.codegen.type.impl;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

public class CapturedParameterizedType extends CapturedType<CapturedParameterizedType, ParameterizedType> {

    @Override
    protected CapturedParameterizedType copy(boolean mutable) {
        CapturedParameterizedType other = new CapturedParameterizedType();
        other.owner = copyType(type -> type.owner, mutable);
        other.rawType = copyType(type -> type.rawType, mutable);
        other.typeArguments = copyCollection(type -> type.typeArguments, mutable);
        return other;
    }

    @Nullable
    private CapturedType<?, ?> owner;
    private CapturedClassType rawType;
    private List<CapturedType<?, ?>> typeArguments;

    protected CapturedParameterizedType(ParameterizedType type) {
        super(type);
        if (type.getOwnerType() != null) {
            this.owner = from(type.getOwnerType());
        }
        this.rawType = (CapturedClassType) from(type.getRawType());
        this.typeArguments = capture(type.getActualTypeArguments());
    }

    protected CapturedParameterizedType() {
        super(null);
    }

    public @Nullable CapturedType<?, ?> getOwner() {
        return owner;
    }

    public CapturedType<?, ?> getRawType() {
        return rawType;
    }

    public List<CapturedType<?, ?>> getTypeArguments() {
        return typeArguments;
    }

    @Override
    protected void collectTypesOnImport(Set<CapturedClassType> imports) {

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
}
