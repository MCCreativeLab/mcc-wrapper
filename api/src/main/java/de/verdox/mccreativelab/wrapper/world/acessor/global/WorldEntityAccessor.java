package de.verdox.mccreativelab.wrapper.world.acessor.global;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.entity.MCCTeleportFlag;
import de.verdox.mccreativelab.wrapper.world.MCCEntitySpawnReason;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkAccessor;
import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkEntityAccessor;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface WorldEntityAccessor<
        WORLD_ACCESS extends WorldEntityAccessor<WORLD_ACCESS, CHUNK_ACCESS>,
        CHUNK_ACCESS extends ChunkEntityAccessor<CHUNK_ACCESS, WORLD_ACCESS>
        > extends WorldAccessor<WORLD_ACCESS, CHUNK_ACCESS> {
    /**
     * Used to summon an entity at a specified location
     * @param location the location
     * @param mccEntityType the entity type
     * @return a future
     */
    default <T extends MCCEntity> CompletableFuture<T> summon(@NotNull MCCLocation location, @NotNull MCCEntityType<T> mccEntityType, @NotNull MCCEntitySpawnReason spawnReason) {
        checkAccess(location);
        return getOrLoadChunk(location).thenApply(chunkAccess -> chunkAccess.summon(location, mccEntityType, spawnReason));
    }

    /**
     * Teleports an entity to another location.
     *
     * @param location the location
     * @return the future that is completed when the teleportation is done
     */
    default CompletableFuture<Boolean> teleport(@NotNull MCCLocation location, @NotNull MCCEntity entity, MCCTeleportFlag... flags) {
        checkAccess(location);
        return getOrLoadChunk(location).thenApply(chunkAccess -> chunkAccess.teleport(location, entity, flags));
    }
}
