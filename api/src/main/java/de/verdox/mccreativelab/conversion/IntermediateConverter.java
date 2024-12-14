package de.verdox.mccreativelab.conversion;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;

/**
 * A converter that converts between an intermediate type I and an api type T by using an existing converter from F to an api type T.
 * The intermediate type is also backed by a native type
 *
 * @param <F> the native type
 * @param <I> the intermediate type
 * @param <T> the api type
 */
public class IntermediateConverter<F, I, T> implements MCCConverter<I, T> {

    private final MCCConverter<F, I> nativeToIntermediateConverter;
    private final MCCConverter<F, T> nativeToApiConverter;

    public IntermediateConverter(MCCConverter<F, I> nativeToIntermediateConverter, MCCConverter<F, T> nativeToApiConverter) {
        this.nativeToIntermediateConverter = nativeToIntermediateConverter;
        this.nativeToApiConverter = nativeToApiConverter;
    }

    @Override
    public ConversionResult<T> wrap(I nativeType) {
        ConversionResult<F> conversionResult = nativeToIntermediateConverter.unwrap(nativeType);
        if (!conversionResult.result().isDone() || conversionResult.value() == null) {
            return done(null);
        }
        return nativeToApiConverter.wrap(conversionResult.value());
    }

    @Override
    public ConversionResult<I> unwrap(T platformImplType) {
        ConversionResult<F> conversionResult = nativeToApiConverter.unwrap(platformImplType);
        if (!conversionResult.result().isDone() || conversionResult.value() == null) {
            return done(null);
        }
        F unwrappedNativeType = conversionResult.value();
        return nativeToIntermediateConverter.wrap(unwrappedNativeType);
    }

    @Override
    public Class<T> apiImplementationClass() {
        return nativeToApiConverter.apiImplementationClass();
    }

    @Override
    public Class<I> nativeMinecraftType() {
        return nativeToIntermediateConverter.apiImplementationClass();
    }
}
