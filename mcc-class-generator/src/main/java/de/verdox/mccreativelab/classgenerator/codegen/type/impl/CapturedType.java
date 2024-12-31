package de.verdox.mccreativelab.classgenerator.codegen.type.impl;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.CodeExpression;
import de.verdox.vserializer.generic.Serializer;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @param <C> Self referencing generic
 * @param <T>
 */
public abstract class CapturedType<C extends CapturedType<C, T>, T extends Type> implements CodeExpression {
    static final CapturedRegistry CAPTURED_REGISTRY = new CapturedRegistry();

    public static CapturedType<?, ?> from(Type type) {
        CapturedType<?, ?> found = CAPTURED_REGISTRY.resolve(type);
        if (found != null) {
            return found;
        }

        if (type instanceof Class<?> clazz) {
            return new CapturedClassType(clazz);
        } else if (type instanceof TypeVariable<?> typeVariable) {
            return new CapturedTypeVariable(typeVariable);
        } else if (type instanceof ParameterizedType parameterizedType) {
            return new CapturedParameterizedType(parameterizedType);
        } else if (type instanceof WildcardType wildcardType) {
            return new CapturedWildcardType(wildcardType);
        } else {
            throw new IllegalStateException("No captured type found for type " + type.getTypeName() + " (" + type.getClass().getCanonicalName() + ")");
        }
    }

    public static final CapturedType<?, ?> OBJECT_TYPE = from(Object.class);

    public static CapturedType<?, ?> from(TypeToken<?> type) {
        return from(type.getType());
    }

    protected CapturedType(T type) {
        if (type != null) {
            CAPTURED_REGISTRY.link(type, this);
        }
    }

    protected <O extends CapturedType<?, ?>> O edit(Consumer<O> edit) {
        edit.accept((O) this);
        return (O) this;
    }

    protected List<CapturedType<?, ?>> capture(Type[] types) {
        return (List<CapturedType<?, ?>>) Arrays.stream(types).map(CapturedType::from).map(capturedType -> ((CapturedType<?, ?>) capturedType).copy()).toList();
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
        return copy(getter, (a) -> (A) a.copy(false), a -> (A) a.copy(true), mutable);
    }

    protected <A extends CapturedType<?, ?>> List<A> copyCollection(Function<C, List<A>> getter, boolean mutable) {
        return copy(getter, List::copyOf, LinkedList::new, mutable);
    }

    public final C copy() {
        return copy(false);
    }

    protected final C mutableCopy() {
        return copy(true);
    }

    protected abstract C copy(boolean mutable);

    protected abstract void collectTypesOnImport(Set<CapturedClassType> imports);
}
