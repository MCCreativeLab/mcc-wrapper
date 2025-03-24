package de.verdox.mccreativelab.classgenerator.codegen.type.impl;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.classgenerator.NMSMapper;
import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.CodeExpression;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.CapturedJavaClass;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.MutableClassType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.reference.CapturedTypeReference;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @param <C> Self referencing generic
 * @param <T>
 */
public abstract class CapturedType<C extends CapturedType<C, T>, T> implements CodeExpression {

    public static final CapturedRegistry CAPTURED_REGISTRY = new CapturedRegistry();

    public static CapturedType<?, ?> swap(Type type) {
        return NMSMapper.swap(from(type));
    }

    public static CapturedType<?, ?> swap(TypeToken<?> type) {
        return NMSMapper.swap(from(type));
    }

    public static CapturedType<?, ?> from(Type type) {
        CapturedType<?, ?> found = CAPTURED_REGISTRY.resolve(type);
        if (found != null) {
            return found;
        }

        if (type instanceof Class<?> clazz) {
            found = new CapturedJavaClass(clazz);
        } else if (type instanceof TypeVariable<?> typeVariable) {
            found = new CapturedTypeVariable(typeVariable);
        } else if (type instanceof ParameterizedType parameterizedType) {
            found = new CapturedParameterizedType(parameterizedType);
        } else if (type instanceof WildcardType wildcardType) {
            found = new CapturedWildcardType(wildcardType);
        }
        if (found != null) {
            CAPTURED_REGISTRY.linkToRealType(type, found);
            return found;
        } else {
            throw new IllegalStateException("No captured type found for type " + type.getTypeName() + " (" + type.getClass().getCanonicalName() + ")");
        }
    }

    public static final CapturedType<?, ?> OBJECT_TYPE = from(Object.class);

    public static CapturedType<?, ?> from(TypeToken<?> type) {
        return from(type.getType());
    }

    protected <O extends CapturedType<?, ?>> O edit(Consumer<O> edit) {
        edit.accept((O) this);
        return (O) this;
    }

    protected List<CapturedType<?, ?>> capture(Type[] types) {
        List<CapturedType<?, ?>> captured = new LinkedList<>();
        for (Type type : types) {
            captured.add(from(type));
        }
        return captured;
    }

    protected void writeCollectionOfTypes(@Nullable List<? extends CapturedType<?, ?>> collection, CodeLineBuilder codeLineBuilder) {
        if (collection == null) {
            return;
        }
        for (int i = 0; i < collection.size(); i++) {
            var upper = collection.get(i);
            upper.write(codeLineBuilder);
            if (i < collection.size() - 1) {
                codeLineBuilder.append(", ");
            }
        }
    }

    protected <A> A copy(Function<C, A> getter, Function<A, A> copy, Function<A, A> mutableCopy, boolean mutable) {
        A got = getter.apply((C) this);
        if (got == null) {
            return null;
        }

        if (mutable) {
            return mutableCopy.apply(got);
        }
        return copy.apply(got);
    }

    protected <A extends CapturedType<?, ?>> A copyType(Function<C, A> getter, boolean mutable) {
        return copy(getter, (a) -> {
            if (a instanceof ClassType<?> classType && !(classType instanceof MutableClassType)) {
                return a;
            }
            return (A) a.copy(false);
        }, a -> {
            if (a instanceof ClassType<?> classType && !(classType instanceof MutableClassType)) {
                return a;
            }
            return (A) a.copy(true);
        }, mutable);
    }

    protected <A> List<A> copyCollection(Function<C, List<A>> getter, boolean mutable) {
        return copy(getter, List::copyOf, LinkedList::new, mutable);
    }

    public abstract ClassType<?> getRawType();

    public final C copy() {
        return copy(false);
    }

    protected final C mutableCopy() {
        return copy(true);
    }

    protected abstract C copy(boolean mutable);

    public abstract void collectTypesOnImport(Set<ClassType<?>> imports);

    @Override
    public String toString() {
        CodeLineBuilder codeLineBuilder = new CodeLineBuilder(null, 0);
        write(codeLineBuilder);
        return codeLineBuilder.toString();
    }
}
