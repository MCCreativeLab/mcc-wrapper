package de.verdox.mccreativelab.wrapper.world;

import java.util.Objects;

public class MCCVector {
    protected final double x;
    protected final double y;
    protected final double z;

    public MCCVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MCCLocation toLocation(MCCWorld world) {
        return new MCCLocation(world, x(), y(), z(), 90, 0);
    }

    public MCCVector add(int x, int y, int z) {
        return new MCCVector(x() + x, y() + y, z + z());
    }

    public MCCVector add(MCCVector vector) {
        return new MCCVector(x() + vector.x(), y() + vector.y(), z() + vector.z());
    }

    public MCCVector add(MCCBlockPos vector) {
        return new MCCVector(x() + vector.x(), y() + vector.y(), z() + vector.z());
    }

    public MCCVector withX(double x) {
        return new MCCVector(x, y, z);
    }

    public MCCVector withY(double y) {
        return new MCCVector(x, y, z);
    }

    public MCCVector withZ(double z) {
        return new MCCVector(x, y, z);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCCVector mccVector = (MCCVector) o;
        return Double.compare(x, mccVector.x) == 0 && Double.compare(y, mccVector.y) == 0 && Double.compare(z, mccVector.z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "MCCVector{" + "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
