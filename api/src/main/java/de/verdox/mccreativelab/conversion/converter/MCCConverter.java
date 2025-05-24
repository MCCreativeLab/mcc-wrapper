package de.verdox.mccreativelab.conversion.converter;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A converter that defines logic to convert between a native platform type and an api type implementation.
 * In the mcc conversion ecosystem one native type is always mapped to one api type. However, api types may have different implementation types.
 *
 * @param <F> the native platform type
 * @param <T> the api type implementation
 */
public interface MCCConverter<F, T> {
    /**
     * Wraps the provided native type into an api type impl
     *
     * @param nativeType the native type
     * @return the result
     */
    ConversionResult<T> wrap(F nativeType);

    /**
     * Wraps the provided native type into an api type impl
     *
     * @param nativeType the native type
     * @return the result
     */
    default ConversionResult<T> wrap(F nativeType, TypeToken<T> toType) {
        return wrap(nativeType);
    }

    /**
     * Unwraps the provided api type impl into a native type.
     *
     * @param platformImplType the api type impl
     * @return the result
     */
    ConversionResult<F> unwrap(T platformImplType);

    /**
     * Unwraps the provided api type impl into a native type.
     *
     * @param platformImplType the api type impl
     * @return the result
     */
    default ConversionResult<F> unwrap(T platformImplType, TypeToken<F> fromType) {
        return unwrap(platformImplType);
    }

    /**
     * The api impl class
     *
     * @return the class
     */
    Class<T> apiImplementationClass();

    /**
     * The native class
     *
     * @return the class
     */
    Class<F> nativeMinecraftType();

    /**
     * Helper method to create a result when the conversion was successful
     *
     * @param result the result of the conversion
     * @param <V> the generic result type
     * @return the conversion result wrapper
     */
    default <V> ConversionResult<V> done(V result) {
        return new ConversionResult<>(ResultType.DONE, result);
    }

    /**
     * Helper method to create a result when the conversion was not successful or was not applied
     *
     * @param result the result of the conversion
     * @param <V> the generic result type
     * @return the conversion result wrapper
     */
    default <V> ConversionResult<V> notDone(V result) {
        return new ConversionResult<>(ResultType.NOT_DONE, result);
    }

    /**
     * A wrapper for conversion results.
     * @param result the result type
     * @param value the result object
     * @param <V> the generic result type
     */
    record ConversionResult<V>(ResultType result, V value) {
    }

    /**
     * A conversion result type
     */
    enum ResultType {
        /**
         * The converter successfully converted an object
         */
        DONE,
        /**
         * The converter either did not convert an object successfully or was not applied
         */
        NOT_DONE;

        /**
         * Whether the conversion was done correctly
         * @return true if it was done correctly
         */
        public boolean isDone() {
            return this.equals(DONE);
        }
    }

    /**
     * A debug string
     * @return the string
     */
    default String toReadableString() {
        return "MCCConverter<" + nativeMinecraftType() + " <-> " + apiImplementationClass() + ">";
    }

    default List<TypeToken<?>> getTypeArguments(TypeToken<?> token) {
        Type type = token.getType();
        if (type instanceof ParameterizedType parameterizedType) {
            return Arrays.stream(parameterizedType.getActualTypeArguments())
                    .map(TypeToken::of)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
