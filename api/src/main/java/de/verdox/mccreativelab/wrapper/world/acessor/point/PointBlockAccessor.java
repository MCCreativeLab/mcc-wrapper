package de.verdox.mccreativelab.wrapper.world.acessor.point;

import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.acessor.global.WorldBlockAccessor;
import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkBlockAccessor;

public interface PointBlockAccessor<
        SELF extends PointBlockAccessor<SELF, WORLD_ACCESS, CHUNK_ACCESS>,
        WORLD_ACCESS extends WorldBlockAccessor<WORLD_ACCESS, CHUNK_ACCESS, SELF>,
        CHUNK_ACCESS extends ChunkBlockAccessor<CHUNK_ACCESS, WORLD_ACCESS, SELF>
        >
        extends PointAccessor<WORLD_ACCESS, CHUNK_ACCESS> {
    /**
     * Returns the block type of this block.
     */
    default MCCBlockType getBlockType() {
        return getChunk().getBlockTypeAt(getPos());
    }

    /**
     * Returns the block data of this block.
     */
    default MCCBlockState getBlockState() {
        return getChunk().getBlockDataAt(getPos());
    }

    /**
     * Sets the block type
     */
    default void setBlockType(MCCBlockType mccBlockType, boolean triggerBlockUpdate) {
        getChunk().setBlock(mccBlockType, getPos(), triggerBlockUpdate);
    }

    /**
     * Sets the block type
     */
    default void setBlockState(MCCBlockState mccBlockState, boolean triggerBlockUpdate) {
        getChunk().setBlock(mccBlockState, getPos(), triggerBlockUpdate);
    }

    /**
     * Returns the location of this block
     *
     * @return the location of the block
     */
    MCCLocation getLocation();
}
