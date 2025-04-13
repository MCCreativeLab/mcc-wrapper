package de.verdox.mccreativelab.impl.vanilla.platform.converter;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.world.MCCInteractionResult;
import net.minecraft.world.InteractionResult;

public class InteractionResultConverter implements MCCConverter<InteractionResult, MCCInteractionResult> {
    @Override
    public ConversionResult<MCCInteractionResult> wrap(InteractionResult nativeType) {
        if (nativeType.equals(InteractionResult.PASS)) {
            return done(MCCInteractionResult.PASS);
        } else if (nativeType.equals(InteractionResult.FAIL)) {
            return done(MCCInteractionResult.FAIL);
        } else if (nativeType.equals(InteractionResult.CONSUME)) {
            return done(MCCInteractionResult.CONSUME);
        } else if (nativeType.equals(InteractionResult.SUCCESS)) {
            return done(MCCInteractionResult.SUCCESS);
        } else if (nativeType.equals(InteractionResult.SUCCESS_SERVER)) {
            return done(MCCInteractionResult.SUCCESS_SERVER);
        } else if (nativeType.equals(InteractionResult.TRY_WITH_EMPTY_HAND)) {
            return done(MCCInteractionResult.TRY_WITH_EMPTY_HAND);
        }
        throw new IllegalArgumentException("Unknown native interaction result");
    }

    @Override
    public ConversionResult<InteractionResult> unwrap(MCCInteractionResult platformImplType) {
        if (platformImplType.equals(MCCInteractionResult.PASS)) {
            return done(InteractionResult.PASS);
        } else if (platformImplType.equals(MCCInteractionResult.FAIL)) {
            return done(InteractionResult.FAIL);
        } else if (platformImplType.equals(MCCInteractionResult.CONSUME)) {
            return done(InteractionResult.CONSUME);
        } else if (platformImplType.equals(MCCInteractionResult.SUCCESS)) {
            return done(InteractionResult.SUCCESS);
        } else if (platformImplType.equals(MCCInteractionResult.SUCCESS_SERVER)) {
            return done(InteractionResult.SUCCESS_SERVER);
        } else if (platformImplType.equals(MCCInteractionResult.TRY_WITH_EMPTY_HAND)) {
            return done(InteractionResult.TRY_WITH_EMPTY_HAND);
        }
        throw new IllegalArgumentException("Unknown api interaction result");
    }

    @Override
    public Class<MCCInteractionResult> apiImplementationClass() {
        return MCCInteractionResult.class;
    }

    @Override
    public Class<InteractionResult> nativeMinecraftType() {
        return InteractionResult.class;
    }
}
