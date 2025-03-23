package de.verdox.mccreativelab.classgenerator.codegen.type.impl;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;

import java.lang.reflect.WildcardType;
import java.util.*;

public class CapturedWildcardType extends CapturedType<CapturedWildcardType, WildcardType> {

    public static CapturedWildcardType create() {
        return new CapturedWildcardType();
    }

    @Override
    protected CapturedWildcardType copy(boolean mutable) {
        CapturedWildcardType other = new CapturedWildcardType();
        other.upperBound = copyCollection(type -> type.upperBound, mutable);
        other.lowerBound = copyCollection(type -> type.lowerBound, mutable);
        return other;
    }

    private List<CapturedType<?, ?>> upperBound = new LinkedList<>();
    private List<CapturedType<?, ?>> lowerBound = new LinkedList<>();

    protected CapturedWildcardType(WildcardType type) {
        CAPTURED_REGISTRY.linkToRealType(type, this);
        this.upperBound = capture(type.getUpperBounds());
        this.lowerBound = capture(type.getLowerBounds());
    }

    protected CapturedWildcardType() {
    }

    public CapturedWildcardType withUpperBounds(List<CapturedType<?, ?>> bounds, boolean clear) {
        return mutableCopy().edit(capturedTypeVariable -> {
            if (clear) {
                capturedTypeVariable.upperBound.clear();
            }
            capturedTypeVariable.upperBound.addAll(bounds);
        });
    }

    public CapturedWildcardType withLowerBounds(List<CapturedType<?, ?>> bounds, boolean clear) {
        return mutableCopy().edit(capturedTypeVariable -> {
            if (clear) {
                capturedTypeVariable.upperBound.clear();
            }
            capturedTypeVariable.upperBound.addAll(bounds);
        });
    }

    /**
     * Upper bounds: {@code ? extends Number}.
     *
     * @return
     */
    public CapturedWildcardType withUpperBound(CapturedType<?, ?> capturedType) {
        return mutableCopy().edit(type -> type.upperBound.add(capturedType));
    }

    @Override
    public ClassType<?> getRawType() {
        throw new IllegalStateException("Wildcards don't have a raw type");
    }

    /**
     * Upper bounds: {@code ? extends Number}.
     *
     * @return
     */
    public CapturedWildcardType withUpperBound(int index, CapturedType<?, ?> capturedType) {
        return mutableCopy().edit(type -> type.upperBound.set(index, capturedType));
    }

    /**
     * Lower bounds: {@code ? super Number}.
     *
     * @return
     */
    public CapturedWildcardType withLowerBound(CapturedType<?, ?> capturedType) {
        return mutableCopy().edit(type -> type.lowerBound.add(capturedType));
    }

    /**
     * Lower bounds: {@code ? super Number}.
     *
     * @return
     */
    public CapturedWildcardType withLowerBound(int index, CapturedType<?, ?> capturedType) {
        return mutableCopy().edit(type -> type.lowerBound.set(index, capturedType));
    }

    /**
     * Lower bounds: {@code ? super Number}.
     *
     * @return
     */
    public CapturedWildcardType withNoLowerBounds() {
        return mutableCopy().edit(type -> type.lowerBound.clear());
    }

    /**
     * Upper bounds: {@code ? extends Number}.
     *
     * @return
     */
    public CapturedWildcardType withNoUpperBounds() {
        return mutableCopy().edit(type -> type.upperBound.clear());
    }

    public List<CapturedType<?, ?>> getLowerBounds() {
        return lowerBound;
    }

    public List<CapturedType<?, ?>> getUpperBounds() {
        return upperBound;
    }

    @Override
    public void collectTypesOnImport(Set<ClassType<?>> imports) {
        for (CapturedType<?, ?> capturedType : this.upperBound) {
            capturedType.collectTypesOnImport(imports);
        }
        for (CapturedType<?, ?> capturedType : this.lowerBound) {
            capturedType.collectTypesOnImport(imports);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapturedWildcardType that = (CapturedWildcardType) o;
        return toString().equals(that.toString());
    }

    @Override
    public void write(CodeLineBuilder codeLineBuilder) {
        codeLineBuilder.append("?");
        if (upperBound != null) {
            for (int i = 0; i < upperBound.size(); i++) {
                var upper = upperBound.get(i);
                if (upper.equals(CapturedType.OBJECT_TYPE)) {
                    continue;
                }
                codeLineBuilder.append(" extends ");
                upper.write(codeLineBuilder);
                if (i < upperBound.size() - 1) {
                    codeLineBuilder.append(", ");

                }
            }
        }

        for (int i = 0; i < lowerBound.size(); i++) {
            var lower = lowerBound.get(i);
            if (lower.equals(CapturedType.OBJECT_TYPE)) {
                continue;
            }
            codeLineBuilder.append(" super ");
            lower.write(codeLineBuilder);
            if (i < lowerBound.size() - 1) {
                codeLineBuilder.append(", ");
            }
        }
    }
}
