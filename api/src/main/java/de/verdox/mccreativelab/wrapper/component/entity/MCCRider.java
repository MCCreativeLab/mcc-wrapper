package de.verdox.mccreativelab.wrapper.component.entity;

import de.verdox.mccreativelab.wrapper.component.GameComponent;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an entity that can ride other entities
 */
public interface MCCRider extends GameComponent<MCCEntity> {
    /**
     * Starts riding an entity
     * @param vehicle the entity to ride
     * @param force whether to force it
     * @return true if it worked
     */
    boolean startRiding(MCCRideable vehicle, boolean force);

    /**
     * Starts riding an entity
     * @param vehicle the entity to ride
     * @return true if it worked
     */
    default boolean startRiding(MCCRideable vehicle) {
        return startRiding(vehicle, false);
    }

    /**
     * Stops riding an entity
     * @return true if it worked
     */
    boolean stopRiding();

    @Nullable
    MCCRideable getCurrentlyRiddenEntity();
}
