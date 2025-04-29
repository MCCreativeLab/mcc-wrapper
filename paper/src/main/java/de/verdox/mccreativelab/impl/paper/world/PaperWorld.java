package de.verdox.mccreativelab.impl.paper.world;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.impl.vanilla.world.NMSWorld;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCTeleportFlag;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.typed.MCCBlocks;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import io.papermc.paper.entity.TeleportFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PaperWorld extends NMSWorld {
    public PaperWorld(ServerLevel handle) {
        super(handle);
    }

    @Override
    public List<MCCPlayer> getPlayers() {
        return conversionService.wrap(handle.getPlayers(serverPlayer -> serverPlayer.isRealPlayer).stream().toList());
    }

    @Override
    public CompletableFuture<MCCChunk> getOrLoadChunk(int chunkX, int chunkZ) {
        return handle.getWorld().getChunkAtAsync(chunkX, chunkZ).thenApply(chunk -> conversionService.wrap(chunk));
    }

    @Override
    public CompletableFuture<MCCChunk> getOrLoadChunk(MCCLocation location) {
        return handle.getWorld().getChunkAtAsync(conversionService.unwrap(location, new TypeToken<Location>(){})).thenApply(chunk -> conversionService.wrap(chunk));
    }

    @Override
    public CompletableFuture<MCCEntity> teleport(@NotNull MCCEntity entity, @NotNull MCCLocation location, MCCTeleportFlag... flags) {
        CompletableFuture<MCCEntity> future = new CompletableFuture<>();
        var bukkitFlags = Arrays.stream(flags).map(mccTeleportFlag -> BukkitAdapter.unwrap(mccTeleportFlag, TeleportFlag.EntityState.class)).toArray(TeleportFlag.EntityState[]::new);
        Entity bukkitEntity = BukkitAdapter.unwrap(entity, Entity.class);
        bukkitEntity.teleportAsync(BukkitAdapter.unwrap(location, Location.class), PlayerTeleportEvent.TeleportCause.PLUGIN, bukkitFlags).whenComplete((aBoolean, throwable) -> {
            if(throwable != null) {
                future.completeExceptionally(throwable);
                return;
            }
            if(aBoolean) {
                future.complete(entity);
            }
            else {
                future.complete(null);
            }
        });
        return future;
    }

    @Override
    @MCCReflective
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
}
