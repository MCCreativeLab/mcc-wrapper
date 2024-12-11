package de.verdox.mccreativelab.conversion.converter;

import com.google.common.reflect.TypeToken;

public class EnumConverter<F extends Enum<F>, A extends Enum<A>> implements MCCConverter<F, A> {
    private final Class<F> nativeEnum;
    private final Class<A> apiEnum;

    public EnumConverter(Class<F> nativeEnum, Class<A> apiEnum) {
        this.nativeEnum = nativeEnum;
        this.apiEnum = apiEnum;
    }

    @Override
    public ConversionResult<A> wrap(F nativeType, TypeToken<A> tokenToConvertTo) {
        return done(Enum.valueOf(apiEnum, nativeType.name()));
    }

    @Override
    public ConversionResult<F> unwrap(A platformImplType, TypeToken<F> tokenToConvertTo) {
        return done(Enum.valueOf(nativeEnum, platformImplType.name()));
    }

    @Override
    public Class<A> apiImplementationClass() {
        return apiEnum;
    }

    @Override
    public Class<F> nativeMinecraftType() {
        return nativeEnum;
    }
}
