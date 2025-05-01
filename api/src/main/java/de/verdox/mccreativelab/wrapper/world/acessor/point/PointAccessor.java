package de.verdox.mccreativelab.wrapper.world.acessor.point;

import de.verdox.mccreativelab.wrapper.world.acessor.Accessor;
import de.verdox.mccreativelab.wrapper.world.acessor.global.WorldAccessor;
import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkAccessor;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCPos;

/**
 * Has access to a specific point
 */
public interface PointAccessor<
        WORLD_ACCESS extends WorldAccessor<WORLD_ACCESS, CHUNK_ACCESS>,
        CHUNK_ACCESS extends ChunkAccessor<CHUNK_ACCESS, WORLD_ACCESS>
        > extends Accessor {

    MCPos getPos();

    /**
     * Returns the chunk of this point
     */
    CHUNK_ACCESS getChunk();

    /**
     * Returns the world of this point
     */
    WORLD_ACCESS getWorld();
}
