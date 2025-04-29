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
    public UUID getUUID() {
        return handle.getLevel().uuid;
    }
}
