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
    boolean canAccess(double x, double y, double z);

    default boolean canAccess(int x, int y, int z) {
        return canAccess(MCCLocation.calculateBlockX(x), MCCLocation.calculateBlockY(z), MCCLocation.calculateBlockZ(z));
    }

    default void checkAccess(MCCLocation mccLocation) {
        if (!canAccess(mccLocation)) {
            throw new IllegalArgumentException(this + " cannot access the location " + mccLocation);
        }
    }

    default void checkAccess(double x, double y, double z) {
        if (!canAccess(x, y, z)) {
            throw new IllegalArgumentException(this + " annot access the coordinates " + x + "," + y + "," + z);
        }
    }
}
