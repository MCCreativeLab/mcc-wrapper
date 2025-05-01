package de.verdox.mccreativelab.impl.vanilla.entity;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.world.chunk.NMSChunk;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.entity.permission.MCCPermissionContainer;
import de.verdox.mccreativelab.wrapper.exceptions.OperationNotPossibleOnNMS;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.cached.TempCache;
import de.verdox.mccreativelab.wrapper.platform.cached.TempData;
import de.verdox.mccreativelab.wrapper.platform.cached.signal.ObservedSignal;
import de.verdox.mccreativelab.wrapper.platform.cached.signal.SignalCache;
import de.verdox.mccreativelab.wrapper.util.MCCEntityProperty;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCVector;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.pointer.Pointers;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.*;

public class NMSEntity<T extends Entity> extends MCCHandle<T> implements MCCEntity {
    public static final MCCConverter<Entity, NMSEntity> CONVERTER = converter(NMSEntity.class, Entity.class, NMSEntity::new, nmsEntity -> (Entity) nmsEntity.getHandle());

    private final MCCPermissionContainer permissionContainer = new MCCPermissionContainer();
    protected Pointers adventurePointer;


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
    public MCCEntityProperty<MCCVector, MCCEntity> velocity() {
        return new MCCEntityProperty<>() {
            @Override
            public @Nullable MCCVector get() {
                return new MCCVector(getHandle().getDeltaMovement().x(), getHandle().getDeltaMovement().y(), getHandle().getDeltaMovement().z());
            }

            @Override
            public void set(@Nullable MCCVector newValue) {
                getHandle().setDeltaMovement(newValue.x(), newValue.y(), newValue.z());
            }

            @Override
            public void sync() {

            }
        };
    }

    @Override
    public void remove() {
        getHandle().remove(Entity.RemovalReason.DISCARDED);
    }

    @Override
    public boolean isRemoved() {
        return handle.isRemoved();
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
    public @NotNull MCCChunk getChunk() {
        return new NMSChunk(handle.level().getChunk(handle.chunkPosition().x, handle.chunkPosition().z));
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
}
