package de.verdox.mccreativelab.conversion.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;

import java.util.List;
import java.util.Optional;

/**
 * Converts optionals. It unwraps an optional and tries to convert its value.
 */
public class OptionalConverter extends ContainerConverter<Optional, Optional> {
    public OptionalConverter(ConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public ConversionResult<Optional> wrap(Optional nativeType) {
        if (nativeType.isEmpty())
            return done(Optional.empty());

        Object o = nativeType.get();
        return done(Optional.of(getConversionService().wrap(o)));
    }

    @Override
    public ConversionResult<Optional> unwrap(Optional platformImplType) {
        if (platformImplType.isEmpty())
            return done(Optional.empty());

        Object o = platformImplType.get();
        return done(Optional.of(getConversionService().unwrap(o)));
    }

    @Override
    public ConversionResult<Optional> wrap(Optional nativeType, TypeToken<Optional> toType) {
        List<TypeToken<?>> args = getTypeArguments(toType);
        TypeToken<?> valueType = args.isEmpty() ? TypeToken.of(Object.class) : args.get(0);

        if (nativeType.isEmpty()) {
            return done(Optional.empty());
        }

        Object wrapped = getConversionService().wrap(nativeType.get(), valueType);
        return done(Optional.ofNullable(wrapped));
    }

    @Override
    public ConversionResult<Optional> unwrap(Optional platformImplType, TypeToken<Optional> fromType) {
        List<TypeToken<?>> args = getTypeArguments(fromType);
        TypeToken<?> valueType = args.isEmpty() ? TypeToken.of(Object.class) : args.get(0);

        if (platformImplType.isEmpty()) {
            return done(Optional.empty());
        }

        Object unwrapped = getConversionService().unwrap(platformImplType.get(), valueType);
        return done(Optional.ofNullable(unwrapped));
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
