package de.verdox.mccreativelab.wrapper.entity;

import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.annotations.MCCInstantiationSource;
import de.verdox.mccreativelab.wrapper.entity.permission.MCCPermissible;
import de.verdox.mccreativelab.wrapper.platform.TempDataHolder;
import de.verdox.mccreativelab.wrapper.util.MCCTicking;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.pointer.Pointers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Describes an entity in a minecraft world
 */
@MCCInstantiationSource(sourceClasses = {MCCWorld.class})
public interface MCCEntity extends MCCKeyedWrapper, TempDataHolder, MCCWrapped, Audience, MCCPermissible, MCCRideable, MCCRider, MCCTicking, net.kyori.adventure.text.event.HoverEventSource<net.kyori.adventure.text.event.HoverEvent.ShowEntity>, net.kyori.adventure.sound.Sound.Emitter {
    /**
     * Gets the type of this entity
     *
     * @return the type
     */
    @NotNull MCCEntityType getType();

    /**
     * Gets the uuid of this entity
     *
     * @return the uuid
     */
    UUID getUUID();

    /**
     * Gets the display name of this entity
     *
     * @return the display name
     */
    Component displayName();

    /**
     * Sets the display name of this entity
     *
     * @param name the display name
     */
    void displayName(Component name);

    /**
     * Teleports an entity to another location.
     *
     * @param location the location
     * @return the future that is completed when the teleportation is done
     */
    CompletableFuture<MCCEntity> teleport(@NotNull MCCLocation location, MCCTeleportFlag... flags);

    MCCLocation getLocation();

    int getEntityID();

    boolean isCrouching();

    boolean isAlwaysTicking();

    boolean isAttackable();

    boolean isCurrentlyGlowing();

    boolean isFreezing();

    boolean isFullyFrozen();

    boolean isIgnoringBlockTriggers();

    boolean isInBubbleColumn();

    boolean isInLava();

    boolean isInLiquid();

    boolean isInWall();

    boolean isInWater();

    boolean isInWaterOrBubble();

    boolean isInWaterOrRain();

    boolean isInWaterOrRainOrBubble();

    boolean isNoGravity();

    boolean isOnFire();

    boolean isOnRails();

    boolean isPickable();

    boolean isPushable();

    boolean isShiftKeyDown();

    boolean isSprinting();

    boolean isSteppingCarefully();

    boolean isSwimming();

    boolean isTicking();

    boolean isUnderwater();

    boolean isOnGround();

    /**
     * Marks the entity for removal
     */
    void remove();

    /**
     * Changes the rotation of the entity
     * @param yaw the yaw
     * @param pitch the pitch
     */
    void setRotation(float yaw, float pitch);

    @Override
    default Key getRegistryKey() {
        return getType().getRegistryKey();
    }

    @NotNull
    @Override
    default net.kyori.adventure.text.event.HoverEvent<net.kyori.adventure.text.event.HoverEvent.ShowEntity> asHoverEvent(final @NotNull java.util.function.UnaryOperator<net.kyori.adventure.text.event.HoverEvent.ShowEntity> op) {
        return net.kyori.adventure.text.event.HoverEvent.showEntity(op.apply(HoverEvent.ShowEntity.showEntity(this.getType().key(), this.getUUID(), this.displayName())));
    }
}
