package de.verdox.mccreativelab.impl.vanilla.world;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.cached.TempCache;
import de.verdox.mccreativelab.wrapper.platform.cached.TempData;
import de.verdox.mccreativelab.wrapper.util.math.AxisAlignedBoundingBox;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCChunkPos;
import de.verdox.mccreativelab.wrapper.world.coordinates.Pos;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.pointer.Pointers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
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
    public List<MCCPlayer> getPlayers() {
        return conversionService.wrap(handle.getPlayers(serverPlayer -> true), new TypeToken<>() {});
    }

    @Override
    public UUID getUUID() {
        return null;
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
        return conversionService.wrap(handle.getEntities(nmsToExclude, aabb, entity -> filter.test(conversionService.wrap(entity, new TypeToken<>() {}))), new TypeToken<>() {});
    }

    @Override
    public @Nullable MCCPlayer getNearestPlayer(double x, double y, double z, double distance, Predicate<MCCPlayer> filter) {
        return conversionService.wrap(handle.getNearestPlayer(x, y, z, distance, player -> filter.test(conversionService.wrap(player, MCCPlayer.class))), MCCPlayer.class);
    }

    @Override
    public boolean hasNearbyAlivePlayer(double x, double y, double z, double distance) {
        return handle.hasNearbyAlivePlayer(x, y, z, distance);
    }

    @Override
    public @Nullable MCCPlayer getPlayer(UUID playerUUID) {
        return conversionService.wrap(handle.getPlayerByUUID(playerUUID), new TypeToken<>() {});
    }

    @Override
    public List<MCCEntity> getEntities() {
        List<MCCEntity> result = new ArrayList<>();
        handle.getAllEntities().forEach(entity -> result.add(conversionService.wrap(entity, MCCEntity.class)));
        return List.copyOf(result);
    }

    @Override
    public @NotNull Key key() {
        return conversionService.wrap(handle.dimension().location(), new TypeToken<>() {});
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

    @Override
    public CompletableFuture<MCCChunk> getOrLoadChunk(Pos<?> pos) {
        CompletableFuture<MCCChunk> future = new CompletableFuture<>();
        MCChunkPos chunkPos = pos.toChunkPos();
        future.complete(conversionService.wrap(handle.getChunk(chunkPos.x(), chunkPos.z(), ChunkStatus.FULL, true), new TypeToken<>() {}));
        return future;
    }

    @Override
    public @Nullable MCCChunk getChunkImmediately(Pos<?> pos) {
        MCChunkPos chunkPos = pos.toChunkPos();
        return conversionService.wrap(handle.getChunk(chunkPos.x(), chunkPos.z(), ChunkStatus.FULL, false), new TypeToken<>() {});
    }

    @Override
    public boolean canAccess(Pos<?> pos) {
        if(pos instanceof MCCLocation mccLocation) {
            return mccLocation.world().key().equals(key());
        }
        return true;
    }
}
