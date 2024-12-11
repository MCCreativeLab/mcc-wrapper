package de.verdox.mccreativelab.conversion.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.TypeUtil;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

import java.util.Optional;

public class OptionalConverter implements MCCConverter<Optional, Optional> {

    @Override
    public ConversionResult<Optional> wrap(Optional nativeType, TypeToken<Optional> tokenToConvertTo) {
        if (nativeType.isEmpty())
            return done(Optional.empty());

        TypeToken<?> valueType = TypeUtil.extractGeneric(tokenToConvertTo, 0);

        Object o = nativeType.get();
        return done(Optional.of(MCCPlatform.getInstance().getConversionService().wrap(o, valueType)));
    }

    @Override
    public ConversionResult<Optional> unwrap(Optional platformImplType, TypeToken<Optional> tokenToConvertTo) {
        if (platformImplType.isEmpty())
            return done(Optional.empty());

        TypeToken<?> valueType = TypeUtil.extractGeneric(tokenToConvertTo, 0);

        Object o = platformImplType.get();
        return done(Optional.of(MCCPlatform.getInstance().getConversionService().unwrap(o, valueType)));
    }

    @Override
    public Class<Optional> apiImplementationClass() {
        return Optional.class;
    }

    @Override
    public Class<Optional> nativeMinecraftType() {
        return Optional.class;
    }
}
