package de.verdox.mccreativelab.conversion.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Used to convert between maps. The converter will iterate through the key value pairs and apply its logic to both the key and the value. It then creates a new map from a provided constructor to return the converted map.
 *
 * @param <M> the generic map type
 * @param <T> the specific map type
 */
public class MapConverter<M extends Map, T extends M> extends ContainerConverter<M, M> {
    private final Supplier<T> constructor;
    private final Class<M> mapType;

    public MapConverter(ConversionService conversionService, Supplier<T> constructor, Class<M> mapType) {
        super(conversionService);
        this.constructor = constructor;
        this.mapType = mapType;
    }

    @Override
    public ConversionResult<M> wrap(M nativeType) {
        M newMap = constructor.get();
        nativeType.forEach((nativeKey, nativeValue) -> {
            Object wrappedKey = getConversionService().wrap(nativeKey);
            Object wrappedValue = getConversionService().wrap(nativeValue);
            newMap.put(wrappedKey, wrappedValue);
        });
        return done(newMap);
    }

    @Override
    public ConversionResult<M> unwrap(M platformImplType) {
        M newMap = constructor.get();
        platformImplType.forEach((wrappedKey, wrappedValue) -> {
            Object nativeKey = getConversionService().unwrap(wrappedKey);
            Object nativeValue = getConversionService().unwrap(wrappedValue);
            newMap.put(nativeKey, nativeValue);
        });
        return done(newMap);
    }

    @Override
    public ConversionResult<M> wrap(M nativeType, TypeToken<M> toType) {
        List<TypeToken<?>> args = getTypeArguments(toType);
        TypeToken<?> keyType = args.size() > 0 ? args.get(0) : TypeToken.of(Object.class);
        TypeToken<?> valueType = args.size() > 1 ? args.get(1) : TypeToken.of(Object.class);

        M newMap = constructor.get();
        nativeType.forEach((nativeKey, nativeValue) -> {
            Object wrappedKey = getConversionService().wrap(nativeKey, keyType);
            Object wrappedValue = getConversionService().wrap(nativeValue, valueType);
            newMap.put(wrappedKey, wrappedValue);
        });
        return done(newMap);
    }

    @Override
    public ConversionResult<M> unwrap(M platformImplType, TypeToken<M> fromType) {
        List<TypeToken<?>> args = getTypeArguments(fromType);
        TypeToken<?> keyType = args.size() > 0 ? args.get(0) : TypeToken.of(Object.class);
        TypeToken<?> valueType = args.size() > 1 ? args.get(1) : TypeToken.of(Object.class);

        M newMap = constructor.get();
        platformImplType.forEach((wrappedKey, wrappedValue) -> {
            Object nativeKey = getConversionService().unwrap(wrappedKey, keyType);
            Object nativeValue = getConversionService().unwrap(wrappedValue, valueType);
            newMap.put(nativeKey, nativeValue);
        });
        return done(newMap);
    }

    @Override
    public Class<M> apiImplementationClass() {
        return mapType;
    }

    @Override
    public Class<M> nativeMinecraftType() {
        return mapType;
    }
}
