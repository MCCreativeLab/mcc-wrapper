package de.verdox.mccreativelab.wrapper.misc;

import de.verdox.mccreativelab.wrapper.world.MCCPos;

public interface MCCBlockHitResult extends MCCHitResult {
    /**
     * @return the hit direction
     */
    MCCDirection getDirection();

    /**
     * @return the block pos of the hit block
     */
    MCCPos getBlockPos();

    /**
     * @return whether the hit is inside the block
     */
    boolean isInside();

    /**
     * @return whether the hit is in world border
     */
    boolean isWorldBorderHit();
}
