package de.verdox.mccreativelab.conversion;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public interface ConversionService {
    /**
     * Registers a new converter that converts between an impl type and a native type. The impl type implements the api type
     * @param apiType the api type
     * @param converter the converter
     * @param <A> the api type
     * @param <T> the impl type
     * @param <F> the native type
     */
    <A, T extends A, F> void registerConverterForNewImplType(Class<A> apiType, MCCConverter<F, T> converter);

    /**
     * Wraps a given native object into an impl object that implements a specific api type
     * @param nativeObject the native object
     * @return the api object
     * @param <F> the native type
     * @param <T> the api type
     */
    <F, T> T wrap(F nativeObject);

    /**
     * Wraps a given native object into an impl object that implements a specific api type
     * @param nativeObject the native object
     * @param apiTypeToConvertTo a type token for explicit type declaration
     * @return the api object
     * @param <F> the native type
     * @param <T> the api type
     */
    <F, T> T wrap(@Nullable F nativeObject, TypeToken<T> apiTypeToConvertTo);

    /**
     * Wraps a given impl object into a native object
     * @param apiObject the api object
     * @return the native object
     * @param <F> the native type
     * @param <T> the api type
     */
    <F, T> F unwrap(T apiObject);

    /**
     * Wraps a given impl object into a native object
     * @param apiObject the api object
     * @param nativePlatformType a class for explicit type declaration
     * @return the native object
     * @param <F> the native type
     * @param <T> the api type
     */
    default <F, T> F unwrap(@Nullable T apiObject, TypeToken<F> nativePlatformType) {
        return unwrap(apiObject);
    }

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
     * Returns the api type a native type would be wrapped to. Throws an exception if no converter is available
     *
     * @param nativeType the native type
     * @param <F>        the generic native type
     * @param <T>        the generic api type
     * @throws NoConverterFoundException if no converter was found
     * @return the api type
     */
    <F, T> Class<T> wrapClassType(Class<F> nativeType);

    /**
     * Returns the api type a native type would be wrapped to or null if no converter was found.
     *
     * @param nativeType the native type
     * @param <F>        the generic native type
     * @param <T>        the generic api type
     * @return the api type
     */
    @Nullable <F, T> Class<T> wrapClassTypeOrNull(Class<F> nativeType);

    /**
     * Checks if the given native type has a converter mapping
     * @param nativeType the native type
     * @return true if a mapping exists
     */
    boolean isNativeTypeKnown(Class<?> nativeType);

    /**
     * Checks if the given api type has a converter mapping
     * @param apiType the api type
     * @return true if a mapping exists
     */
    boolean isApiTypeKnown(Class<?> apiType);

    /**
     * Wraps a given native object into an impl object that implements a specific api type
     * @param nativeObject the native object
     * @param apiTypeToConvertTo a class for explicit type declaration
     * @return the api object
     * @param <F> the native type
     * @param <T> the api type
     */
    default <F, T> T wrap(@Nullable F nativeObject, Class<T> apiTypeToConvertTo) {
        return wrap(nativeObject, TypeToken.of(apiTypeToConvertTo));
    }

    /**
     * Wraps a given impl object into a native object
     * @param apiObject the api object
     * @param nativePlatformType a class for explicit type declaration
     * @return the native object
     * @param <F> the native type
     * @param <T> the api type
     */
    default <F, T> F unwrap(@Nullable T apiObject, Class<F> nativePlatformType) {
        return unwrap(apiObject, TypeToken.of(nativePlatformType));
    }

    default <FROM, TO> boolean convertsCollection(FROM from, TypeToken<TO> toTypeToken) {
        return Collection.class.isAssignableFrom(from.getClass()) && toTypeToken.isSubtypeOf(new TypeToken<Collection<?>>() {});
    }

    default <FROM, TO> boolean convertsMap(FROM from, TypeToken<TO> toTypeToken) {
        return Map.class.isAssignableFrom(from.getClass()) &&
                toTypeToken.isSubtypeOf(new TypeToken<Map<?, ?>>() {});
    }

    default <FROM, TO> boolean convertsMapEntry(FROM from, TypeToken<TO> toTypeToken) {
        return Map.Entry.class.isAssignableFrom(from.getClass()) &&
                toTypeToken.isSubtypeOf(new TypeToken<Map.Entry<?, ?>>() {});
    }

    default <FROM, TO> boolean convertsOptional(FROM from, TypeToken<TO> toTypeToken) {
        return from instanceof Optional &&
                toTypeToken.isSubtypeOf(new TypeToken<Optional<?>>() {});
    }

    default <FROM, TO> boolean convertsIterator(FROM from, TypeToken<TO> toTypeToken) {
        return Iterator.class.isAssignableFrom(from.getClass()) &&
                toTypeToken.isSubtypeOf(new TypeToken<Iterator<?>>() {});
    }

    default <FROM, TO> boolean convertsIterable(FROM from, TypeToken<TO> toTypeToken) {
        return Iterable.class.isAssignableFrom(from.getClass()) &&
                toTypeToken.isSubtypeOf(new TypeToken<Iterable<?>>() {});
    }

}
