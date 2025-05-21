package de.verdox.mccreativelab.wrapper.component.entity;

import de.verdox.mccreativelab.wrapper.component.GameComponent;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.vserializer.generic.SerializationElement;

/**
 * Marks this entity as persistent.
 */
public interface MCCPersistent extends GameComponent<MCCEntity> {
    boolean isPersistent();

    void setPersistent(boolean value);

    /**
     * Serializes the entities persistent data
     */
    SerializationElement serializePersistentData();

    /**
     * Loads the data from the provided serialization element into the entity
     */
    void loadPersistentDataIntoObject(SerializationElement serializationElement);
}
