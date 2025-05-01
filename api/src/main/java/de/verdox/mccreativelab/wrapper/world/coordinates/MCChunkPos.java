package de.verdox.mccreativelab.wrapper.world.coordinates;

import de.verdox.mccreativelab.wrapper.util.math.MathUtil;

public record MCChunkPos(int x, int z) implements Pos<MCChunkPos> {

    public MCChunkPos(int value) {
        this(value, value); // Wenn nur ein Wert übergeben wird, wird er für beide Koordinaten verwendet
    }

    @Override
    public MCChunkPos add(MCChunkPos vector) {
        return new MCChunkPos(x + vector.x, z + vector.z);
    }

    public MCChunkPos add(int value) {
        return new MCChunkPos(x + value, z + value);
    }

    @Override
    public MCChunkPos sub(MCChunkPos vector) {
        return new MCChunkPos(x - vector.x, z - vector.z);
    }

    public MCChunkPos sub(int value) {
        return new MCChunkPos(x - value, z - value);
    }

    @Override
    public MCChunkPos mul(MCChunkPos vector) {
        return new MCChunkPos(x * vector.x, z * vector.z);
    }

    public MCChunkPos mul(int value) {
        return new MCChunkPos(x * value, z * value);
    }

    @Override
    public MCChunkPos div(MCChunkPos vector) {
        return new MCChunkPos(x / vector.x, z / vector.z);
    }

    public MCChunkPos div(int value) {
        return new MCChunkPos(x / value, z / value);
    }

    @Override
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    @Override
    public double lengthSquared() {
        return MathUtil.square(x) + MathUtil.square(z);
    }

    @Override
    public double distance(MCChunkPos vector) {
        return Math.sqrt(distanceSquared(vector));
    }

    @Override
    public double distanceSquared(MCChunkPos vector) {
        return MathUtil.square(x - vector.x) + MathUtil.square(z - vector.z);
    }

    @Override
    public double dot(MCChunkPos vector) {
        return x * vector.x + z * vector.z;
    }

    @Override
    public MCChunkPos crossProduct(MCChunkPos vector) {
        // Da es 2D ist, das Cross-Produkt ergibt nur eine Z-Komponente.
        int newZ = x * vector.z - z * vector.x;
        return new MCChunkPos(0, newZ); // 2D Cross-Produkt ergibt nur die Z-Komponente
    }

    @Override
    public boolean isInAABB(MCChunkPos min, MCChunkPos max) {
        return x >= min.x && x <= max.x && z >= min.z && z <= max.z;
    }

    @Override
    public boolean isInSphere(MCChunkPos origin, double radius) {
        return distanceSquared(origin) <= MathUtil.square(radius);
    }

    @Override
    public MCPos toPos() {
        return toBlockPos().toPos();
    }

    @Override
    public MCBlockPos toBlockPos() {
        return new MCBlockPos(x() * 16, 0, z() * 16);
    }
}
