package de.verdox.mccreativelab.wrapper.world.coordinates;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;

public interface Pos<V extends Pos<V>> {
    double EPSILON = 0.000001;

    V add(V vector);

    V sub(V vector);

    V mul(V vector);

    V div(V vector);

    double length();

    double lengthSquared();

    double distance(V vector);

    double distanceSquared(V vector);

    double dot(V vector);

    V crossProduct(V vector);

    boolean isInAABB(V min, V max);

    boolean isInSphere(V origin, double radius);

    default MCLocalBlockPos toLocalPos() {
        MCBlockPos blockPos = toBlockPos();
        var localX = Math.floorMod(blockPos.x(), 16);
        var localY = Math.floorMod(blockPos.y(), 16);
        var localZ = Math.floorMod(blockPos.z(), 16);
        return new MCLocalBlockPos(localX, localY, localZ);
    }

    default MCChunkPos toChunkPos() {
        MCBlockPos blockPos = toBlockPos();
        var chunkX = blockPos.x() >> 4;
        var chunkZ = blockPos.z() >> 4;
        return new MCChunkPos(chunkX, chunkZ);
    }

    MCPos toPos();

    MCBlockPos toBlockPos();
}
