package de.verdox.mccreativelab.wrapper.world.coordinates;

import de.verdox.mccreativelab.wrapper.util.math.MathUtil;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;

public record MCBlockPos(int x, int y, int z) implements Pos<MCBlockPos> {

    public MCBlockPos(int value) {
        this(value, value, value);
    }

    @Override
    public MCBlockPos add(MCBlockPos vector) {
        return new MCBlockPos(x + vector.x, y + vector.y, z + vector.z);
    }

    public MCBlockPos add(int value) {
        return new MCBlockPos(x + value, y + value, z + value);
    }

    @Override
    public MCBlockPos sub(MCBlockPos vector) {
        return new MCBlockPos(x - vector.x, y - vector.y, z - vector.z);
    }

    @Override
    public MCBlockPos mul(MCBlockPos vector) {
        return new MCBlockPos(x * vector.x, y * vector.y, z * vector.z);
    }

    public MCBlockPos mul(int value) {
        return new MCBlockPos(x * value, y * value, z * value);
    }

    @Override
    public MCBlockPos div(MCBlockPos vector) {
        return new MCBlockPos(x / vector.x, y / vector.y, z / vector.z);
    }

    public MCBlockPos div(int value) {
        return new MCBlockPos(x / value, y / value, z / value);
    }

    @Override
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    @Override
    public double lengthSquared() {
        return MathUtil.square(x) + MathUtil.square(y) + MathUtil.square(z);
    }

    @Override
    public double distance(MCBlockPos vector) {
        return Math.sqrt(distanceSquared(vector));
    }

    @Override
    public double distanceSquared(MCBlockPos vector) {
        return MathUtil.square(x - vector.x) + MathUtil.square(y - vector.y) + MathUtil.square(z - vector.z);
    }

    @Override
    public double dot(MCBlockPos vector) {
        return x * vector.x + y * vector.y + z * vector.z;
    }

    @Override
    public MCBlockPos crossProduct(MCBlockPos vector) {
        int newX = y * vector.z - z * vector.y;
        int newY = z * vector.x - x * vector.z;
        int newZ = x * vector.y - y * vector.x;
        return new MCBlockPos(newX, newY, newZ);
    }

    @Override
    public boolean isInAABB(MCBlockPos min, MCBlockPos max) {
        return x >= min.x && x <= max.x &&
                y >= min.y && y <= max.y &&
                z >= min.z && z <= max.z;
    }

    @Override
    public boolean isInSphere(MCBlockPos origin, double radius) {
        return distanceSquared(origin) <= MathUtil.square(radius);
    }

    @Override
    public MCPos toPos() {
        return new MCPos(x, y, z);
    }

    @Override
    public MCBlockPos toBlockPos() {
        return this;
    }

    public MCPos blockCenter() {
        return new MCPos(x + 0.5, y + 0.5, z + 0.5);
    }

    public MCCLocation toLocation(MCCWorld world) {
        return new MCCLocation(world, this.toPos());
    }
}
