package de.verdox.mccreativelab.impl.paper.platform.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import org.bukkit.World;
import org.bukkit.block.Block;

class BukkitBlockConverter implements MCCConverter<Block, MCCBlock> {

    @Override
    public ConversionResult<MCCBlock> wrap(Block nativeType) {
        return done(new MCCBlock(BukkitAdapter.wrap(nativeType.getLocation()), BukkitAdapter.wrap(nativeType.getChunk(), new TypeToken<>() {})));
    }

    @Override
    public ConversionResult<Block> unwrap(MCCBlock platformImplType) {
        return done(BukkitAdapter.unwrap(platformImplType.getLocation().world(), new TypeToken<World>() {}).getBlockAt(BukkitAdapter.unwrap(platformImplType.getLocation())));
    }

    @Override
    public Class<MCCBlock> apiImplementationClass() {
        return MCCBlock.class;
    }

    @Override
    public Class<Block> nativeMinecraftType() {
        return Block.class;
    }
}
