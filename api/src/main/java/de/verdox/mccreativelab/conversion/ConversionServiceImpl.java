package de.verdox.mccreativelab.conversion;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.*;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConversionServiceImpl implements ConversionService {
    //TODO: Introduce caching of nativeObjects to object allocations
    private static final Logger LOGGER = Logger.getLogger(ConversionService.class.getSimpleName());

    private final ConversionCache<MCCConverter<?, ?>> conversionCache = new ConversionCache<>();

    public ConversionServiceImpl() {
        registerConverterForNewImplType(Optional.class, new OptionalConverter(this));
        registerConverterForNewImplType(List.class, new CollectionConverter<>(this, ArrayList::new, List.class));

        registerConverterForNewImplType(Set.class, new CollectionConverter<>(this, HashSet::new, Set.class));
        registerConverterForNewImplType(Map.class, new MapConverter<>(this, HashMap::new, Map.class));
        registerConverterForNewImplType(Map.Entry.class, new MapEntryConverter(this));
    }

    @Override
    public <A, T extends A, F> void registerConverterForNewImplType(Class<A> apiType, MCCConverter<F, T> converter) {
        LOGGER.log(Level.FINER, "Registering converter for " + apiType.getSimpleName() + " (" + converter.nativeMinecraftType().getSimpleName() + " <-> " + converter.apiImplementationClass().getSimpleName() + ")");
        conversionCache.put(apiType, converter.apiImplementationClass(), converter.nativeMinecraftType(), converter);
    }

    @Override
    public <F, T> Class<T> wrapClassType(Class<F> nativeType) {
        Objects.requireNonNull(nativeType, "The provided native type cannot be null");
        Class<T> result = wrapClassTypeOrNull(nativeType);
        if (result == null) {
            throw new NoConverterFoundException("Could not find a converter to wrap the native type (" + nativeType.getCanonicalName() + "). Make sure that you have registered a converter for the given object type.");
        }
        return result;
    }

    @Override
    public @Nullable <F, T> Class<T> wrapClassTypeOrNull(Class<F> nativeType) {
        Objects.requireNonNull(nativeType, "The provided native type cannot be null");
        return conversionCache.streamAllDirectVariantsForNativeType(nativeType)
                .filter(mccConverter -> mccConverter.nativeMinecraftType().isAssignableFrom(nativeType))
                .map(mccConverter -> (MCCConverter<Object, Object>) mccConverter)
                .map(MCCConverter::apiImplementationClass)
                .map(objectClass -> conversionCache.getApiTypeOfImplType(objectClass))
                .map(aClass -> (Class<T>) aClass)
                .filter(Objects::nonNull)
                .findAny().orElse(null);
    }

    @Override
    public <F, T> T wrap(@Nullable F nativeObject) {
        if (nativeObject == null) {
            return null;
        }

        //TODO: Group only if the groups are not related to each other
/*        var allPossibleConverters = conversionCache.streamAllVariantsForNativeType(nativeObject.getClass()).collect(Collectors.groupingBy(mccConverter -> conversionCache.getApiTypeOfImplType(mccConverter.apiImplementationClass())));

        if (allPossibleConverters.size() > 1) {
            LOGGER.log(Level.SEVERE, "The native type " + nativeObject + " has more than one hierarchy path. This makes using wrap() without providing an explicit TypeToken unsafe. Please use wrap(nativeObject, TypeToken) instead. Potential converters are " + allPossibleConverters);
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                LOGGER.warning("\t" + stackTraceElement.toString());
            }
        }*/

        T result = conversionCache.streamAllDirectVariantsForNativeType(nativeObject.getClass())
                .filter(mccConverter -> mccConverter.nativeMinecraftType().isAssignableFrom(nativeObject.getClass()))
                .map(mccConverter -> ((MCCConverter<F, T>) mccConverter).wrap(nativeObject))
                .filter(objectConversionResult -> objectConversionResult.result().isDone())
                .map(MCCConverter.ConversionResult::value)
                .findAny().orElse(null);

        if (result != null) {
            return result;
        }
        // When the converter did not return a result we return the native object and assume that there is no converter needed. This is useful for types that are not wrapped by the api
        return (T) nativeObject;
    }

    @Override
    public <F, T> T wrap(@Nullable F nativeObject, TypeToken<T> apiTypeToConvertTo) {
        if (nativeObject == null) {
            return null;
        }
        T result = conversionCache.streamAllVariantsForNativeType(nativeObject.getClass())
                .filter(mccConverter -> mccConverter.nativeMinecraftType().isAssignableFrom(nativeObject.getClass()))
                .filter(mccConverter -> {
                    Class<?> converterApiClass = mccConverter.apiImplementationClass();
                    boolean expectedIsAssignableFromActual = apiTypeToConvertTo.getRawType().isAssignableFrom(converterApiClass);
                    boolean actualIsAssignableFromExpected = converterApiClass.isAssignableFrom(apiTypeToConvertTo.getRawType());
                    return expectedIsAssignableFromActual || actualIsAssignableFromExpected;
                })
                .map(mccConverter -> (MCCConverter<F, T>) mccConverter)
                .map(mccConverter -> mccConverter.wrap(nativeObject, apiTypeToConvertTo))
                .filter(objectConversionResult -> objectConversionResult.result().isDone())
                .map(MCCConverter.ConversionResult::value)
                .filter(wrapped -> apiTypeToConvertTo.getRawType().isInstance(wrapped))
                .findAny().orElse(null);

        if (result != null) {
            return result;
        }

        try {
            return (T) apiTypeToConvertTo.getRawType().cast(nativeObject);
        } catch (ClassCastException e) {
            throw new NoConverterFoundException("Could not find a converter to wrap the native type " + nativeObject + " (" + nativeObject.getClass().getCanonicalName() + ") to an object of type " + apiTypeToConvertTo.getRawType() + ". Make sure that you have registered a converter for the given object type. None of the following potential converters could produce a valid result " + conversionCache.streamAllVariantsForNativeType(nativeObject.getClass()).map(MCCConverter::toReadableString).toList());
        }
    }

    @Override
    public <F, T> F unwrap(@Nullable T implementedApiObject) {
        if (implementedApiObject == null) {
            return null;
        }

        MCCConverter<F, T> converter;
        MCCConverter.ConversionResult<F> result = null;
        // First we use the converter the object type is mapped to in the conversion cache
        if ((converter = (MCCConverter<F, T>) conversionCache.getValue(implementedApiObject.getClass())) != null && (result = converter.unwrap(implementedApiObject)).result().isDone()) {
            try {
                return result.value();
            } catch (ClassCastException e) {
                throw new IllegalStateException("The found converter for the implemented api type " + implementedApiObject.getClass() + " was able to unwrap the provided object but failed produced a ClassCastException because the native type of the converter that was used is different from what was expected", e);
            }
        }
        // If the produced result is not null but also not done we throw a specific exception for that
        else if (result != null) {
            throw new IllegalStateException("The found converter could not provide a valid result for the implemented api type " + implementedApiObject.getClass());
        }

        // At this point no converter was found
        // Maybe F = T so at last we try to return it directly. If that does not work also we throw the NoConverterFoundException
        try {
            return (F) implementedApiObject;
        } catch (ClassCastException e) {
            throw new NoConverterFoundException("Could not find a converter to wrap the api type " + implementedApiObject + " (" + implementedApiObject.getClass().getCanonicalName() + "). Make sure that you have registered a converter for the given object type.");
        }
    }

    @Override
    public <A1, A2> A2 apiTypeToOtherApiType(A1 apiType, ConversionService conversionService) {
        if (conversionService.equals(this)) {
            throw new IllegalArgumentException("The provided conversion service is identical to this conversion service.");
        }
        if (apiType == null) {
            return null;
        }
        Object nativeObject = unwrap(apiType);
        return conversionService.wrap(nativeObject);
    }

    @Override
    public boolean isNativeTypeKnown(Class<?> nativeType) {
        return conversionCache.knowsNativeType(nativeType);
    }

    @Override
    public boolean isApiTypeKnown(Class<?> apiType) {
        return conversionCache.knowsApiType(apiType);
    }

    @VisibleForTesting
    public ConversionCache<MCCConverter<?, ?>> getConversionCache() {
        return conversionCache;
    }

    @Override
    public String toString() {
        return "ConversionServiceImpl{" +
                "conversionCache=" + conversionCache +
                '}';
    }
}
