package de.verdox.mccreativelab.conversion;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface ConversionService {
    <A, T extends A, F> void registerPlatformType(Class<A> apiType, MCCConverter<F, T> converter);

    //<F, T> T wrap(F nativeObject);

    <F, T> T wrap(F nativeObject, TypeToken<T> apiTypeToConvertTo);

    //<F, T> F unwrap(T apiObject);

    <F, T> F unwrap(T apiObject, TypeToken<F> nativeTypeToConvertTo);

    /**
     * Returns the api type a native type would be wrapped to
     *
     * @param nativeType the native type
     * @param <F>        the generic native type
     * @param <T>        the generic api type
     * @return the api type
     */
    <F, T> Class<T> wrapClassType(Class<F> nativeType);

    @Nullable <F, T> Class<T> wrapClassTypeOrNull(Class<F> nativeType);

    boolean isNativeTypeKnown(Class<?> nativeType);

    boolean isApiTypeKnown(Class<?> apiType);

    default <F, T> T wrap(@Nullable F objectToWrap, Class<T> apiTypeToConvertTo) {
        return wrap(objectToWrap, TypeToken.of(apiTypeToConvertTo));
    }

    default <F, T> F unwrap(@Nullable T objectToUnwrap, Class<F> nativePlatformType) {
        return unwrap(objectToUnwrap, TypeToken.of(nativePlatformType));
    }


    Set<ClassPair> getAllKnownClassPairs();

    record ClassPair(Class<?> apiType, Class<?> nativeType) {}
}
