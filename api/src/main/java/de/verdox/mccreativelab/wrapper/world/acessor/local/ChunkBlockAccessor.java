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
    POINT_ACCESS get(double x, double y, double z);

    POINT_ACCESS getHighest(double x, double z);

    MCCBlockState getBlockDataAtLocal(int localX, int globalBlockY, int localZ);

    void setBlockLocal(@NotNull MCCBlockState mccBlockState, int localX, int globalY, int localZ, boolean triggerBlockUpdate);

    /**
     * Naturally breaks this block as if a player had broken it.
     *
     * @param tool           the tool used
     * @param triggerEffect  whether to trigger a block break effect
     * @param dropExperience whether to drop Experience
     * @param ignoreTool     whether to ignore the tool
     */
    void breakBlockNaturally(double globalX, double globalY, double globalZ, @Nullable MCCItemStack tool, boolean triggerEffect, boolean dropLoot, boolean dropExperience, boolean ignoreTool);

    /**
     * Triggers a block update at world coordinates
     */
    void triggerBlockUpdate(double globalX, double globalY, double globalZ);

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

    default MCCBlockType getBlockTypeAtLocal(int localX, int globalBlockY, int localZ) {
        return getBlockDataAtLocal(localX, globalBlockY, localZ).getBlockType();
    }

    default MCCBlockState getBlockDataAt(MCCLocation location) {
        checkAccess(location);
        return getBlockDataAtLocal(location.toChunkBlockLocalX(), location.blockY(), location.toChunkBlockLocalZ());
    }

    default MCCBlockType getBlockTypeAt(MCCLocation location) {
        checkAccess(location);
        return getBlockTypeAtLocal(location.toChunkBlockLocalX(), location.blockY(), location.toChunkBlockLocalZ());
    }

    default MCCBlockType getBlockTypeAt(double globalX, double globalY, double globalZ) {
        return getBlockTypeAtLocal(MCCLocation.calculateBlockLocalX(globalX), MCCLocation.calculateBlockY(globalY), MCCLocation.calculateBlockLocalZ(globalZ));
    }

    default MCCBlockState getBlockDataAt(double globalX, double globalY, double globalZ) {
        return getBlockDataAtLocal(MCCLocation.calculateBlockLocalX(globalX), MCCLocation.calculateBlockY(globalY), MCCLocation.calculateBlockLocalZ(globalZ));
    }

    default void setBlock(@NotNull MCCBlockType mccBlockType, MCCLocation location, boolean triggerBlockUpdate) {
        checkAccess(location);
        setBlockLocal(mccBlockType, location.toChunkBlockLocalX(), location.blockY(), location.toChunkBlockLocalZ(), triggerBlockUpdate);
    }

    default void setBlock(@NotNull MCCBlockState mccBlockState, MCCLocation location, boolean triggerBlockUpdate) {
        checkAccess(location);
        setBlockLocal(mccBlockState, location.toChunkBlockLocalX(), location.blockY(), location.toChunkBlockLocalZ(), triggerBlockUpdate);
    }

    default void setBlock(@NotNull MCCBlockType mccBlockType, double globalX, double globalY, double globalZ, boolean triggerBlockUpdate) {
        setBlockLocal(mccBlockType, MCCLocation.calculateBlockLocalX(globalX), MCCLocation.calculateBlockY(globalY), MCCLocation.calculateBlockLocalZ(globalZ), triggerBlockUpdate);
    }

    default void setBlock(@NotNull MCCBlockState mccBlockState, double globalX, double globalY, double globalZ, boolean triggerBlockUpdate) {
        setBlockLocal(mccBlockState, MCCLocation.calculateBlockLocalX(globalX), MCCLocation.calculateBlockY(globalY), MCCLocation.calculateBlockLocalZ(globalZ), triggerBlockUpdate);
    }

    default void setBlockLocal(@NotNull MCCBlockType mccBlockType, int localX, int globalBlockY, int localZ, boolean triggerBlockUpdate) {
        setBlockLocal(mccBlockType.getDefaultState(), localX, globalBlockY, localZ, triggerBlockUpdate);
    }
}
