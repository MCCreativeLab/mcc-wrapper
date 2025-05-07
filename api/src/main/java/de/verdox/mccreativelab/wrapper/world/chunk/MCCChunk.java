package de.verdox.mccreativelab.wrapper.world.chunk;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkBlockAccessor;
import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkEntityAccessor;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCLocalBlockPos;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCPos;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

public interface MCCChunk extends MCCWrapped, ChunkBlockAccessor<MCCChunk, MCCWorld, MCCBlock>, ChunkEntityAccessor<MCCChunk, MCCWorld> {
    Logger LOGGER = Logger.getLogger(MCCChunk.class.getSimpleName());

    boolean isLoaded();

    MCCWorld getWorld();

    int getHeight();

    int getHighestFilledSectionIndex();

    List<MCCEntity> getEntitiesInChunk();

    int getHighestNonAirBlock(int globalX, int globalZ);

    int getAmountChunkSections();

    List<MCCChunkSection> getChunkSections();

    MCCChunkSection getChunkSectionByIndex(@NonNegative int index);

    MCCChunkSection getChunkSectionByGlobalYCoordinate(int blockHeight);

    @Override
    default MCCBlockState getBlockDataAtLocal(MCLocalBlockPos localBlockPos) {
        MCCChunkSection chunkSection = getChunkSectionByGlobalYCoordinate(localBlockPos.globalY());
        return chunkSection.getBlockState(localBlockPos.localX(), MCCLocation.calculateBlockLocalY(localBlockPos.globalY()), localBlockPos.localZ());
    }

    @Override
    default void setBlockLocal(@NotNull MCCBlockState mccBlockState, MCLocalBlockPos localBlockPos, boolean triggerBlockUpdate) {
        MCCChunkSection chunkSection = getChunkSectionByGlobalYCoordinate(localBlockPos.globalY());

        chunkSection.setBlockState(localBlockPos.localX(), MCCLocation.calculateBlockLocalY(localBlockPos.globalY()), localBlockPos.localZ(), mccBlockState);
        if (triggerBlockUpdate) {
            triggerBlockUpdate(localBlockPos.toPos(getChunkPos()));
        }
    }
}
