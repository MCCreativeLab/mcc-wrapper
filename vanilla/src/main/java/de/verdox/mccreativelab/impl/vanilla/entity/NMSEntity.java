package de.verdox.mccreativelab.impl.vanilla.entity;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.entity.MCCTeleportFlag;
import de.verdox.mccreativelab.wrapper.entity.permission.MCCPermissionContainer;
import de.verdox.mccreativelab.wrapper.exceptions.OperationNotPossibleOnNMS;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.TempCache;
import de.verdox.mccreativelab.wrapper.platform.TempData;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.pointer.Pointers;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class NMSEntity<T extends Entity> extends MCCHandle<T> implements MCCEntity {
    public static final MCCConverter<Entity, NMSEntity> CONVERTER = converter(NMSEntity.class, Entity.class, NMSEntity::new, nmsEntity -> (Entity) nmsEntity.getHandle());

    private final MCCPermissionContainer permissionContainer = new MCCPermissionContainer();
    protected Pointers adventurePointer;
    public final Sinks.Many<Long> tickSink = Sinks.many().multicast().directBestEffort();


    public NMSEntity(T handle) {
        super(handle);
    }

    @Override
    public @NotNull MCCEntityType getType() {
        return conversionService.wrap(handle.getType(), new TypeToken<>() {});
    }

    @Override
    public UUID getUUID() {
        return handle.getUUID();
    }

    @Override
    public Component displayName() {
        return conversionService.wrap(handle.getDisplayName(), new TypeToken<>() {});
    }

    @Override
    public void displayName(Component name) {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public CompletableFuture<MCCEntity> teleport(@NotNull MCCLocation location, MCCTeleportFlag... flags) {
        Preconditions.checkArgument(location != null, "location cannot be null");
        CompletableFuture<MCCEntity> done = new CompletableFuture<>();
        location.checkFinite();
        Set<MCCTeleportFlag> flagSet = new HashSet<>(List.of(flags));
        boolean dismount = !flagSet.contains(MCCTeleportFlag.RETAIN_VEHICLE);
        boolean retainPassengers = flagSet.contains(MCCTeleportFlag.RETAIN_PASSENGERS);

        boolean isWorldChange = Objects.equals(location.world(), location.world());

        if (flagSet.contains(MCCTeleportFlag.RETAIN_PASSENGERS) && getHandle().isVehicle() && !isWorldChange) {
            done.complete(null);
        } else if (!dismount && getHandle().isPassenger() && isWorldChange) {
            done.complete(null);
        } else if ((retainPassengers || !getHandle().isVehicle()) && !getHandle().isRemoved()) {
            if (dismount) {
                getHandle().stopRiding();
            }

            if (location.world() != null && !isWorldChange) {

                ServerLevel targetWorld = conversionService.unwrap(location.world(), ServerLevel.class);
                Vec3 targetPos = new Vec3(location.x(), location.y(), location.z());

                handle.teleport(new TeleportTransition(targetWorld, targetPos, Vec3.ZERO, location.pitch(), location.yaw(), Set.of(), entity -> {
                    done.complete(this);
                }));
            } else {
                getHandle().moveTo(location.x(), location.y(), location.z(), location.yaw(), location.pitch());
                getHandle().setYHeadRot(location.yaw());
                if (retainPassengers && getHandle().isVehicle()) {
                    // Teleport passengers
                    getHandle().getSelfAndPassengers().forEach(entity -> {
                        for (Entity passenger : entity.getPassengers()) {
                            Vec3 vec3 = getHandle().getPassengerRidingPosition(passenger);
                            Vec3 vec32 = passenger.getVehicleAttachmentPoint(getHandle());
                            passenger.moveTo(vec3.x - vec32.x, vec3.y - vec32.y, vec3.z - vec32.z);
                        }
                    });
                }
                done.complete(this);
            }

        } else {
            done.complete(null);
        }
        return done;
    }

    @Override
    public MCCLocation getLocation() {
        return new MCCLocation(conversionService.wrap(handle.level(), new TypeToken<>() {}), handle.position().x(), handle.position().y(), handle.position().z(), handle.getYRot(), handle.getXRot());
    }

    @Override
    public @NotNull Key key() {
        return getType().key();
    }

    @Override
    public TempData getTempData() {
        return TempCache.get(getHandle());
    }


    @Override
    public int getEntityID() {
        return handle.getId();
    }

    @Override
    public boolean isCrouching() {
        return handle.isCrouching();
    }

    @Override
    public boolean isAlwaysTicking() {
        return handle.isAlwaysTicking();
    }

    @Override
    public boolean isAttackable() {
        return handle.isAttackable();
    }

    @Override
    public boolean isCurrentlyGlowing() {
        return handle.isCurrentlyGlowing();
    }

    @Override
    public boolean isFreezing() {
        return handle.isFreezing();
    }

    @Override
    public boolean isFullyFrozen() {
        return handle.isFullyFrozen();
    }

    @Override
    public boolean isIgnoringBlockTriggers() {
        return handle.isIgnoringBlockTriggers();
    }

    @Override
    public boolean isInBubbleColumn() {
        return invokeMethodInHandle("isInBubbleColumn");
    }

    @Override
    public boolean isInLava() {
        return handle.isInLava();
    }

    @Override
    public boolean isInLiquid() {
        return handle.isInLiquid();
    }

    @Override
    public boolean isInWall() {
        return handle.isInWall();
    }

    @Override
    public boolean isInWater() {
        return handle.isInWater();
    }

    @Override
    public boolean isInWaterOrBubble() {
        return handle.isInWaterOrBubble();
    }

    @Override
    public boolean isInWaterOrRain() {
        return handle.isInWaterOrRain();
    }

    @Override
    public boolean isInWaterOrRainOrBubble() {
        return handle.isInWaterRainOrBubble();
    }

    @Override
    public boolean isNoGravity() {
        return handle.isNoGravity();
    }

    @Override
    public boolean isOnFire() {
        return handle.isOnFire();
    }

    @Override
    public boolean isOnRails() {
        return handle.isOnRails();
    }

    @Override
    public boolean isPickable() {
        return handle.isPickable();
    }

    @Override
    public boolean isPushable() {
        return handle.isPushable();
    }

    @Override
    public boolean isShiftKeyDown() {
        return handle.isShiftKeyDown();
    }

    @Override
    public boolean isSprinting() {
        return handle.isSprinting();
    }

    @Override
    public boolean isSteppingCarefully() {
        return handle.isSteppingCarefully();
    }

    @Override
    public boolean isSwimming() {
        return handle.isSwimming();
    }

    @Override
    public boolean isTicking() {
        return ((ServerChunkCache) getHandle().level().getChunkSource()).isPositionTicking(
                ChunkPos.asLong(net.minecraft.util.Mth.floor(getHandle().getX()) >> 4, net.minecraft.util.Mth.floor(getHandle().getZ()) >> 4)
        );
    }

    @Override
    public boolean isUnderwater() {
        return handle.isUnderWater();
    }

    @Override
    public boolean isOnGround() {
        return handle.onGround();
    }

    @Override
    public void setRotation(float yaw, float pitch) {
        yaw = MCCLocation.normalizeYaw(yaw);
        pitch = MCCLocation.normalizePitch(pitch);
        getHandle().setYRot(yaw);
        getHandle().setXRot(pitch);
        getHandle().yRotO = yaw;
        getHandle().xRotO = pitch;
        getHandle().setYHeadRot(yaw);
    }

    @Override
    public Pointers pointers() {
        if (this.adventurePointer == null) {
            this.adventurePointer = Pointers.builder()
                    .withDynamic(net.kyori.adventure.identity.Identity.DISPLAY_NAME, this::displayName)
                    .withDynamic(net.kyori.adventure.identity.Identity.UUID, this::getUUID)
                    .withStatic(net.kyori.adventure.permission.PermissionChecker.POINTER, permission -> getPermissions().permissionValue(permission))
                    .build();
        }
        return this.adventurePointer;
    }

    @Override
    public MCCPermissionContainer getPermissions() {
        return permissionContainer;
    }

    @Override
    public Flux<Long> tickSignal() {
        return tickSink.asFlux();
    }
}
