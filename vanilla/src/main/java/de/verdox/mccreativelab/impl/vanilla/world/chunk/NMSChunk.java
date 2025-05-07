package de.verdox.mccreativelab.impl.vanilla.world.chunk;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.entity.MCCTeleportFlag;
import de.verdox.mccreativelab.wrapper.entity.types.MCCItemEntity;
import de.verdox.mccreativelab.wrapper.exceptions.OperationNotPossibleOnNMS;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.world.MCCEntitySpawnReason;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunkSection;
import de.verdox.mccreativelab.wrapper.world.coordinates.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

public class NMSChunk extends MCCHandle<LevelChunk> implements MCCChunk {
    public static final MCCConverter<LevelChunk, NMSChunk> CONVERTER = converter(NMSChunk.class, LevelChunk.class, NMSChunk::new, MCCHandle::getHandle);

    private final MCChunkPos chunkPos;

    public NMSChunk(LevelChunk handle) {
        super(handle);
        chunkPos = new MCChunkPos(handle.getPos().x, handle.getPos().z);
    }

    @Override
    @MCCReflective
    public boolean isLoaded() {
        return readFieldFromHandle("loaded", new TypeToken<Boolean>() {});
    }

    @Override
    public MCCWorld getWorld() {
        return conversionService.wrap(((ServerLevel) handle.getLevel()), new TypeToken<>() {});
    }

    @Override
    public MCChunkPos getChunkPos() {
        return chunkPos;
    }

    @Override
    public int getHeight() {
        return handle.getHeight();
    }

    @Override
    public int getHighestFilledSectionIndex() {
        return handle.getHighestFilledSectionIndex();
    }

    @Override
    public List<MCCEntity> getEntitiesInChunk() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public int getHighestNonAirBlock(int globalX, int globalZ) {
        return handle.getHeight(Heightmap.Types.WORLD_SURFACE, globalX, globalZ);
    }

    @Override
    public int getAmountChunkSections() {
        return handle.getSections().length;
    }

    @Override
    public List<MCCChunkSection> getChunkSections() {
        List<MCCChunkSection> list = new LinkedList<>();
        for (int i = 0; i < getAmountChunkSections(); i++) {
            list.add(getChunkSectionByIndex(i));
        }
        return list;
    }

    @Override
    public MCCChunkSection getChunkSectionByIndex(int index) {
        LevelChunkSection levelChunkSection = handle.getSection(index);
        return new NMSChunkSection(handle, levelChunkSection, index, chunkPos.x(), chunkPos.z());
    }

    @Override
    public MCCChunkSection getChunkSectionByGlobalYCoordinate(int blockHeight) {
        int maxBuildHeight = getWorld().getMaxBuildHeight();
        int minBuildHeight = getWorld().getMinBuildHeight();

        // Prüfen, ob die Y-Koordinate im gültigen Bereich liegt
        if (blockHeight < minBuildHeight || blockHeight > maxBuildHeight) {
            return null; // Außerhalb des Bauhöhenbereichs
        }

        // Chunk-Abschnittsindex berechnen
        int sectionIndex = (blockHeight - minBuildHeight) >> 4; // Division durch 16 (Schichtenhöhe)

        // Chunk-Abschnitt anhand des Index abrufen
        return getChunkSectionByIndex(sectionIndex);
    }

    @Override
    public MCCBlock get(Pos<?> pos) {
        return new MCCBlock(new MCCLocation(conversionService.wrap(handle.getLevel(), MCCWorld.class), pos.toPos()), this);
    }

    @Override
    public MCCBlock getHighest(double x, double z) {
        int height = handle.getHeight(Heightmap.Types.WORLD_SURFACE, MCCLocation.calculateBlockX(x), MCCLocation.calculateBlockZ(z));
        return new MCCBlock(new MCCLocation(conversionService.wrap(handle.getLevel(), MCCWorld.class), x, height, z), this);
    }

    @Override
    public void breakBlockNaturally(Pos<?> pos, @Nullable MCCItemStack tool, boolean triggerEffect, boolean dropLoot, boolean dropExperience, boolean ignoreTool) {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public void triggerBlockUpdate(Pos<?> pos) {
        checkAccess(pos);
        MCBlockPos blockPos = pos.toBlockPos();
        handle.getLevel().updateNeighborsAt(new BlockPos(blockPos.x(), blockPos.y(), blockPos.z()), conversionService.unwrap(get(pos).getBlockType(), Block.class));

    }

    @Override
    public <T extends MCCEntity> T summon(@NotNull Pos<?> pos, @NotNull MCCEntityType<T> mccEntityType, @NotNull MCCEntitySpawnReason spawnReason) {
        Preconditions.checkArgument(pos != null, "pos cannot be null");
        Preconditions.checkArgument(mccEntityType != null, "mccEntityType cannot be null");
        Preconditions.checkArgument(spawnReason != null, "spawnReason cannot be null");
        checkAccess(pos);
        MCCEntity constructedEntity = mccEntityType.constructNewEntity(getWorld(), spawnReason);
        Objects.requireNonNull(constructedEntity, "Could not construct an entity of type " + mccEntityType.key());
        Entity entity = conversionService.unwrap(constructedEntity);

        entity.setPos(new Vec3(pos.toPos().x(), pos.toPos().y(), pos.toPos().z()));

        if (entity instanceof Mob) {
            ((Mob) entity).finalizeSpawn((ServerLevelAccessor) this.getHandle().getLevel(), this.getHandle().getLevel().getCurrentDifficultyAt(entity.blockPosition()), conversionService.unwrap(spawnReason, EntitySpawnReason.class), null);
        }

        this.getHandle().getLevel().addFreshEntity(entity);
        return conversionService.wrap(entity);
    }

    @Override
    public boolean teleport(@NotNull Pos<?> pos, @NotNull MCCEntity entity, MCCTeleportFlag... flags) {
        Preconditions.checkArgument(pos != null, "pos cannot be null");
        Preconditions.checkArgument(entity != null, "entity cannot be null");
        checkAccess(pos);

        MCCLocation location = null;
        if (pos instanceof MCCLocation loc) {
            location = loc;
        } else {
            location = new MCCLocation(getWorld(), pos.toPos());
        }

        location.checkFinite();
        Set<MCCTeleportFlag> flagSet = new HashSet<>(List.of(flags));
        boolean dismount = !flagSet.contains(MCCTeleportFlag.RETAIN_VEHICLE);
        boolean retainPassengers = flagSet.contains(MCCTeleportFlag.RETAIN_PASSENGERS);

        boolean isWorldChange = Objects.equals(entity.getLocation().world(), location.world());
        var handle = conversionService.unwrap(entity, Entity.class);

        if (flagSet.contains(MCCTeleportFlag.RETAIN_PASSENGERS) && handle.isVehicle() && !isWorldChange) {
            LOGGER.log(Level.FINER, "Could not teleport " + handle + " because flag " + MCCTeleportFlag.RETAIN_PASSENGERS + " is active and the entity is a vehicle.");
            return false;
        } else if (!dismount && handle.isPassenger() && isWorldChange) {
            LOGGER.log(Level.FINER, "Could not teleport " + handle + " because flag the entity is a passenger and cannot be dismounted due to a world change");
            return false;
        } else if ((retainPassengers || !handle.isVehicle()) && !handle.isRemoved()) {
            if (dismount) {
                handle.stopRiding();
            }

            if (location.world() != null && !isWorldChange) {
                ServerLevel targetWorld = conversionService.unwrap(location.world(), ServerLevel.class);
                Vec3 targetPos = new Vec3(location.x(), location.y(), location.z());
                LOGGER.log(Level.FINER, "Teleporting " + handle + " to a location in a different world (" + entity.getLocation().world().key() + " -> " + location.world().key());
                handle.teleport(new TeleportTransition(targetWorld, targetPos, Vec3.ZERO, location.pitch(), location.yaw(), Set.of(), e -> {
                    LOGGER.log(Level.FINER, "Teleporting " + handle + " to a location in a different world");
                }));
            } else {
                handle.moveTo(location.x(), location.y(), location.z(), location.yaw(), location.pitch());
                handle.setYHeadRot(location.yaw());
                if (retainPassengers && handle.isVehicle()) {
                    // Teleport passengers
                    handle.getSelfAndPassengers().forEach(selfOrPassenger -> {
                        for (Entity passenger : selfOrPassenger.getPassengers()) {
                            Vec3 vec3 = handle.getPassengerRidingPosition(passenger);
                            Vec3 vec32 = passenger.getVehicleAttachmentPoint(handle);
                            passenger.moveTo(vec3.x - vec32.x, vec3.y - vec32.y, vec3.z - vec32.z);
                        }
                        LOGGER.log(Level.FINER, "Teleporting " + handle + " and its passengers to a position in the same world");
                    });
                }
            }
            return true;
        } else {
            LOGGER.log(Level.FINER, "Could not teleport " + handle + ". Is vehicle = " + handle.isVehicle() + ", Is removed = " + handle.isRemoved() + ", retainPassengers = " + retainPassengers);
            return false;
        }
    }

    @Override
    public MCCItemEntity dropItemNaturally(@NotNull Pos<?> pos, MCCItemStack item, @Nullable Consumer<MCCItemEntity> dropCallback) {
        Preconditions.checkArgument(pos != null, "pos cannot be null");
        Preconditions.checkArgument(item != null, "Item cannot be null");

        double xs = (handle.getLevel().random.nextFloat() * 0.5F) + 0.25D;
        double ys = (handle.getLevel().random.nextFloat() * 0.5F) + 0.25D;
        double zs = (handle.getLevel().random.nextFloat() * 0.5F) + 0.25D;
        pos = pos.toPos().add(new MCPos(xs, ys, zs));
        return this.dropItem(pos, item, dropCallback);
    }

    @Override
    public MCCItemEntity dropItem(@NotNull Pos<?> pos, MCCItemStack item, @Nullable Consumer<MCCItemEntity> dropCallback) {
        Preconditions.checkArgument(pos != null, "Location cannot be null");
        Preconditions.checkArgument(item != null, "Item cannot be null");

        MCPos mcPos = pos.toPos();

        ItemEntity entity = new ItemEntity(getHandle().getLevel(), mcPos.x(), mcPos.y(), mcPos.z(), conversionService.unwrap(item.copy()));
        entity.setPickUpDelay(10);
        MCCItemEntity mccEntity = conversionService.wrap(entity);
        if (dropCallback != null) {
            dropCallback.accept(mccEntity);
        }
        getHandle().getLevel().addFreshEntity(entity);
        return mccEntity;
    }

    @Override
    public boolean canAccess(Pos<?> pos) {
        return getWorld().canAccess(pos) && pos.toChunkPos().equals(chunkPos);
    }
}
