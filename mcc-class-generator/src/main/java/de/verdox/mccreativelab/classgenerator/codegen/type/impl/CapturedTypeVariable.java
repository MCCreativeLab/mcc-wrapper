package de.verdox.mccreativelab.classgenerator.codegen.type.impl;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.vserializer.SerializableField;
import de.verdox.vserializer.generic.Serializer;
import de.verdox.vserializer.generic.SerializerBuilder;

import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * Represents a generic type variable
 */
public class CapturedTypeVariable extends CapturedType<CapturedTypeVariable, TypeVariable<?>> {
    public static CapturedTypeVariable create(String name) {
        return new CapturedTypeVariable().withName(name);
    }

/*    public static final Serializer<CapturedTypeVariable> SERIALIZER = SerializerBuilder.create("type_variable", CapturedTypeVariable.class)
            .constructor(
                    new SerializableField<>("className", Serializer.Primitive.STRING, CapturedTypeVariable::getName),
                    new SerializableField<>("bounds", Serializer.Collection.create(CapturedType.SERIALIZER, LinkedList::new), CapturedTypeVariable::getBounds),
                    (s, capturedTypes) -> CapturedTypeVariable.create(s).withBounds(capturedTypes)
            )
            .build();*/

    @Override
    protected CapturedTypeVariable copy(boolean mutable) {
        CapturedTypeVariable other = new CapturedTypeVariable();
        other.name = this.name;
        other.bounds = copyCollection(type -> type.bounds, mutable);
        return other;
    }

    private String name;
    private List<CapturedType<?, ?>> bounds;

    protected CapturedTypeVariable(TypeVariable<?> type) {
        super(type);
        name = type.getName();
        bounds = capture(type.getBounds());
    }

    protected CapturedTypeVariable() {
        super(null);
    }

    public String getName() {
        return name;
    }

    public List<CapturedType<?, ?>> getBounds() {
        return bounds;
    }

    public CapturedTypeVariable withName(String name) {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.name = name);
    }

    public CapturedTypeVariable withNoBounds() {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.bounds.clear());
    }

    public CapturedTypeVariable withBounds(List<CapturedType<?, ?>> bounds) {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.bounds.addAll(bounds));
    }

    public CapturedTypeVariable withBound(int index, CapturedType<?, ?> bound) {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.bounds.set(index, bound));
    }

    public CapturedTypeVariable withBound(CapturedType<?, ?> bound) {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.bounds.add(bound));
    }

    @Override
    protected void collectTypesOnImport(Set<CapturedClassType> imports) {
        for (CapturedType<?, ?> bound : bounds) {
            bound.collectTypesOnImport(imports);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapturedTypeVariable that = (CapturedTypeVariable) o;
        return Objects.equals(name, that.name) && Objects.equals(bounds, that.bounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, bounds);
    }

    @Override
    public void write(CodeLineBuilder codeLineBuilder) {
        codeLineBuilder.append(name);
    }
}
