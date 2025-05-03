package de.verdox.mccreativelab.wrapper.misc;

import de.verdox.mccreativelab.wrapper.MCCWrapped;

public interface MCCHitResult extends MCCWrapped {
    /**
     * @return the x position of the hit
     */
    double x();

    /**
     * @return the y position of the hit
     */
    double y();

    /**
     * @return the z position of the hit
     */
    double z();

    /**
     * @return the hit type
     */
    MCCHitType getType();
}
