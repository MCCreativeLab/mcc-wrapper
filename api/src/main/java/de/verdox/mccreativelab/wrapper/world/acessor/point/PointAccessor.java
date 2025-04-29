package de.verdox.mccreativelab.wrapper.world.acessor.point;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.acessor.Accessor;
import de.verdox.mccreativelab.wrapper.world.acessor.global.WorldAccessor;
import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkAccessor;

/**
 * Has access to a specific point
 */
public interface PointAccessor<
        WORLD_ACCESS extends WorldAccessor<WORLD_ACCESS, CHUNK_ACCESS>,
        CHUNK_ACCESS extends ChunkAccessor<CHUNK_ACCESS, WORLD_ACCESS>
        > extends Accessor {
    /**
     * Returns the x coordinate of this point
     */
    int x();

    /**
     * Returns the y coordinate of this point
     */
    int y();

    /**
     * Returns the z coordinate of this point
     */
    int z();

    /**
     * Returns the chunk of this point
     */
    CHUNK_ACCESS getChunk();

    /**
     * Returns the world of this point
     */
    WORLD_ACCESS getWorld();

    @Override
    default boolean canAccess(MCCLocation mccLocation) {
        return getChunk().canAccess(mccLocation) && mccLocation.blockX() == x() && mccLocation.blockY() == y() && mccLocation.blockZ() == z();
    }
}
