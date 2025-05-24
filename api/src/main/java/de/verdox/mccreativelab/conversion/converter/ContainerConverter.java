package de.verdox.mccreativelab.conversion.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;

import java.util.Objects;

/**
 * A converter that converts objects that contain objects that are converted.
 * The containers are duplicated and filled with the converted objects.
 * @param <F> the native type
 * @param <T> the api type
 */
public abstract class ContainerConverter<F, T> implements MCCConverter<F, T> {
    private final ConversionService conversionService;

    public ContainerConverter(ConversionService conversionService) {
        Objects.requireNonNull(conversionService);
        this.conversionService = conversionService;
    }


    /**
     * Returns the conversion service that should be used to convert the values of the containers
     * @return the conversion service
     */
    public ConversionService getConversionService() {
        return conversionService;
    }
}
