package de.verdox.mccreativelab.wrapper.misc;

import de.verdox.mccreativelab.wrapper.world.coordinates.MCBlockPos;

public interface MCCBlockHitResult extends MCCHitResult {
    /**
     * @return the hit direction
     */
    MCCDirection getDirection();

    /**
     * @return the block pos of the hit block
     */
    MCBlockPos getBlockPos();

    /**
     * @return whether the hit is inside the block
     */
    boolean isInside();

    /**
     * @return whether the hit is in world border
     */
    boolean isWorldBorderHit();
}
