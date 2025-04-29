package de.verdox.mccreativelab.wrapper.world.acessor.global;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.acessor.Accessor;
import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents an accessor that has async access to local accessors
 */
public interface WorldAccessor<
        WORLD_ACCESS extends WorldAccessor<WORLD_ACCESS, CHUNK_ACCESS>,
        CHUNK_ACCESS extends ChunkAccessor<CHUNK_ACCESS, WORLD_ACCESS>
        > extends Accessor {
    /**
     * Gets or loads a chunk at specified world coordinates
     */
    default CompletableFuture<CHUNK_ACCESS> getOrLoadChunk(int globalX, int globalY, int globalZ) {
        return getOrLoadChunk(MCCLocation.calculateChunkX(globalX), MCCLocation.calculateChunkZ(globalZ));
    }

    /**
     * Gets or loads a chunk at specified chunk coordinates
     */
    CompletableFuture<CHUNK_ACCESS> getOrLoadChunk(int chunkX, int chunkZ);

    /**
     * Gets a chunk if it is loaded and available at specified chunk coordinates
     */
    @Nullable
    CHUNK_ACCESS getChunkImmediately(int chunkX, int chunkZ);

    /**
     * Gets a chunk if it is loaded and available at a specified location
     */
    @Nullable
    default CHUNK_ACCESS getChunkImmediately(MCCLocation mccLocation) {
        checkAccess(mccLocation);
        return getChunkImmediately(mccLocation.blockX(), mccLocation.blockY(), mccLocation.blockZ());
    }


    /**
     * Gets or loads a chunk at a specified location
     */
    default CompletableFuture<CHUNK_ACCESS> getOrLoadChunk(MCCLocation mccLocation) {
        checkAccess(mccLocation);
        return getOrLoadChunk(mccLocation.blockX(), mccLocation.blockY(), mccLocation.blockZ());
    }

    /**
     * Gets a chunk if it is loaded and available at specified world coordinates
     */
    @Nullable
    default CHUNK_ACCESS getChunkImmediately(int globalX, int globalY, int globalZ) {
        return getChunkImmediately(MCCLocation.calculateChunkX(globalX), MCCLocation.calculateChunkZ(globalZ));
    }

    /**
     * Used to read something from a chunk at the specified coordinates
     */
    default <T> CompletableFuture<T> atChunk(int chunkX, int chunkZ, Function<CHUNK_ACCESS, T> read) {
        return getOrLoadChunk(chunkX, chunkZ).thenApply(read);
    }

    /**
     * Used to do something at the specified coordinates
     */
    default CompletableFuture<Void> atChunk(int chunkX, int chunkZ, Consumer<CHUNK_ACCESS> doSomething) {
        return getOrLoadChunk(chunkX, chunkZ).thenAcceptAsync(doSomething);
    }

    /**
     * Used to read something from a chunk at the specified coordinates
     */
    default <T> CompletableFuture<T> atChunk(MCCLocation mccLocation, Function<CHUNK_ACCESS, T> read) {
        checkAccess(mccLocation);
        return atChunk(mccLocation.blockX(), mccLocation.blockY(), mccLocation.blockZ(), read);
    }

    /**
     * Used to do something at the specified coordinates
     */
    default CompletableFuture<Void> atChunk(MCCLocation mccLocation, Consumer<CHUNK_ACCESS> doSomething) {
        checkAccess(mccLocation);
        return atChunk(mccLocation.blockX(), mccLocation.blockY(), mccLocation.blockZ(), doSomething);
    }

    /**
     * Used to read something from a chunk at the specified coordinates
     */
    default <T> CompletableFuture<T> atChunk(int globalX, int globalY, int globalZ, Function<CHUNK_ACCESS, T> read) {
        return atChunk(MCCLocation.calculateChunkX(globalX), MCCLocation.calculateChunkZ(globalZ), read);
    }

    /**
     * Used to do something at the specified coordinates
     */
    default CompletableFuture<Void> atChunk(int globalX, int globalY, int globalZ, Consumer<CHUNK_ACCESS> doSomething) {
        return atChunk(MCCLocation.calculateChunkX(globalX), MCCLocation.calculateChunkZ(globalZ), doSomething);
    }
}
