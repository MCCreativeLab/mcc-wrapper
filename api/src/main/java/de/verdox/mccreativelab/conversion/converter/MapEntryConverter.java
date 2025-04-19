package de.verdox.mccreativelab.conversion.converter;

import de.verdox.mccreativelab.conversion.ConversionService;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to convert map entries. Same logic as {@link MapConverter}
 */
public class MapEntryConverter extends ContainerConverter<Map.Entry, Map.Entry> {
    public MapEntryConverter(ConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public ConversionResult<Map.Entry> wrap(Map.Entry nativeType) {
        Object wrappedKey = getConversionService().wrap(nativeType.getKey());
        Object wrappedValue = getConversionService().wrap(nativeType.getValue());
        Map<Object, Object> map = new HashMap<>();
        map.put(wrappedKey, wrappedValue);
        return done(map.entrySet().stream().findAny().orElseThrow());
    }

    @Override
    public ConversionResult<Map.Entry> unwrap(Map.Entry platformImplType) {
        Object unWrappedKey = getConversionService().unwrap(platformImplType.getKey());
        Object unWrappedValue = getConversionService().unwrap(platformImplType.getValue());
        Map<Object, Object> map = new HashMap<>();
        map.put(unWrappedKey, unWrappedValue);
        return done(map.entrySet().stream().findAny().orElseThrow());
    }

    @Override
    public Class<Map.Entry> apiImplementationClass() {
        return Map.Entry.class;
    }

    @Override
    public Class<Map.Entry> nativeMinecraftType() {
        return Map.Entry.class;
    }
}
