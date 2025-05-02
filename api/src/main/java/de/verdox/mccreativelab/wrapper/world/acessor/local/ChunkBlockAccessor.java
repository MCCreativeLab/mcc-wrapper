package de.verdox.mccreativelab.wrapper.world.acessor.local;

import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.acessor.global.WorldBlockAccessor;
import de.verdox.mccreativelab.wrapper.world.acessor.point.PointBlockAccessor;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCLocalBlockPos;
import de.verdox.mccreativelab.wrapper.world.coordinates.Pos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ChunkBlockAccessor<
        SELF extends ChunkBlockAccessor<SELF, WORLD_ACCESS, POINT_ACCESS>,
        WORLD_ACCESS extends WorldBlockAccessor<WORLD_ACCESS, SELF, POINT_ACCESS>,
        POINT_ACCESS extends PointBlockAccessor<POINT_ACCESS, WORLD_ACCESS, SELF>
        > extends ChunkAccessor<SELF, WORLD_ACCESS> {
    POINT_ACCESS get(Pos<?> pos);

    POINT_ACCESS getHighest(double x, double z);

    MCCBlockState getBlockDataAtLocal(MCLocalBlockPos localBlockPos);

    default MCCBlockType getBlockTypeAtLocal(MCLocalBlockPos localBlockPos) {
        return getBlockDataAtLocal(localBlockPos).getBlockType();
    }

    void setBlockLocal(@NotNull MCCBlockState mccBlockState, MCLocalBlockPos localBlockPos, boolean triggerBlockUpdate);

    default void setBlockLocal(@NotNull MCCBlockType mccBlockType, MCLocalBlockPos localBlockPos, boolean triggerBlockUpdate) {
        setBlockLocal(mccBlockType.getDefaultState(), localBlockPos, triggerBlockUpdate);
    }

    /**
     * Naturally breaks this block as if a player had broken it.
     *
     * @param tool           the tool used
     * @param triggerEffect  whether to trigger a block break effect
     * @param dropExperience whether to drop Experience
     * @param ignoreTool     whether to ignore the tool
     */
    void breakBlockNaturally(Pos<?> pos, @Nullable MCCItemStack tool, boolean triggerEffect, boolean dropLoot, boolean dropExperience, boolean ignoreTool);

    /**
     * Triggers a block update at world coordinates
     */
    void triggerBlockUpdate(Pos<?> pos);



    default MCCBlockState getBlockDataAt(Pos<?> pos) {
        checkAccess(pos);
        return getBlockDataAtLocal(pos.toLocalPos());
    }

    default MCCBlockType getBlockTypeAt(Pos<?> pos) {
        return getBlockTypeAtLocal(pos.toLocalPos());
    }

    default void setBlock(@NotNull MCCBlockType mccBlockType, Pos<?> pos, boolean triggerBlockUpdate) {
        checkAccess(pos);
        setBlockLocal(mccBlockType, pos.toLocalPos(), triggerBlockUpdate);
    }

    default void setBlock(@NotNull MCCBlockState mccBlockState, Pos<?> pos, boolean triggerBlockUpdate) {
        checkAccess(pos);
        setBlockLocal(mccBlockState, pos.toLocalPos(), triggerBlockUpdate);
    }


}
