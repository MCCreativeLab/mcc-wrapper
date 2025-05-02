package de.verdox.mccreativelab.wrapper.world.coordinates;

import de.verdox.mccreativelab.wrapper.util.math.MathUtil;

public record MCLocalBlockPos(int localX, int globalY, int localZ) {
    public MCLocalBlockPos(int value) {
        this(value, value, value);
    }


    public MCLocalBlockPos add(MCLocalBlockPos vector) {
        return new MCLocalBlockPos(
                (localX + vector.localX) & 15,
                globalY + vector.globalY,
                (localZ + vector.localZ) & 15
        );
    }

    public MCLocalBlockPos add(int value) {
        return new MCLocalBlockPos(
                (localX + value) & 15,
                globalY + value,
                (localZ + value) & 15
        );
    }


    public MCLocalBlockPos sub(MCLocalBlockPos vector) {
        return new MCLocalBlockPos(
                (localX - vector.localX + 16) & 15,
                globalY - vector.globalY,
                (localZ - vector.localZ + 16) & 15
        );
    }

    public MCLocalBlockPos sub(int value) {
        return new MCLocalBlockPos(
                (localX - value + 16) & 15,
                globalY - value,
                (localZ - value + 16) & 15
        );
    }


    public MCLocalBlockPos mul(MCLocalBlockPos vector) {
        return new MCLocalBlockPos(
                (localX * vector.localX) & 15,
                globalY * vector.globalY,
                (localZ * vector.localZ) & 15
        );
    }

    public MCLocalBlockPos mul(int value) {
        return new MCLocalBlockPos(
                (localX * value) & 15,
                globalY * value,
                (localZ * value) & 15
        );
    }


    public MCLocalBlockPos div(MCLocalBlockPos vector) {
        return new MCLocalBlockPos(
                localX / vector.localX,
                globalY / vector.globalY,
                localZ / vector.localZ
        );
    }

    public MCLocalBlockPos div(int value) {
        return new MCLocalBlockPos(
                localX / value,
                globalY / value,
                localZ / value
        );
    }


    public double length() {
        return Math.sqrt(lengthSquared());
    }


    public double lengthSquared() {
        return MathUtil.square(localX) + MathUtil.square(globalY) + MathUtil.square(localZ);
    }


    public double distance(MCLocalBlockPos vector) {
        return Math.sqrt(distanceSquared(vector));
    }


    public double distanceSquared(MCLocalBlockPos vector) {
        return MathUtil.square(localX - vector.localX) + MathUtil.square(globalY - vector.globalY) + MathUtil.square(localZ - vector.localZ);
    }


    public MCLocalBlockPos toLocalPos() {
        return this;
    }


    public MCPos toPos(MCChunkPos chunkPos) {
        return toBlockPos(chunkPos).toPos();
    }


    public MCBlockPos toBlockPos(MCChunkPos chunkPos) {
        return new MCBlockPos(chunkPos.x() + localX, globalY, chunkPos.z() + localZ);
    }
}
