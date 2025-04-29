package de.verdox.mccreativelab.wrapper.component.entity;

import com.google.common.base.Preconditions;
import de.verdox.mccreativelab.wrapper.component.GameComponent;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

import java.util.List;

/**
 * Represents that this entity is rideable
 */
public interface MCCRideable extends GameComponent<MCCEntity> {
    /**
     * Returns all passengers of the entity
     * @return all passengers
     */
    List<MCCEntity> getPassengers();

    /**
     * Adds a new passenger to the entity
     * @param passenger the new passenger
     * @return true if it worked
     */
    default boolean addPassenger(MCCRider passenger) {
        Preconditions.checkArgument(passenger != null, "Entity passenger cannot be null");
        Preconditions.checkArgument(!this.equals(passenger), "Entity cannot ride itself.");
        return passenger.startRiding(this, true);
    }

    /**
     * Removes a passenger to the entity
     * @param passenger the passenger to remove
     * @return true if it worked
     */
    default boolean removePassenger(MCCRider passenger) {
        Preconditions.checkArgument(passenger != null, "Entity passenger cannot be null");
        return passenger.stopRiding();
    }
}
