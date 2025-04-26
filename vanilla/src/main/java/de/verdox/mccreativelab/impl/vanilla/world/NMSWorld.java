package de.verdox.mccreativelab.impl.vanilla.world;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.entity.types.MCCItemEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.TempCache;
import de.verdox.mccreativelab.wrapper.platform.TempData;
import de.verdox.mccreativelab.wrapper.util.math.AxisAlignedBoundingBox;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.pointer.Pointers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class NMSWorld extends MCCHandle<ServerLevel> implements MCCWorld {
    public static final MCCConverter<ServerLevel, NMSWorld> CONVERTER = converter(NMSWorld.class, ServerLevel.class, NMSWorld::new, MCCHandle::getHandle);
    private Pointers adventurePointer;

    public NMSWorld(ServerLevel handle) {
        super(handle);
    }

    @Override
    public String getName() {
        return readFieldFromHandle("serverLevelData", new TypeToken<ServerLevelData>() {}).getLevelName();
    }

    @Override
    public void breakBlockNaturally(MCCBlock mccBlock, @Nullable MCCItemStack tool, boolean triggerEffect, boolean dropLoot, boolean dropExperience, boolean ignoreTool) {

    }

    @Override
    public MCCItemEntity dropItemNaturally(MCCLocation location, MCCItemStack item, @Nullable Consumer<MCCItemEntity> dropCallback) {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(item != null, "Item cannot be null");

        double xs = (handle.random.nextFloat() * 0.5F) + 0.25D;
        double ys = (handle.random.nextFloat() * 0.5F) + 0.25D;
        double zs = (handle.random.nextFloat() * 0.5F) + 0.25D;
        location = location.add(xs, ys, zs);
        return this.dropItem(location, item, dropCallback);
    }

    @Override
    public MCCItemEntity dropItem(MCCLocation location, MCCItemStack item, @Nullable Consumer<MCCItemEntity> dropCallback) {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(item != null, "Item cannot be null");

        ItemEntity entity = new ItemEntity(getHandle(), location.x(), location.y(), location.z(), conversionService.unwrap(item.copy()));
        entity.setPickUpDelay(10);
        MCCItemEntity mccEntity = conversionService.wrap(entity);
        if (dropCallback != null) {
            dropCallback.accept(mccEntity);
        }
        getHandle().addFreshEntity(entity);
        return mccEntity;
    }

    @Override
    public List<MCCPlayer> getPlayers() {
        return conversionService.wrap(handle.getPlayers(serverPlayer -> true));
    }

    @Override
    public CompletableFuture<MCCEntity> summon(@NotNull MCCLocation location, @NotNull MCCEntityType mccEntityType) {
        MCCEntity constructedEntity = mccEntityType.constructNewEntity();
        Entity entity = conversionService.unwrap(constructedEntity);

        if (entity instanceof Mob) {
            ((Mob) entity).finalizeSpawn(this.getHandle(), this.getHandle().getCurrentDifficultyAt(entity.blockPosition()), EntitySpawnReason.COMMAND, null);
        }

        this.getHandle().addFreshEntity(entity);
        return CompletableFuture.completedFuture(conversionService.wrap(entity));
    }

    @Override
    public CompletableFuture<MCCChunk> getOrLoadChunk(int chunkX, int chunkZ) {
        return conversionService.wrap(handle.getChunk(chunkX, chunkZ, ChunkStatus.FULL, true));
    }

    @Override
    public CompletableFuture<MCCChunk> getOrLoadChunk(MCCLocation location) {
        return conversionService.wrap(handle.getChunk(location.getChunkX(), location.getChunkZ(), ChunkStatus.FULL, true));
    }

    @Override
    public @Nullable MCCChunk getChunkImmediately(int x, int z) {
        return null;
    }

    @Override
    public @Nullable MCCChunk getChunkImmediately(MCCLocation location) {
        return null;
    }

    @Override
    public UUID getUUID() {
        return null;
    }

    @Override
    public void triggerBlockUpdate(MCCLocation location) {
        Objects.requireNonNull(location.getBlockNow(), "The chunk at the location " + location + " is not loaded.");
        handle.updateNeighborsAt(new BlockPos(location.blockX(), location.blockY(), location.blockZ()), conversionService.unwrap(location.getBlockNow().getBlockType()));
    }

    @Override
    public int getMaxBuildHeight() {
        return handle.getMaxY();
    }

    @Override
    public int getMinBuildHeight() {
        return handle.getMinY();
    }

    @Override
    public @Nullable MCCEntity get(UUID entityUUID) {
        return conversionService.wrap(handle.getEntity(entityUUID), MCCEntity.class);
    }

    @Override
    public MCCEntity get(int entityId) {
        return conversionService.wrap(handle.getEntity(entityId), MCCEntity.class);
    }

    @Override
    public List<MCCEntity> getEntities(@Nullable MCCEntity entityToExclude, AxisAlignedBoundingBox boundingBox, Predicate<MCCEntity> filter) {
        Entity nmsToExclude = conversionService.unwrap(entityToExclude, Entity.class);
        AABB aabb = new AABB(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.maxY(), boundingBox.maxZ());
        return conversionService.wrap(handle.getEntities(nmsToExclude, aabb, entity -> filter.test(conversionService.wrap(entity))));
    }

    @Override
    public @Nullable MCCPlayer getNearestPlayer(double x, double y, double z, double distance, Predicate<MCCPlayer> filter) {
        return conversionService.wrap(handle.getNearestPlayer(x, y, z, distance, player -> filter.test(conversionService.wrap(player, MCCPlayer.class))), MCCPlayer.class);
    }

    @Override
    public boolean hasNearbyAlivePlayer(double x, double y, double z, double distance) {
        return handle.hasNearbyAlivePlayer(x,y,z,distance);
    }

    @Override
    public @Nullable MCCPlayer getPlayer(UUID playerUUID) {
        return conversionService.wrap(handle.getPlayerByUUID(playerUUID));
    }

    @Override
    public @NotNull Key key() {
        return conversionService.wrap(handle.dimension().location());
    }

    @Override
    public TempData getTempData() {
        return TempCache.get(getHandle());
    }

    @Override
    public Pointers pointers() {
        if (this.adventurePointer == null) {
            this.adventurePointer = Pointers.builder()
                    .withDynamic(net.kyori.adventure.identity.Identity.NAME, this::getName)
                    .withDynamic(net.kyori.adventure.identity.Identity.UUID, this::getUUID)
                    .build();
        }

        return this.adventurePointer;
    }

    @Override
    public Key getRegistryKey() {
        return Key.key("minecraft", "dimension");
    }
}
