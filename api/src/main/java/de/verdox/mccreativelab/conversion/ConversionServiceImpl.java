package de.verdox.mccreativelab.conversion;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ConversionServiceImpl implements ConversionService {
    //TODO: Introduce caching of nativeObjects to object allocations
    private static final Logger LOGGER = Logger.getLogger(ConversionService.class.getSimpleName());

    private final ConversionCache<MCCConverter<?, ?>> conversionCache = new ConversionCache<>();

    public ConversionServiceImpl() {
        registerPlatformType(Optional.class, new OptionalConverter());
        registerPlatformType(List.class, new CollectionConverter<>(ArrayList::new, List.class));

        registerPlatformType(Set.class, new CollectionConverter<>(HashSet::new, Set.class));
        registerPlatformType(Map.class, new MapConverter<>(HashMap::new, Map.class));
        registerPlatformType(Map.Entry.class, new MapEntryConverter());
    }

    @Override
    public <A, T extends A, F> void registerPlatformType(Class<A> apiType, MCCConverter<F, T> converter) {
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
        return (Class<T>) conversionCache.getAllVariantsForNativeType(nativeType)
                .filter(mccConverter -> mccConverter.nativeMinecraftType().isAssignableFrom(nativeType))
                .map(mccConverter -> (MCCConverter<Object, Object>) mccConverter)
                .map(MCCConverter::apiImplementationClass)
                .map(objectClass -> conversionCache.getImplToApi().get(objectClass))
                .filter(Objects::nonNull)
                .findAny().orElse(null);
    }

/*    @Override
    public <F, T> T wrap(@Nullable F nativeObject) {
        if (nativeObject == null) {
            return null;
        }
        *//*return conversionCache.getAllVariantsForNativeType(nativeObject.getClass())
                        .filter(mccConverter -> mccConverter.nativeMinecraftType().isAssignableFrom(nativeObject.getClass()))
                        .map(mccConverter -> {
                            try{
                                return (MCCConverter<F, T>) mccConverter;
                            }
                            catch (ClassCastException e){
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .map(converter -> converter.wrap(nativeObject))
                        .filter(objectConversionResult -> objectConversionResult.result().isDone() && objectConversionResult.value() != null)
                        .map(MCCConverter.ConversionResult::value)
                        .findAny()
                        .orElseThrow(() -> new NoConverterFoundException("Could not find a converter to wrap the native type " + nativeObject + " (" + nativeObject.getClass().getCanonicalName() + "). Make sure that you have registered a converter for the given object type."));
    *//*
    return null;
    }*/

    @Override
    public <F, T> T wrap(@Nullable F nativeObject, TypeToken<T> apiTypeToConvertTo) {
        if (nativeObject == null) {
            return null;
        }
        Objects.requireNonNull(apiTypeToConvertTo, "The provided api type to convert to cannot be null");

        if (apiTypeToConvertTo.getRawType() == null)
            throw new IllegalArgumentException("Please provide a type token that fully holds the generic information to wrap (" + nativeObject + "). Do under no circumstances use <T> with a generic T that is unknown on compile time.");


        return conversionCache.getAllVariantsForNativeType(nativeObject.getClass())
                .filter(mccConverter -> mccConverter.nativeMinecraftType().isAssignableFrom(nativeObject.getClass())) // e.g. nms Block is assignable from Stonecutter Block
                .filter(mccConverter -> apiTypeToConvertTo.getRawType().isAssignableFrom(mccConverter.apiImplementationClass())) // e.g. NMSBlockType is assignable from MCCBlockType
                .map(mccConverter -> (MCCConverter<F, T>) mccConverter)
                .map(converter -> converter.wrap(nativeObject, apiTypeToConvertTo))
                .filter(objectConversionResult -> objectConversionResult.result().isDone() && objectConversionResult.value() != null)
                .map(MCCConverter.ConversionResult::value)
                .findAny()
                .orElseThrow(() -> new NoConverterFoundException("Could not find a converter to wrap the native type " + nativeObject + " (" + nativeObject.getClass().getCanonicalName() + "). Make sure that you have registered a converter for the given object type. The provided type token is " + apiTypeToConvertTo));
    }

/*    @Override
    public <F, T> F unwrap(@Nullable T apiObject) {
        if (apiObject == null) {
            return null;
        }
*//*        MCCConverter.ConversionResult<F> result = conversionCache.getAllVariantsForApiType(apiObject.getClass())
                .filter(mccConverter -> mccConverter.apiImplementationClass().isAssignableFrom(apiObject.getClass()))
                .map(mccConverter -> {
                    try{
                        return (MCCConverter<F, T>) mccConverter;
                    }
                    catch (ClassCastException e){
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(mccConverter -> mccConverter.unwrap(apiObject))
                .filter(objectConversionResult -> objectConversionResult.result().isDone())
                .findAny()
                .orElseThrow(() -> new NoConverterFoundException("Could not find a converter to unwrap the api type " + apiObject + " (" + apiObject.getClass().getCanonicalName() + "). Make sure that you have registered a converter for the given object type."));
        return result.value();*//*
        return null;
    }*/

    @Override
    public <F, T> F unwrap(T apiObject, TypeToken<F> nativeTypeToConvertTo) {
        if (apiObject == null) {
            return null;
        }
        Objects.requireNonNull(nativeTypeToConvertTo, "The provided native type to convert to cannot be null");

        if (nativeTypeToConvertTo.getRawType() == null)
            throw new IllegalArgumentException("Please provide a type token that fully holds the generic information to unwrap (" + apiObject + "). Do under no circumstances use <T> with a generic T that is unknown on compile time.");

        return conversionCache.getAllVariantsForApiType(apiObject.getClass())
                .filter(mccConverter -> mccConverter.apiImplementationClass().isAssignableFrom(apiObject.getClass()))
                .filter(mccConverter -> mccConverter.nativeMinecraftType().isAssignableFrom(nativeTypeToConvertTo.getRawType()))
                .map(mccConverter -> (MCCConverter<F, T>) mccConverter)
                .map(converter -> converter.unwrap(apiObject, nativeTypeToConvertTo))
                .filter(objectConversionResult -> objectConversionResult.result().isDone() && objectConversionResult.value() != null)
                .map(MCCConverter.ConversionResult::value)
                .findAny()
                .orElseThrow(() -> new NoConverterFoundException("Could not find a converter to unwrap the api type " + apiObject + " (" + nativeTypeToConvertTo.getClass().getCanonicalName() + "). Make sure that you have registered a converter for the given object type. The provided type token was " + nativeTypeToConvertTo));
    }

    @Override
    public boolean isNativeTypeKnown(Class<?> nativeType) {
        return conversionCache.knowsNativeType(nativeType);
    }

    @Override
    public boolean isApiTypeKnown(Class<?> apiType) {
        return conversionCache.knowsApiType(apiType);
    }

    @Override
    public Set<ClassPair> getAllKnownClassPairs() {
        return conversionCache.getApiToNativeMapping().stream().map(pair -> new ClassPair(pair.getKey(), pair.getValue())).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "ConversionServiceImpl{" +
                "conversionCache=" + conversionCache +
                '}';
    }
}
