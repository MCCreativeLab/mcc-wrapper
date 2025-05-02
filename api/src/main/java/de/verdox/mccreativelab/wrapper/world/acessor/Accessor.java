package de.verdox.mccreativelab.wrapper.world.acessor;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.coordinates.Pos;

public interface Accessor {
    /**
     * Checks if the accessor has access to the coordinates
     */
    boolean canAccess(Pos<?> pos);

    default void checkAccess(MCCLocation mccLocation) {
        if (!canAccess(mccLocation)) {
            throw new IllegalArgumentException(this + " cannot access the location " + mccLocation);
        }
    }

    default void checkAccess(Pos<?> pos) {
        if (!canAccess(pos)) {
            throw new IllegalArgumentException(this + " annot access the pos " + pos);
        }
    }
}
