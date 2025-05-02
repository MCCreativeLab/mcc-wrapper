package de.verdox.mccreativelab.wrapper.world.coordinates;

import com.google.common.base.Preconditions;
import de.verdox.mccreativelab.wrapper.util.math.MathUtil;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;

public record MCPos(double x, double y, double z) implements FloatingPointPos<MCPos> {

    public MCPos(double value) {
        this(value, value, value);
    }

    @Override
    public MCPos add(MCPos vector) {
        return new MCPos(vector.x + x, vector.y + y, vector.z + z);
    }

    @Override
    public MCPos sub(MCPos vector) {
        return new MCPos(vector.x - x, vector.y - y, vector.z - z);
    }

    @Override
    public MCPos mul(MCPos vector) {
        return new MCPos(vector.x * x, vector.y * y, vector.z * z);
    }

    @Override
    public MCPos div(MCPos vector) {
        return new MCPos(vector.x / x, vector.y / y, vector.z / z);
    }

    @Override
    public MCPos normalize() {
        double length = length();
        return new MCPos(x / length, y / length, z / length);
    }

    @Override
    public MCPos rotateAroundX(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double y = angleCos * y() - angleSin * z();
        double z = angleSin * y() + angleCos * z();
        return new MCPos(x, y, z);
    }

    @Override
    public MCPos rotateAroundY(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double x = angleCos * x() + angleSin * z();
        double z = -angleSin * x() + angleCos * z();
        return new MCPos(x, y, z);
    }

    @Override
    public MCPos rotateAroundZ(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double x = angleCos * x() - angleSin * y();
        double y = angleSin * x() + angleCos * y();
        return new MCPos(x, y, z);
    }

    @Override
    public MCPos rotateAroundAxis(MCPos axis, double angle) {
        Preconditions.checkArgument(axis != null, "The provided axis vector was null");

        double x = x(), y = y(), z = z();
        double x2 = axis.x(), y2 = axis.y(), z2 = axis.z();

        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);
        double dotProduct = this.dot(axis);

        double xPrime = x2 * dotProduct * (1d - cosTheta)
                + x * cosTheta
                + (-z2 * y + y2 * z) * sinTheta;
        double yPrime = y2 * dotProduct * (1d - cosTheta)
                + y * cosTheta
                + (z2 * x - x2 * z) * sinTheta;
        double zPrime = z2 * dotProduct * (1d - cosTheta)
                + z * cosTheta
                + (-y2 * x + x2 * y) * sinTheta;

        return new MCPos(xPrime, yPrime, zPrime);
    }

    @Override
    public double length() {
        return Math.sqrt(MathUtil.square(x) + MathUtil.square(y) + MathUtil.square(z));
    }

    @Override
    public double lengthSquared() {
        return MathUtil.square(x) + MathUtil.square(y) + MathUtil.square(z);
    }

    @Override
    public double distance(MCPos vector) {
        return Math.sqrt(MathUtil.square(x - vector.x) + MathUtil.square(y - vector.y) + MathUtil.square(z - vector.z));
    }

    @Override
    public double distanceSquared(MCPos vector) {
        return MathUtil.square(x - vector.x) + MathUtil.square(y - vector.y) + MathUtil.square(z - vector.z);
    }

    @Override
    public double dot(MCPos vector) {
        return x * vector.x + y * vector.y + z * vector.z;
    }

    @Override
    public MCPos crossProduct(MCPos vector) {
        double newX = y * vector.z - vector.y * z;
        double newY = z * vector.x - vector.z * x;
        double newZ = x * vector.y - vector.x * y;
        return new MCPos(newX, newY, newZ);
    }

    @Override
    public boolean isInAABB(MCPos min, MCPos max) {
        return x >= min.x && x <= max.x && y >= min.y && y <= max.y && z >= min.z && z <= max.z;
    }

    @Override
    public boolean isInSphere(MCPos origin, double radius) {
        return (MathUtil.square(origin.x - x) + MathUtil.square(origin.y - y) + MathUtil.square(origin.z - z)) <= MathUtil.square(radius);
    }

    @Override
    public boolean isNormalized() {
        return Math.abs(this.lengthSquared() - 1) < EPSILON;
    }

    @Override
    public MCPos toPos() {
        return this;
    }

    @Override
    public MCBlockPos toBlockPos() {
        return new MCBlockPos((int) Math.floor(x()), (int) Math.floor(y()), (int) Math.floor(z()));
    }

    public MCCLocation toLocation(MCCWorld world) {
        return new MCCLocation(world, this);
    }
}
