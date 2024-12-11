package de.verdox.mccreativelab.conversion.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.TypeUtil;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

import java.util.HashMap;
import java.util.Map;

public class MapEntryConverter implements MCCConverter<Map.Entry, Map.Entry> {

    @Override
    public ConversionResult<Map.Entry> wrap(Map.Entry nativeType, TypeToken<Map.Entry> tokenToConvertTo) {

        TypeToken<?> keyType = TypeUtil.extractGeneric(tokenToConvertTo, 0);
        TypeToken<?> valueType = TypeUtil.extractGeneric(tokenToConvertTo, 1);

        Object wrappedKey = MCCPlatform.getInstance().getConversionService().wrap(nativeType.getKey(), keyType);
        Object wrappedValue = MCCPlatform.getInstance().getConversionService().wrap(nativeType.getValue(), valueType);
        Map<Object, Object> map = new HashMap<>();
        map.put(wrappedKey, wrappedValue);
        return done(map.entrySet().stream().findAny().orElseThrow());
    }

    @Override
    public ConversionResult<Map.Entry> unwrap(Map.Entry platformImplType, TypeToken<Map.Entry> tokenToConvertTo) {

        TypeToken<?> keyType = TypeUtil.extractGeneric(tokenToConvertTo, 0);
        TypeToken<?> valueType = TypeUtil.extractGeneric(tokenToConvertTo, 1);

        Object unWrappedKey = MCCPlatform.getInstance().getConversionService().unwrap(platformImplType.getKey(), keyType);
        Object unWrappedValue = MCCPlatform.getInstance().getConversionService().unwrap(platformImplType.getValue(), valueType);
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
