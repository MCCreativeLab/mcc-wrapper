package de.verdox.mccreativelab.impl.paper.world.chunk;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.impl.vanilla.world.chunk.NMSChunk;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCTeleportFlag;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.typed.MCCBlocks;
import de.verdox.mccreativelab.wrapper.world.coordinates.Pos;
import io.papermc.paper.entity.TeleportFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class PaperChunk extends NMSChunk {
    public static final MCCConverter<LevelChunk, PaperChunk> CONVERTER = converter(PaperChunk.class, LevelChunk.class, PaperChunk::new, MCCHandle::getHandle);

    public PaperChunk(LevelChunk handle) {
        super(handle);
    }

    @Override
    public void breakBlockNaturally(Pos<?> pos, @Nullable MCCItemStack tool, boolean triggerEffect, boolean dropLoot, boolean dropExperience, boolean ignoreTool) {
        boolean result = false;
        MCCBlock mccBlock = get(pos);
        BlockPos blockPos = new BlockPos(mccBlock.getLocation().blockX(), mccBlock.getLocation().blockY(), mccBlock.getLocation().blockZ());
        BlockState nmsBlockState = getHandle().getBlockState(blockPos);
        if (!mccBlock.getBlockType().equals(MCCBlocks.AIR) && (tool == null || !mccBlock.getBlockState().requiresCorrectToolForDrops() || tool.isCorrectToolForDrops(mccBlock.getBlockState()))) {

            mccBlock.dropBlockLoot(null, tool);
            if (triggerEffect) {
                if (mccBlock.getBlockType().equals(MCCBlocks.FIRE)) {
                    getHandle().getLevel().levelEvent(net.minecraft.world.level.block.LevelEvent.SOUND_EXTINGUISH_FIRE, blockPos, 0);
                } else {
                    //TODO Customize
                    getHandle().getLevel().levelEvent(net.minecraft.world.level.block.LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, net.minecraft.world.level.block.Block.getId(nmsBlockState));
                }
            }
            if (dropExperience) {
                //TODO
                //block.popExperience(this.world.getMinecraftWorld(), this.position, block.getExpDrop(iblockdata, this.world.getMinecraftWorld(), this.position, nmsItem, true));
            }

            result = true;
        }

        boolean destroyed = getHandle().getLevel().removeBlock(blockPos, false);
        if (destroyed) {
            nmsBlockState.getBlock().destroy(getHandle().getLevel(), blockPos, nmsBlockState);
        }
        if (result) {
            // special cases
            if (nmsBlockState.getBlock() instanceof net.minecraft.world.level.block.IceBlock iceBlock) {
                iceBlock.afterDestroy(getHandle().getLevel(), blockPos, conversionService.unwrap(tool));
            } else if (nmsBlockState.getBlock() instanceof net.minecraft.world.level.block.TurtleEggBlock turtleEggBlock) {
                invokeMethodInHandle("decreaseEggs", blockPos, nmsBlockState); // TODO: should do turtleEggBlock.decreaseEggs(getHandle(), blockPos, nmsBlockState); (check this)
            }
        }
    }

    @Override
    public boolean teleport(@NotNull Pos<?> pos, @NotNull MCCEntity entity, MCCTeleportFlag... flags) {
        var bukkitFlags = Arrays.stream(flags).map(mccTeleportFlag -> BukkitAdapter.unwrap(mccTeleportFlag, TeleportFlag.EntityState.class)).toArray(TeleportFlag.EntityState[]::new);
        Entity bukkitEntity = BukkitAdapter.unwrap(entity, Entity.class);
        return bukkitEntity.teleport(BukkitAdapter.unwrap(pos, Location.class), PlayerTeleportEvent.TeleportCause.PLUGIN, bukkitFlags);
    }
}
