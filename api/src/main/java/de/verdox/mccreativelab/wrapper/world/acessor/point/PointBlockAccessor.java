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
        return getChunk().getBlockTypeAt(x(), y(), z());
    }

    /**
     * Returns the block data of this block.
     */
    default MCCBlockState getBlockState() {
        return getChunk().getBlockDataAt(x(), y(), z());
    }

    /**
     * Sets the block type
     */
    default void setBlockType(MCCBlockType mccBlockType, boolean triggerBlockUpdate) {
        getChunk().setBlock(mccBlockType, x(), y(), z(), triggerBlockUpdate);
    }

    /**
     * Sets the block type
     */
    default void setBlockState(MCCBlockState mccBlockState, boolean triggerBlockUpdate) {
        getChunk().setBlock(mccBlockState, x(), y(), z(), triggerBlockUpdate);
    }

    /**
     * Returns the location of this block
     *
     * @return the location of the block
     */
    MCCLocation getLocation();

    @Override
    default boolean canAccess(int x, int y, int z) {
        return getChunk().canAccess(x, y, z) && x() == x && y() == y && z() == z;
    }

    @Override
    default boolean canAccess(MCCLocation mccLocation) {
        return getChunk().canAccess(mccLocation) && x() == mccLocation.blockX() && y() == mccLocation.blockY() && z() == mccLocation.blockZ();
    }
}
