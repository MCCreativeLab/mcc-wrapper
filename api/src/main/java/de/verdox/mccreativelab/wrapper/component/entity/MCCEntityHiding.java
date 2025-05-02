package de.verdox.mccreativelab.wrapper.component.entity;

import de.verdox.mccreativelab.wrapper.component.GameComponent;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;

/**
 * Represents players the server can hide entities from
 */
public interface MCCEntityHiding extends GameComponent<MCCPlayer> {

    /**
     * Hides an entity from this player
     */
    void hide(MCCEntity other);

    /**
     * Shows an entity to this player
     */
    void show(MCCEntity other);

    /**
     * Checks if the player can see the other entity
     */
    boolean canSee(MCCEntity other);

}
