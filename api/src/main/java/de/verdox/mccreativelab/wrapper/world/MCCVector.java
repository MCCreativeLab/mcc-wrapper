package de.verdox.mccreativelab.wrapper.world;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import de.verdox.mccreativelab.wrapper.util.math.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MCCVector {
    /**
     * Threshold for fuzzy equals().
     */
    private static final double epsilon = 0.000001;

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

    // Add operations
    public MCCVector add(double x, double y, double z) {
        return new MCCVector(x() + x, y() + y, z() + z);
    }

    public MCCVector add(double val) {
        return add(val, val, val);
    }

    public MCCVector add(MCCVector vector) {
        return add(vector.x(), vector.y(), vector.z());
    }

    public MCCVector add(MCCBlockPos vector) {
        return add(vector.x(), vector.y(), vector.z());
    }
    // Sub operations
    public MCCVector sub(double x, double y, double z) {
        return new MCCVector(x() - x, y() - y, z() - z);
    }

    public MCCVector sub(double val) {
        return sub(val, val, val);
    }

    public MCCVector sub(MCCVector vector) {
        return sub(vector.x(), vector.y(), vector.z());
    }

    public MCCVector sub(MCCBlockPos vector) {
        return sub(vector.x(), vector.y(), vector.z());
    }
    // Mul operations
    public MCCVector mul(double x, double y, double z) {
        return new MCCVector(x() * x, y() * y, z() * z);
    }

    public MCCVector mul(double val) {
        return mul(val, val, val);
    }

    public MCCVector mul(MCCVector vector) {
        return mul(vector.x(), vector.y(), vector.z());
    }

    public MCCVector mul(MCCBlockPos vector) {
        return mul(vector.x(), vector.y(), vector.z());
    }
    // Div operations
    public MCCVector div(double x, double y, double z) {
        return new MCCVector(x() / x, y() / y, z() / z);
    }

    public MCCVector div(double val) {
        return div(val, val, val);
    }

    public MCCVector div(MCCVector vector) {
        return div(vector.x(), vector.y(), vector.z());
    }

    public MCCVector div(MCCBlockPos vector) {
        return div(vector.x(), vector.y(), vector.z());
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

    @NotNull
    public MCCVector normalize() {
        double length = length();
        return new MCCVector(x / length, y / length, z / length);
    }

    /**
     * Gets the magnitude of the vector, defined as sqrt(x^2+y^2+z^2). The
     * value of this method is not cached and uses a costly square-root
     * function, so do not repeatedly call this method to get the vector's
     * magnitude. NaN will be returned if the inner result of the sqrt()
     * function overflows, which will be caused if the length is too long.
     *
     * @return the magnitude
     */
    public double length() {
        return Math.sqrt(MathUtil.square(x) + MathUtil.square(y) + MathUtil.square(z));
    }

    /**
     * Gets the magnitude of the vector squared.
     *
     * @return the magnitude
     */
    public double lengthSquared() {
        return MathUtil.square(x) + MathUtil.square(y) + MathUtil.square(z);
    }

    /**
     * Get the distance between this vector and another. The value of this
     * method is not cached and uses a costly square-root function, so do not
     * repeatedly call this method to get the vector's magnitude. NaN will be
     * returned if the inner result of the sqrt() function overflows, which
     * will be caused if the distance is too long.
     *
     * @param o The other vector
     * @return the distance
     */
    public double distance(@NotNull MCCVector o) {
        return Math.sqrt(MathUtil.square(x - o.x) + MathUtil.square(y - o.y) + MathUtil.square(z - o.z));
    }

    /**
     * Get the squared distance between this vector and another.
     *
     * @param o The other vector
     * @return the distance
     */
    public double distanceSquared(@NotNull MCCVector o) {
        return MathUtil.square(x - o.x) + MathUtil.square(y - o.y) + MathUtil.square(z - o.z);
    }

    /**
     * Gets the angle between this vector and another in radians.
     *
     * @param other The other vector
     * @return angle in radians
     */
    public float angle(@NotNull MCCVector other) {
        double dot = Doubles.constrainToRange(dot(other) / (length() * other.length()), -1.0, 1.0);

        return (float) Math.acos(dot);
    }

    /**
     * Calculates the dot product of this vector with another. The dot product
     * is defined as x1*x2+y1*y2+z1*z2. The returned value is a scalar.
     *
     * @param other The other vector
     * @return dot product
     */
    public double dot(@NotNull MCCVector other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Calculates the cross product of this vector with another. The cross
     * product is defined as:
     * <ul>
     * <li>x = y1 * z2 - y2 * z1
     * <li>y = z1 * x2 - z2 * x1
     * <li>z = x1 * y2 - x2 * y1
     * </ul>
     *
     * @param o The other vector
     * @return the same vector
     */
    @NotNull
    public MCCVector crossProduct(@NotNull MCCVector o) {
        double newX = y * o.z - o.y * z;
        double newY = z * o.x - o.z * x;
        double newZ = x * o.y - o.x * y;
        return new MCCVector(newX, newY, newZ);
    }

    /**
     * Returns whether this vector is in an axis-aligned bounding box.
     * <p>
     * The minimum and maximum vectors given must be truly the minimum and
     * maximum X, Y and Z components.
     *
     * @param min Minimum vector
     * @param max Maximum vector
     * @return whether this vector is in the AABB
     */
    public boolean isInAABB(@NotNull MCCVector min, @NotNull MCCVector max) {
        return x >= min.x && x <= max.x && y >= min.y && y <= max.y && z >= min.z && z <= max.z;
    }

    /**
     * Returns whether this vector is within a sphere.
     *
     * @param origin Sphere origin.
     * @param radius Sphere radius
     * @return whether this vector is in the sphere
     */
    public boolean isInSphere(@NotNull MCCVector origin, double radius) {
        return (MathUtil.square(origin.x - x) + MathUtil.square(origin.y - y) + MathUtil.square(origin.z - z)) <= MathUtil.square(radius);
    }

    /**
     * Returns if a vector is normalized
     *
     * @return whether the vector is normalised
     */
    public boolean isNormalized() {
        return Math.abs(this.lengthSquared() - 1) < getEpsilon();
    }

    /**
     * Rotates the vector around the x axis.
     * <p>
     * This piece of math is based on the standard rotation matrix for vectors
     * in three dimensional space. This matrix can be found here:
     * <a href="https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">Rotation
     * Matrix</a>.
     *
     * @param angle the angle to rotate the vector about. This angle is passed
     * in radians
     * @return the same vector
     */
    @NotNull
    public MCCVector rotateAroundX(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double y = angleCos * y() - angleSin * z();
        double z = angleSin * y() + angleCos * z();
        return withY(y).withZ(z);
    }

    /**
     * Rotates the vector around the y axis.
     * <p>
     * This piece of math is based on the standard rotation matrix for vectors
     * in three dimensional space. This matrix can be found here:
     * <a href="https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">Rotation
     * Matrix</a>.
     *
     * @param angle the angle to rotate the vector about. This angle is passed
     * in radians
     * @return the same vector
     */
    @NotNull
    public MCCVector rotateAroundY(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double x = angleCos * x() + angleSin * z();
        double z = -angleSin * x() + angleCos * z();
        return withX(x).withZ(z);
    }

    /**
     * Rotates the vector around the z axis
     * <p>
     * This piece of math is based on the standard rotation matrix for vectors
     * in three dimensional space. This matrix can be found here:
     * <a href="https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">Rotation
     * Matrix</a>.
     *
     * @param angle the angle to rotate the vector about. This angle is passed
     * in radians
     * @return the same vector
     */
    @NotNull
    public MCCVector rotateAroundZ(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double x = angleCos * x() - angleSin * y();
        double y = angleSin * x() + angleCos * y();
        return withX(x).withY(y);
    }

    /**
     * Rotates the vector around a given arbitrary axis in 3 dimensional space.
     *
     * <p>
     * Rotation will follow the general Right-Hand-Rule, which means rotation
     * will be counterclockwise when the axis is pointing towards the observer.
     * <p>
     * This method will always make sure the provided axis is a unit vector, to
     * not modify the length of the vector when rotating. If you are experienced
     * with the scaling of a non-unit axis vector, you can use
     * {@link MCCVector#rotateAroundNonUnitAxis(MCCVector, double)}.
     *
     * @param axis the axis to rotate the vector around. If the passed vector is
     * not of length 1, it gets copied and normalized before using it for the
     * rotation. Please use {@link MCCVector#normalize()} on the instance before
     * passing it to this method
     * @param angle the angle to rotate the vector around the axis
     * @return the same vector
     * @throws IllegalArgumentException if the provided axis vector instance is
     * null
     */
    @NotNull
    public MCCVector rotateAroundAxis(@NotNull MCCVector axis, double angle) throws IllegalArgumentException {
        Preconditions.checkArgument(axis != null, "The provided axis vector was null");

        return rotateAroundNonUnitAxis(axis.isNormalized() ? axis : axis.normalize(), angle);
    }

    /**
     * Rotates the vector around a given arbitrary axis in 3 dimensional space.
     *
     * <p>
     * Rotation will follow the general Right-Hand-Rule, which means rotation
     * will be counterclockwise when the axis is pointing towards the observer.
     * <p>
     * Note that the vector length will change accordingly to the axis vector
     * length. If the provided axis is not a unit vector, the rotated vector
     * will not have its previous length. The scaled length of the resulting
     * vector will be related to the axis vector. If you are not perfectly sure
     * about the scaling of the vector, use
     * {@link Vector#rotateAroundAxis(Vector, double)}
     *
     * @param axis the axis to rotate the vector around.
     * @param angle the angle to rotate the vector around the axis
     * @return the same vector
     * @throws IllegalArgumentException if the provided axis vector instance is
     * null
     */
    @NotNull
    public MCCVector rotateAroundNonUnitAxis(@NotNull MCCVector axis, double angle) throws IllegalArgumentException {
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

        return withX(xPrime).withY(yPrime).withZ(zPrime);
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

    /**
     * Get the threshold used for equals().
     *
     * @return The epsilon.
     */
    public static double getEpsilon() {
        return epsilon;
    }
}
