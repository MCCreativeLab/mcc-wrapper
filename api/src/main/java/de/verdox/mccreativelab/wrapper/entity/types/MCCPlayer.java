package de.verdox.mccreativelab.wrapper.entity.types;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.annotations.MCCLogic;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.component.entity.MCCEffectTarget;
import de.verdox.mccreativelab.wrapper.component.entity.MCCEntityHiding;
import de.verdox.mccreativelab.wrapper.component.entity.MCCPluginMessenger;
import de.verdox.mccreativelab.wrapper.entity.ContainerViewer;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.player.MCCGameMode;
import de.verdox.mccreativelab.wrapper.entity.player.client.MCCClientOption;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainer;
import de.verdox.mccreativelab.wrapper.inventory.types.container.MCCPlayerInventory;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.platform.cached.signal.ObservedSignal;
import de.verdox.mccreativelab.wrapper.util.MCCEntityMultiProperty;
import de.verdox.mccreativelab.wrapper.util.MCCEntityProperty;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.Weather;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.UUID;

/**
 * Represents a player on a minecraft server
 */
public interface MCCPlayer extends MCCLivingEntity, ContainerViewer, Identified {
    @Override
    default net.kyori.adventure.identity.@NotNull Identity identity() {
        return net.kyori.adventure.identity.Identity.identity(this.getUUID());
    }

    /**
     * Gets the inventory of the player
     *
     * @return the inventory
     */
    MCCPlayerInventory getInventory();

    String getPlayerName();

    void syncInventory();

    /**
     * Represents the world time of the player
     *
     * @return the world time property
     */
    @MCCLogic
    MCCEntityProperty<Long, MCCPlayer> getTimeProperty();

    /**
     * Represents the client weather of the player
     *
     * @return the world time property
     */
    @MCCLogic
    MCCEntityProperty<Weather, MCCPlayer> getWeatherProperty();

    /**
     * Represents all players hidden from this player
     *
     * @return the hide property
     */
    @MCCLogic
    MCCEntityMultiProperty<MCCPlayer, MCCPlayer> getHideProperty();

    /**
     * Represents the ability of a player to click in any gui
     *
     * @return the inventory click property
     */
    @MCCLogic
    MCCEntityProperty<Boolean, MCCPlayer> getInventoryClickProperty();

    /**
     * Represents the ability of a player to click in any gui
     *
     * @return the inventory interact property
     */
    @MCCLogic
    MCCEntityProperty<Boolean, MCCPlayer> getInventoryInteractProperty();

    /**
     * Represents the ability of a player to swap his hand items
     *
     * @return the swap hand property
     */
    @MCCLogic
    MCCEntityProperty<Boolean, MCCPlayer> getSwapHandsProperty();

    /**
     * Represents the players game mode
     * @return the property
     */
    MCCEntityProperty<MCCGameMode, MCCPlayer> getGameModeProperty();

    /**
     * Returns the previous game mode of the player
     * @return the previous game mode
     */
    @Nullable
    MCCGameMode getPreviousGameMode();

    /**
     * Represents the ability of a player to interact with blocks or the air
     *
     * @return the interact property
     */
    @MCCLogic
    MCCEntityProperty<Boolean, MCCPlayer> getInteractProperty();

    /**
     * Returns true if the player is still online
     *
     * @return true if the player is still online
     */
    boolean isOnline();

    /**
     * Sets the resource pack of a player
     *
     * @param uuid        the uuid of the resource pack
     * @param downloadURL the download url of the resource pack
     * @param hashBytes   the hash bytes of the resource pack
     * @param prompt      the prompt to show to the player
     * @param required    whether the resource pack is required on this server
     */
    void setResourcePack(UUID uuid, String downloadURL, byte[] hashBytes, @Nullable Component prompt, boolean required);

    /**
     * Sends block damage to this player at a specified location
     *
     * @param location location of the block with the damage state
     * @param progress the damage state
     * @param entityId The id of the effect
     */
    void sendBlockDamage(@NotNull MCCLocation location, @Range(from = 0, to = 1) float progress, int entityId);

    /**
     * Breaks a block for a player
     * @param block the block
     * @return true if the block was broken
     */
    boolean breakBlock(MCCBlock block);

    <T> T getClientOption(MCCClientOption<T> type);

    /**
     * @param state the state to drop
     * @return whether the player has the correct tool for drops of the state
     */
    boolean hasCorrectToolForDrops(MCCBlockState state);

    /**
     * Adds the item stack from the provided inventory at the provided slot into the player's inventory
     * @param container the container
     * @param slot the slot
     */
    default void addItemOrDropFromSlot(MCCContainer container, int slot) {
        if (container.getSize() <= slot) {
            return;
        }
        var item = container.getItem(slot);
        if (item == null || item.isEmpty()) {
            return;
        }

        addItemOrDrop(item);
        container.setItem(slot, null);
    }

    /**
     * Adds the provided item stack to the player's inventory or drops it on the ground
     * @param stack the item stack
     */
    default void addItemOrDrop(MCCItemStack stack) {
        getInventory().addItem(stack).forEach((integer, mccItemStack) -> getChunk().dropItem(getLocation(), mccItemStack, mccItemEntity -> mccItemEntity.setOwner(getUUID())));
    }

    /**
     * Sets the entity to spectate. If null is provided the camera gets reset
     * @param entityToSpectate the entity to spectate
     */
    void setCamera(@Nullable MCCEntity entityToSpectate) throws IllegalStateException;

    /**
     * Returns the entity the player is currently spectating
     */
    @Nullable
    MCCEntity getCamera();

    /**
     * Returns the {@link MCCEffectTarget} component
     */
    default MCCPluginMessenger asPluginMessenger() {
        return MCCPlatform.getInstance().getGameComponentRegistry().create(this, MCCPluginMessenger.class);
    }

    /**
     * Returns the {@link MCCEffectTarget} component
     */
    default MCCEntityHiding asHider() {
        return MCCPlatform.getInstance().getGameComponentRegistry().create(this, MCCEntityHiding.class);
    }

    /**
     * Used to subscribe to player inputs
     */
    default ObservedSignal<Input> inputSignal() {
        return MCCPlatform.getInstance().createSignal(Key.key("minecraft", "player_input"), this, new TypeToken<Input>() {}).asFlux();
    }

    record Input(long tick, boolean forward, boolean backward, boolean left, boolean right, boolean jump, boolean sneak,
                 boolean sprint) {}
}
