package de.verdox.mccreativelab.impl.paper.world;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.impl.vanilla.world.NMSWorld;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCChunkPos;
import de.verdox.mccreativelab.wrapper.world.coordinates.Pos;
import net.minecraft.server.level.ServerLevel;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PaperWorld extends NMSWorld {
    public static final MCCConverter<ServerLevel, PaperWorld> CONVERTER = converter(PaperWorld.class, ServerLevel.class, PaperWorld::new, MCCHandle::getHandle);

    public PaperWorld(ServerLevel handle) {
        super(handle);
    }

    @Override
    public CompletableFuture<MCCChunk> getOrLoadChunk(Pos<?> pos) {
        MCChunkPos chunkPos = pos.toChunkPos();
        return handle.getWorld().getChunkAtAsync(chunkPos.x(), chunkPos.z()).thenApply(chunk -> BukkitAdapter.toMcc(chunk, MCCChunk.class));
    }

    @Override
    public UUID getUUID() {
        return handle.getLevel().uuid;
    }
}
