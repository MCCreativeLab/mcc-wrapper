package de.verdox.mccreativelab.conversion.converter;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

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

    public MapConverter(Supplier<T> constructor, Class<M> mapType) {
        this.constructor = constructor;
        this.mapType = mapType;
    }

    @Override
    public ConversionResult<M> wrap(M nativeType) {
        M newMap = constructor.get();
        nativeType.forEach((nativeKey, nativeValue) -> {
            Object wrappedKey = MCCPlatform.getInstance().getConversionService().wrap(nativeKey);
            Object wrappedValue = MCCPlatform.getInstance().getConversionService().wrap(nativeValue);
            newMap.put(wrappedKey, wrappedValue);
        });
        return done(newMap);
    }

    @Override
    public ConversionResult<M> unwrap(M platformImplType) {
        M newMap = constructor.get();
        platformImplType.forEach((wrappedKey, wrappedValue) -> {
            Object nativeKey = MCCPlatform.getInstance().getConversionService().unwrap(wrappedKey);
            Object nativeValue = MCCPlatform.getInstance().getConversionService().unwrap(wrappedValue);
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
