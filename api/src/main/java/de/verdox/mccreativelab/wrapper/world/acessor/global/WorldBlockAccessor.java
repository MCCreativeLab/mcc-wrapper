package de.verdox.mccreativelab.wrapper.world.acessor.global;

import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkBlockAccessor;
import de.verdox.mccreativelab.wrapper.world.acessor.point.PointBlockAccessor;
import de.verdox.mccreativelab.wrapper.world.coordinates.Pos;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public interface WorldBlockAccessor<
        SELF extends WorldBlockAccessor<SELF, CHUNK_ACCESS, POINT_ACCESS>,
        CHUNK_ACCESS extends ChunkBlockAccessor<CHUNK_ACCESS, SELF, POINT_ACCESS>,
        POINT_ACCESS extends PointBlockAccessor<POINT_ACCESS, SELF, CHUNK_ACCESS>
        > extends WorldAccessor<SELF, CHUNK_ACCESS> {

    default <T> CompletableFuture<T> at(Pos<?> pos, Function<POINT_ACCESS, T> read) {
        checkAccess(pos);

        return atChunk(pos, chunkAccess -> {
            return read.apply(chunkAccess.get(pos));
        });
    }

    default CompletableFuture<Void> at(Pos<?> pos, Consumer<POINT_ACCESS> doSomething) {
        checkAccess(pos);

        return atChunk(pos, chunkAccess -> {
            doSomething.accept(chunkAccess.get(pos));
        });
    }
}
