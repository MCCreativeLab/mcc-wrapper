package de.verdox.mccreativelab.wrapper.world.acessor.global;

import de.verdox.mccreativelab.wrapper.world.acessor.Accessor;
import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkAccessor;
import de.verdox.mccreativelab.wrapper.world.coordinates.Pos;
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
     * Gets or loads a chunk at specified chunk coordinates
     */
    CompletableFuture<CHUNK_ACCESS> getOrLoadChunk(Pos<?> pos);

    /**
     * Gets a chunk if it is loaded and available at specified chunk coordinates
     */
    @Nullable
    CHUNK_ACCESS getChunkImmediately(Pos<?> pos);

    /**
     * Used to read something from a chunk at the specified coordinates
     */
    default <T> CompletableFuture<T> atChunk(Pos<?> pos, Function<CHUNK_ACCESS, T> read) {
        return getOrLoadChunk(pos).thenApply(read);
    }

    /**
     * Used to do something at the specified coordinates
     */
    default CompletableFuture<Void> atChunk(Pos<?> pos, Consumer<CHUNK_ACCESS> doSomething) {
        return getOrLoadChunk(pos).thenAcceptAsync(doSomething);
    }
}
