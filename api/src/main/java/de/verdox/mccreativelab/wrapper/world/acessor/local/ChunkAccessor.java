package de.verdox.mccreativelab.wrapper.world.acessor.local;

import de.verdox.mccreativelab.wrapper.world.acessor.Accessor;
import de.verdox.mccreativelab.wrapper.world.acessor.global.WorldAccessor;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCChunkPos;

/**
 * Represents an accessor that has local read and write rights over its elements.
 * All elements can be accessed without blocking loads
 */
public interface ChunkAccessor<
        CHUNK_ACCESS extends ChunkAccessor<CHUNK_ACCESS, WORLD_ACCESS>,
        WORLD_ACCESS extends WorldAccessor<WORLD_ACCESS, CHUNK_ACCESS>
        > extends Accessor {

    WORLD_ACCESS getWorld();

    MCChunkPos getChunkPos();
}
