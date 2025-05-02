package de.verdox.mccreativelab.wrapper.component.entity;

import de.verdox.mccreativelab.wrapper.component.GameComponent;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 * Marks this entity as persistent.
 */
public interface MCCPersistent extends GameComponent<MCCEntity> {
    boolean isPersistent();
    void setPersistent(boolean value);
}
