package de.verdox.mccreativelab.conversion;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;

public class ApiToApiConverter<A1, A2> implements MCCConverter<A1, A2> {

    public ApiToApiConverter(){
        
    }

    @Override
    public ConversionResult<A2> wrap(A1 nativeType) {
        return null;
    }

    @Override
    public ConversionResult<A1> unwrap(A2 platformImplType) {
        return null;
    }

    @Override
    public Class<A2> apiImplementationClass() {
        return null;
    }

    @Override
    public Class<A1> nativeMinecraftType() {
        return null;
    }
}
