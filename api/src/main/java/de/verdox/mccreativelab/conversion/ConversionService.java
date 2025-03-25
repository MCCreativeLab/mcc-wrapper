package de.verdox.mccreativelab.conversion;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import org.jetbrains.annotations.Nullable;

public interface ConversionService {
    <A, T extends A, F> void registerConverterForNewImplType(Class<A> apiType, MCCConverter<F, T> converter);

    <F, T> T wrap(F nativeObject);

    <F, T> F unwrap(T apiObject);

    /**
     * This method is used to convert from one type tree to another.
     * <p>
     * It works when both api types are mapped to the same native type. Via the native type we can convert it from one api type to another.
     * The method is useful when you want to convert between MCC objects and Bukkit Objects for example. Both usually map to the same native type.
     * However, be advised that not all impl types might work here.
     *
     * @param apiType           the api object to convert
     * @param conversionService the other conversion service that holds the information of the second api type
     * @param <A1>              the first api type
     * @param <A2>              the second api type
     * @return an object of the other api type but with the same native type
     */
    <A1, A2> A2 apiTypeToOtherApiType(A1 apiType, ConversionService conversionService);

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

    default <F, T> T wrap(@Nullable F objectToWrap, TypeToken<T> apiTypeToConvertTo) {
        return (T) wrap(objectToWrap);
    }

    default <F, T> T wrap(@Nullable F objectToWrap, Class<T> apiTypeToConvertTo) {
        return (T) wrap(objectToWrap);
    }

    default <F, T> F unwrap(@Nullable T objectToUnwrap, TypeToken<F> nativePlatformType) {
        return (F) unwrap(objectToUnwrap);
    }

    default <F, T> F unwrap(@Nullable T objectToUnwrap, Class<F> nativePlatformType) {
        return (F) unwrap(objectToUnwrap);
    }
}
