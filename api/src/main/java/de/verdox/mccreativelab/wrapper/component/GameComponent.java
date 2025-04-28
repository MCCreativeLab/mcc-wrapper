package de.verdox.mccreativelab.wrapper.component;

/**
 * Represents a game component that is hold by an element on the platform
 */
public interface GameComponent<OWNER> {
    OWNER getOwner();
}
