package de.verdox.mccreativelab.conversion.converter;

/**
 * Used to convert between two enum types that share the same constants (same name)
 * @param <F> the native type
 * @param <A> the api type
 */
public class EnumConverter<F extends Enum<F>, A extends Enum<A>> implements MCCConverter<F, A> {
    private final Class<F> nativeEnum;
    private final Class<A> apiEnum;

    public EnumConverter(Class<F> nativeEnum, Class<A> apiEnum) {
        this.nativeEnum = nativeEnum;
        this.apiEnum = apiEnum;
    }

    @Override
    public ConversionResult<A> wrap(F nativeType) {
        return done(Enum.valueOf(apiEnum, nativeType.name()));
    }

    @Override
    public ConversionResult<F> unwrap(A platformImplType) {
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

    @Override
    public String toString() {
        return toReadableString();
    }
}
