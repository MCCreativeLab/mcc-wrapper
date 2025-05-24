package de.verdox.mccreativelab.conversion.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;

import java.util.HashMap;
import java.util.List;
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
    public ConversionResult<Map.Entry> wrap(Map.Entry nativeType, TypeToken<Map.Entry> toType) {
        List<TypeToken<?>> args = getTypeArguments(toType);
        TypeToken<?> keyType = args.size() > 0 ? args.get(0) : TypeToken.of(Object.class);
        TypeToken<?> valueType = args.size() > 1 ? args.get(1) : TypeToken.of(Object.class);

        Object wrappedKey = getConversionService().wrap(nativeType.getKey(), keyType);
        Object wrappedValue = getConversionService().wrap(nativeType.getValue(), valueType);

        Map<Object, Object> map = new HashMap<>();
        map.put(wrappedKey, wrappedValue);
        return done(map.entrySet().iterator().next());
    }

    @Override
    public ConversionResult<Map.Entry> unwrap(Map.Entry platformImplType, TypeToken<Map.Entry> fromType) {
        List<TypeToken<?>> args = getTypeArguments(fromType);
        TypeToken<?> keyType = args.size() > 0 ? args.get(0) : TypeToken.of(Object.class);
        TypeToken<?> valueType = args.size() > 1 ? args.get(1) : TypeToken.of(Object.class);

        Object nativeKey = getConversionService().unwrap(platformImplType.getKey(), keyType);
        Object nativeValue = getConversionService().unwrap(platformImplType.getValue(), valueType);

        Map<Object, Object> map = new HashMap<>();
        map.put(nativeKey, nativeValue);
        return done(map.entrySet().iterator().next());
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
