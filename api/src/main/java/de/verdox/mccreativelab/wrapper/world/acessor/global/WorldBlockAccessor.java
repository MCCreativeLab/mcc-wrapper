package de.verdox.mccreativelab.wrapper.world.acessor.global;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkBlockAccessor;
import de.verdox.mccreativelab.wrapper.world.acessor.point.PointBlockAccessor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public interface WorldBlockAccessor<
        SELF extends WorldBlockAccessor<SELF, CHUNK_ACCESS, POINT_ACCESS>,
        CHUNK_ACCESS extends ChunkBlockAccessor<CHUNK_ACCESS, SELF, POINT_ACCESS>,
        POINT_ACCESS extends PointBlockAccessor<POINT_ACCESS, SELF, CHUNK_ACCESS>
        > extends WorldAccessor<SELF, CHUNK_ACCESS> {

    default <T> CompletableFuture<T> at(double globalX, double globalY, double globalZ, Function<POINT_ACCESS, T> read) {
        checkAccess(globalX, globalY, globalZ);

        return atChunk(globalX, globalY, globalZ, l -> {
            return read.apply(l.get(globalX, globalY, globalZ));
        });
    }

    default CompletableFuture<Void> at(double globalX, double globalY, double globalZ, Consumer<POINT_ACCESS> doSomething) {
        checkAccess(globalX, globalY, globalZ);

        return atChunk(globalX, globalY, globalZ, l -> {
            doSomething.accept(l.get(globalX, globalY, globalZ));
        });
    }

    default <T> CompletableFuture<T> at(MCCLocation mccLocation, Function<POINT_ACCESS, T> read) {
        checkAccess(mccLocation);

        return at(mccLocation.blockX(), mccLocation.blockY(), mccLocation.blockZ(), read);
    }

    default CompletableFuture<Void> at(MCCLocation mccLocation, Consumer<POINT_ACCESS> doSomething) {
        checkAccess(mccLocation);
        return at(mccLocation.blockX(), mccLocation.blockY(), mccLocation.blockZ(), doSomething);
    }
}
