package de.verdox.mccreativelab.wrapper.world.chunk;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkBlockAccessor;
import de.verdox.mccreativelab.wrapper.world.acessor.local.ChunkEntityAccessor;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface MCCChunk extends MCCWrapped, ChunkBlockAccessor<MCCChunk, MCCWorld, MCCBlock>, ChunkEntityAccessor<MCCChunk, MCCWorld> {
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
    default MCCBlockState getBlockDataAtLocal(int localX, int localY, int localZ) {
        MCCChunkSection chunkSection = getChunkSectionByGlobalYCoordinate(localY);
        return chunkSection.getBlockState(localX, localY, localZ);
    }

    @Override
    default void setBlockLocal(@NotNull MCCBlockState mccBlockState, int localX, int globalY, int localZ, boolean triggerBlockUpdate) {
        MCCChunkSection chunkSection = getChunkSectionByGlobalYCoordinate(globalY);
        chunkSection.setBlockState(localX, globalY, localZ, mccBlockState);
        if (triggerBlockUpdate) {
            triggerBlockUpdate(
                    MCCLocation.calculateBlockGlobalX(chunkX(), localX),
                    globalY,
                    MCCLocation.calculateBlockGlobalZ(chunkZ(), localZ)
            );
        }
    }
}
