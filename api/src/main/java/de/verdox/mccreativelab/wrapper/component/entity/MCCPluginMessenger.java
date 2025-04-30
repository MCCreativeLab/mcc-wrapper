package de.verdox.mccreativelab.wrapper.component.entity;

import de.verdox.mccreativelab.wrapper.component.GameComponent;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import net.kyori.adventure.key.Key;

/**
 * Represents an entity that can be used to send plugin messages
 */
public interface MCCPluginMessenger extends GameComponent<MCCPlayer> {
    /**
     * Sends a plugin message over a specified channel
     * @param channel the channel
     * @param message the message
     */
    void sendPluginMessage(Key channel, byte[] message);
}
