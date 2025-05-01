package de.verdox.mccreativelab.wrapper.world.coordinates;

import com.google.common.primitives.Doubles;

public interface FloatingPointPos<V extends FloatingPointPos<V>> extends Pos<V> {

    V normalize();

    V rotateAroundX(double angle);

    V rotateAroundY(double angle);

    V rotateAroundZ(double angle);

    V rotateAroundAxis(V axis, double angle);

    boolean isNormalized();

    default float angle(V other) {
        double dot = Doubles.constrainToRange(dot(other) / (length() * other.length()), -1.0, 1.0);

        return (float) Math.acos(dot);
    }

}
