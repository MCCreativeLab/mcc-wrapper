package de.verdox.mccreativelab.wrapper.world.acessor;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;

public interface Accessor {
    /**
     * Checks if the accessor has access to this location
     */
    boolean canAccess(MCCLocation mccLocation);

    /**
     * Checks if the accessor has access to the coordinates
     */
    boolean canAccess(int x, int y, int z);

    default void checkAccess(MCCLocation mccLocation) {
        if (!canAccess(mccLocation)) {
            throw new IllegalArgumentException("Cannot access the location " + mccLocation);
        }
    }

    default void checkAccess(int x, int y, int z) {
        if (!canAccess(x, y, z)) {
            throw new IllegalArgumentException("Cannot access the coordinates " + x + "," + y + "," + z);
        }
    }
}
