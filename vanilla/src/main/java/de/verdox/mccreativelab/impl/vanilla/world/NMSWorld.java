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
import de.verdox.mccreativelab.wrapper.typed.MCCBlocks;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.pointer.Pointers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.storage.ServerLevelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
    public void breakBlockNaturally(@NotNull MCCBlock mccBlock, @Nullable MCCItemStack tool, boolean triggerEffect, boolean dropLoot, boolean dropExperience, boolean ignoreTool) {
        boolean result = false;
        BlockPos blockPos = new BlockPos(mccBlock.getLocation().blockX(), mccBlock.getLocation().blockY(), mccBlock.getLocation().blockZ());
        BlockState nmsBlockState = getHandle().getBlockState(blockPos);
        if (!mccBlock.getBlockType().equals(MCCBlocks.AIR) && (tool == null || !mccBlock.getBlockState().requiresCorrectToolForDrops() || tool.isCorrectToolForDrops(mccBlock.getBlockState()))) {

            mccBlock.dropBlockLoot(null, conversionService.unwrap(tool));
            if (triggerEffect) {
                if (mccBlock.getBlockType().equals(MCCBlocks.FIRE)) {
                    getHandle().levelEvent(net.minecraft.world.level.block.LevelEvent.SOUND_EXTINGUISH_FIRE, blockPos, 0);
                } else {
                    //TODO Customize
                    getHandle().levelEvent(net.minecraft.world.level.block.LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, net.minecraft.world.level.block.Block.getId(nmsBlockState));
                }
            }
            if (dropExperience) {
                //TODO
                //block.popExperience(this.world.getMinecraftWorld(), this.position, block.getExpDrop(iblockdata, this.world.getMinecraftWorld(), this.position, nmsItem, true));
            }

            result = true;
        }


        boolean destroyed = getHandle().removeBlock(blockPos, false);
        if (destroyed) {
            nmsBlockState.getBlock().destroy(getHandle(), blockPos, nmsBlockState);
        }
        if (result) {
            // special cases
            if (nmsBlockState.getBlock() instanceof net.minecraft.world.level.block.IceBlock iceBlock) {
                iceBlock.afterDestroy(getHandle(), blockPos, conversionService.unwrap(tool));
            } else if (nmsBlockState.getBlock() instanceof net.minecraft.world.level.block.TurtleEggBlock turtleEggBlock) {
                invokeMethodInHandle("decreaseEggs", blockPos, nmsBlockState); // TODO: should do turtleEggBlock.decreaseEggs(getHandle(), blockPos, nmsBlockState); (check this)
            }
        }
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
            ((Mob) entity).finalizeSpawn(this.getHandle(), this.getHandle().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.COMMAND, null);
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
        return conversionService.wrap(handle.getChunkIfLoadedImmediately(x, z));
    }

    @Override
    public @Nullable MCCChunk getChunkImmediately(MCCLocation location) {
        return conversionService.wrap(handle.getChunkIfLoadedImmediately(location.getChunkX(), location.getChunkZ()));
    }

    @Override
    public UUID getUUID() {
        return handle.getLevel().uuid;
    }

    @Override
    public void triggerBlockUpdate(MCCLocation location) {
        Objects.requireNonNull(location.getBlockNow(), "The chunk at the location " + location + " is not loaded.");
        handle.updateNeighborsAt(new BlockPos(location.blockX(), location.blockY(), location.blockZ()), conversionService.unwrap(location.getBlockNow().getBlockType()));
    }

    @Override
    public int getMaxBuildHeight() {
        return handle.getMaxBuildHeight();
    }

    @Override
    public int getMinBuildHeight() {
        return handle.getMinBuildHeight();
    }

    @Override
    public @NotNull Key key() {
        return conversionService.wrap(handle.getTypeKey().location());
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
