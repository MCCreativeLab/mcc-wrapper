package de.verdox.mccreativelab.wrapper.entity;

import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.annotations.MCCInstantiationSource;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.component.entity.MCCEffectTarget;
import de.verdox.mccreativelab.wrapper.component.entity.MCCPersistent;
import de.verdox.mccreativelab.wrapper.component.entity.MCCRideable;
import de.verdox.mccreativelab.wrapper.component.entity.MCCRider;
import de.verdox.mccreativelab.wrapper.entity.permission.MCCPermissible;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.platform.TempDataHolder;
import de.verdox.mccreativelab.wrapper.util.MCCEntityProperty;
import de.verdox.mccreativelab.wrapper.util.MCCTicking;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCVector;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Describes an entity in a minecraft world
 */
@MCCInstantiationSource(sourceClasses = {MCCWorld.class})
public interface MCCEntity extends MCCKeyedWrapper, TempDataHolder, MCCWrapped, Audience, MCCPermissible, MCCTicking, net.kyori.adventure.text.event.HoverEventSource<net.kyori.adventure.text.event.HoverEvent.ShowEntity>, net.kyori.adventure.sound.Sound.Emitter {
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
    default CompletableFuture<Boolean> teleport(@NotNull MCCLocation location, MCCTeleportFlag... flags) {
        return location.world().atChunk(location, (Function<MCCChunk, Boolean>) mccChunk -> mccChunk.teleport(location, this, flags));
    }

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
     * Gets the velocity property of this entity
     */
    MCCEntityProperty<MCCVector, MCCEntity> velocity();

    /**
     * Marks the entity for removal
     */
    void remove();

    /**
     * Checks if the entity was removed
     */
    boolean isRemoved();

    /**
     * Changes the rotation of the entity
     * @param yaw the yaw
     * @param pitch the pitch
     */
    void setRotation(float yaw, float pitch);

    /**
     * Returns the {@link MCCRideable} component
     */
    default MCCRideable asRideable() {
        return MCCPlatform.getInstance().getGameComponentRegistry().create(this, MCCRideable.class);
    }

    /**
     * Returns the {@link MCCRider} component
     */
    default MCCRider asRider() {
        return MCCPlatform.getInstance().getGameComponentRegistry().create(this, MCCRider.class);
    }

    /**
     * Returns the {@link MCCRider} component
     */
    default MCCPersistent asPersistent() {
        return MCCPlatform.getInstance().getGameComponentRegistry().create(this, MCCPersistent.class);
    }

    @Override
    default Key getRegistryKey() {
        return getType().getRegistryKey();
    }

    @NotNull
    @Override
    default net.kyori.adventure.text.event.HoverEvent<net.kyori.adventure.text.event.HoverEvent.ShowEntity> asHoverEvent(final @NotNull java.util.function.UnaryOperator<net.kyori.adventure.text.event.HoverEvent.ShowEntity> op) {
        return net.kyori.adventure.text.event.HoverEvent.showEntity(op.apply(HoverEvent.ShowEntity.showEntity(this.getType().key(), this.getUUID(), this.displayName())));
    }

    /**
     * Gets the current chunk of the entity
     */
    @NotNull MCCChunk getChunk();

    /**
     * Gets the current block of the entity.
     */
    @NotNull
    default MCCBlock getBlock() {
        return getChunk().get(getLocation());
    }
}
