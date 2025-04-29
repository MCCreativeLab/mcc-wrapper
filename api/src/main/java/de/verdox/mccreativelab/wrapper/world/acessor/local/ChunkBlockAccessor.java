package de.verdox.mccreativelab.wrapper.world.acessor.local;

import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.acessor.global.WorldBlockAccessor;
import de.verdox.mccreativelab.wrapper.world.acessor.point.PointBlockAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ChunkBlockAccessor<
        SELF extends ChunkBlockAccessor<SELF, WORLD_ACCESS, POINT_ACCESS>,
        WORLD_ACCESS extends WorldBlockAccessor<WORLD_ACCESS, SELF, POINT_ACCESS>,
        POINT_ACCESS extends PointBlockAccessor<POINT_ACCESS, WORLD_ACCESS, SELF>
        > extends ChunkAccessor<SELF, WORLD_ACCESS> {
    POINT_ACCESS get(int x, int y, int z);

    POINT_ACCESS getHighest(int x, int z);

    MCCBlockState getBlockDataAtLocal(int localX, int localY, int localZ);

    void setBlockLocal(@NotNull MCCBlockState mccBlockState, int localX, int localY, int localZ, boolean triggerBlockUpdate);

    /**
     * Naturally breaks this block as if a player had broken it.
     *
     * @param tool           the tool used
     * @param triggerEffect  whether to trigger a block break effect
     * @param dropExperience whether to drop Experience
     * @param ignoreTool     whether to ignore the tool
     */
    void breakBlockNaturally(int globalX, int globalY, int globalZ, @Nullable MCCItemStack tool, boolean triggerEffect, boolean dropLoot, boolean dropExperience, boolean ignoreTool);

    /**
     * Triggers a block update at world coordinates
     */
    void triggerBlockUpdate(int globalX, int globalY, int globalZ);

    // DEFAULT METHODS

    default POINT_ACCESS get(MCCLocation mccLocation) {
        return get(mccLocation.blockX(), mccLocation.blockY(), mccLocation.blockZ());
    }

    default POINT_ACCESS getHighest(MCCLocation mccLocation) {
        return getHighest(mccLocation.blockX(), mccLocation.blockZ());
    }

    /**
     * Triggers a block update at world coordinates
     */
    default void triggerBlockUpdate(MCCLocation location) {
        checkAccess(location);
        triggerBlockUpdate(location.blockX(), location.blockY(), location.blockZ());
    }

    default void breakBlockNaturally(MCCLocation location, @Nullable MCCItemStack tool, boolean triggerEffect, boolean dropLoot, boolean dropExperience, boolean ignoreTool) {
        checkAccess(location);
        breakBlockNaturally(location.blockX(), location.blockY(), location.blockZ(), tool, triggerEffect, dropLoot, dropExperience, ignoreTool);
    }

    default MCCBlockType getBlockTypeAtLocal(int localX, int globalY, int localZ) {
        return getBlockDataAtLocal(localX, globalY, localZ).getBlockType();
    }

    default MCCBlockState getBlockDataAt(MCCLocation location) {
        checkAccess(location);
        return getBlockDataAtLocal(location.toChunkBlockLocalX(), location.blockY(), location.toChunkBlockLocalZ());
    }

    default MCCBlockType getBlockTypeAt(MCCLocation location) {
        checkAccess(location);
        return getBlockTypeAtLocal(location.toChunkBlockLocalX(), location.blockY(), location.toChunkBlockLocalZ());
    }

    default MCCBlockType getBlockTypeAt(int globalX, int globalY, int globalZ) {
        return getBlockTypeAtLocal(MCCLocation.calculateBlockLocalX(globalX), globalY, MCCLocation.calculateBlockLocalZ(globalZ));
    }

    default MCCBlockState getBlockDataAt(int globalX, int globalY, int globalZ) {
        return getBlockDataAtLocal(MCCLocation.calculateBlockLocalX(globalX), globalY, MCCLocation.calculateBlockLocalZ(globalZ));
    }

    default void setBlock(@NotNull MCCBlockType mccBlockType, MCCLocation location, boolean triggerBlockUpdate) {
        checkAccess(location);
        setBlockLocal(mccBlockType, location.toChunkBlockLocalX(), location.blockY(), location.toChunkBlockLocalZ(), triggerBlockUpdate);
    }

    default void setBlock(@NotNull MCCBlockState mccBlockState, MCCLocation location, boolean triggerBlockUpdate) {
        checkAccess(location);
        setBlockLocal(mccBlockState, location.toChunkBlockLocalX(), location.blockY(), location.toChunkBlockLocalZ(), triggerBlockUpdate);
    }

    default void setBlock(@NotNull MCCBlockType mccBlockType, int globalX, int globalY, int globalZ, boolean triggerBlockUpdate) {
        setBlockLocal(mccBlockType, MCCLocation.calculateBlockLocalX(globalX), globalY, MCCLocation.calculateBlockLocalZ(globalZ), triggerBlockUpdate);
    }

    default void setBlock(@NotNull MCCBlockState mccBlockState, int globalX, int globalY, int globalZ, boolean triggerBlockUpdate) {
        setBlockLocal(mccBlockState, MCCLocation.calculateBlockLocalX(globalX), globalY, MCCLocation.calculateBlockLocalZ(globalZ), triggerBlockUpdate);
    }

    default void setBlockLocal(@NotNull MCCBlockType mccBlockType, int localX, int localY, int localZ, boolean triggerBlockUpdate) {
        setBlockLocal(mccBlockType.getDefaultState(), localX, localY, localZ, triggerBlockUpdate);
    }
}
