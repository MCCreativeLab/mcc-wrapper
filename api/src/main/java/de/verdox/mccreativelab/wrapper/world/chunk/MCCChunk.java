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
    default MCCBlockState getBlockDataAtLocal(int localX, int globalY, int localZ) {
        MCCChunkSection chunkSection = getChunkSectionByGlobalYCoordinate(globalY);
        return chunkSection.getBlockState(localX, MCCLocation.calculateBlockLocalY(globalY), localZ);
    }

    @Override
    default void setBlockLocal(@NotNull MCCBlockState mccBlockState, int localX, int globalY, int localZ, boolean triggerBlockUpdate) {
        MCCChunkSection chunkSection = getChunkSectionByGlobalYCoordinate(globalY);
        chunkSection.setBlockState(localX, MCCLocation.calculateBlockLocalY(globalY), localZ, mccBlockState);
        if (triggerBlockUpdate) {
            triggerBlockUpdate(
                    MCCLocation.calculateBlockGlobalX(chunkX(), localX),
                    globalY,
                    MCCLocation.calculateBlockGlobalZ(chunkZ(), localZ)
            );
        }
    }

    @Override
    default boolean canAccess(MCCLocation mccLocation) {
        return getWorld().canAccess(mccLocation) && mccLocation.getChunkX() == chunkX() && mccLocation.getChunkZ() == chunkZ();
    }

    @Override
    default boolean canAccess(int x, int y, int z) {
        return getWorld().canAccess(x, y, z) && MCCLocation.calculateChunkX(x) == chunkX() && MCCLocation.calculateChunkZ(z) == chunkZ();
    }
}
