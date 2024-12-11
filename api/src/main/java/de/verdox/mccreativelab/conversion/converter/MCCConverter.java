package de.verdox.mccreativelab.conversion.converter;

import com.google.common.reflect.TypeToken;

public interface MCCConverter<F, T> {
    //ConversionResult<T> wrap(F nativeType);

    ConversionResult<T> wrap(F nativeType, TypeToken<T> tokenToConvertTo);

    //ConversionResult<F> unwrap(T platformImplType);

    ConversionResult<F> unwrap(T platformImplType, TypeToken<F> tokenToConvertTo);

    Class<T> apiImplementationClass();

    Class<F> nativeMinecraftType();

    default <V> ConversionResult<V> done(V result) {
        return new ConversionResult<>(ResultType.DONE, result);
    }

    default <V> ConversionResult<V> notDone(V result) {
        return new ConversionResult<>(ResultType.NOT_DONE, result);
    }

    record ConversionResult<V>(ResultType result, V value) {
    }

    enum ResultType {
        DONE,
        NOT_DONE;

        public boolean isDone() {
            return this.equals(DONE);
        }
    }
}
