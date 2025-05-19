package de.verdox.mccreativelab.impl.paper.world;

import de.verdox.mccreativelab.impl.vanilla.world.NMSWorld;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCChunkPos;
import de.verdox.mccreativelab.wrapper.world.coordinates.Pos;
import net.minecraft.server.level.ServerLevel;

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
    public CompletableFuture<MCCChunk> getOrLoadChunk(Pos<?> pos) {
        MCChunkPos chunkPos = pos.toChunkPos();
        return handle.getWorld().getChunkAtAsync(chunkPos.x(), chunkPos.z()).thenApply(chunk -> conversionService.wrap(chunk));
    }

    @Override
    public UUID getUUID() {
        return handle.getLevel().uuid;
    }
}
