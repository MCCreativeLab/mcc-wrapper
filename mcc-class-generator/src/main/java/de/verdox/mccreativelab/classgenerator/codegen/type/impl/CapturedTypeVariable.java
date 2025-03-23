package de.verdox.mccreativelab.classgenerator.codegen.type.impl;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;

import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * Represents a generic type variable
 */
public class CapturedTypeVariable extends CapturedType<CapturedTypeVariable, TypeVariable<?>> {
    public static CapturedTypeVariable create(String name) {
        return new CapturedTypeVariable().withName(name);
    }
    public static CapturedTypeVariable from(TypeVariable<?> typeVariable){
        return new CapturedTypeVariable(typeVariable);
    }

/*    public static final Serializer<CapturedTypeVariable> SERIALIZER = SerializerBuilder.create("type_variable", CapturedTypeVariable.class)
            .constructor(
                    new SerializableField<>("className", Serializer.Primitive.STRING, CapturedTypeVariable::getName),
                    new SerializableField<>("bounds", Serializer.Collection.create(CapturedType.SERIALIZER, LinkedList::new), CapturedTypeVariable::getBounds),
                    (s, capturedTypes) -> CapturedTypeVariable.create(s).withBounds(capturedTypes)
            )
            .build();*/

    @Override
    public ClassType<?> getRawType() {
        throw new IllegalStateException("Type Variables don't have a raw type");
    }

    @Override
    protected CapturedTypeVariable copy(boolean mutable) {
        CapturedTypeVariable other = new CapturedTypeVariable();
        other.name = this.name;
        other.bounds = copyCollection(type -> type.bounds, mutable);
        return other;
    }

    private String name;
    private List<CapturedType<?, ?>> bounds = new LinkedList<>();

    protected CapturedTypeVariable(TypeVariable<?> type) {
        name = type.getName();
        CAPTURED_REGISTRY.linkToRealType(type, this);
        bounds = capture(type.getBounds());
    }

    protected CapturedTypeVariable() {

    }

    public String getName() {
        return name;
    }

    public List<CapturedType<?, ?>> getUpperBounds() {
        return bounds;
    }

    public CapturedTypeVariable withName(String name) {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.name = name);
    }

    public CapturedTypeVariable withNoUpperBounds() {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.bounds.clear());
    }

    public CapturedTypeVariable withUpperBounds(List<CapturedType<?, ?>> bounds, boolean clear) {
        return mutableCopy().edit(capturedTypeVariable -> {
            if(clear){
                capturedTypeVariable.bounds.clear();
            }
            capturedTypeVariable.bounds.addAll(bounds);
        });
    }

    public CapturedTypeVariable withUpperBound(int index, CapturedType<?, ?> bound) {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.bounds.set(index, bound));
    }

    public CapturedTypeVariable withUpperBound(CapturedType<?, ?> bound) {
        return mutableCopy().edit(capturedTypeVariable -> capturedTypeVariable.bounds.add(bound));
    }

    @Override
    public void collectTypesOnImport(Set<ClassType<?>> imports) {
        for (CapturedType<?, ?> bound : bounds) {
            bound.collectTypesOnImport(imports);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapturedTypeVariable that = (CapturedTypeVariable) o;
        return that.toString().equals(this.toString());
    }

    @Override
    public void write(CodeLineBuilder codeLineBuilder) {
        codeLineBuilder.append(name);
        List<CapturedType<?, ?>> boundsWithoutObject = this.bounds.stream().filter(capturedType -> !capturedType.equals(CapturedType.OBJECT_TYPE)).toList();
        if (boundsWithoutObject.isEmpty())
            return;
        for (int i = 0; i < boundsWithoutObject.size(); i++) {
            CapturedType<?, ?> upperBound = boundsWithoutObject.get(i);
            codeLineBuilder.append("extends ").append(upperBound);
            if (i < boundsWithoutObject.size() - 1) {
                codeLineBuilder.append(", ");
            }
        }
    }
}
