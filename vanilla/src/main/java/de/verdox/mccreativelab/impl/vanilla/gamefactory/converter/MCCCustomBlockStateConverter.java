package de.verdox.mccreativelab.impl.vanilla.gamefactory.converter;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.block.MCCBlockStateMapping;
import de.verdox.mccreativelab.gamefactory.block.MCCCustomBlockState;
import de.verdox.mccreativelab.impl.vanilla.block.NMSBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class MCCCustomBlockStateConverter implements MCCConverter<BlockState, MCCCustomBlockState> {

    @Override
    public ConversionResult<MCCCustomBlockState> wrap(BlockState nativeType) {
        var found = MCCBlockStateMapping.getInstance().getCustomBlockState(new NMSBlockState(nativeType));

        return found != null ? done(found) : notDone(null);
    }

    @Override
    public ConversionResult<BlockState> unwrap(MCCCustomBlockState platformImplType) {
        MCCBlockState state = MCCBlockStateMapping.getInstance().getVanillaMapping(platformImplType);
        Objects.requireNonNull(state, "The custom block state "+platformImplType.toBlockDataString()+" was not mapped to a vanilla state.");
        return done(MCCPlatform.getInstance().getConversionService().unwrap(state));
    }

    @Override
    public Class<MCCCustomBlockState> apiImplementationClass() {
        return MCCCustomBlockState.class;
    }

    @Override
    public Class<BlockState> nativeMinecraftType() {
        return BlockState.class;
    }
}
