package de.verdox.mccreativelab.wrapper.block;

import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.acessor.point.PointAccessor;
import de.verdox.mccreativelab.wrapper.world.acessor.point.PointBlockAccessor;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCBlockPos;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCPos;
import de.verdox.mccreativelab.wrapper.world.coordinates.Pos;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Describes an actual block in a minecraft world with a location and a type.
 *
 */
public final class MCCBlock implements MCCKeyedWrapper, PointBlockAccessor<MCCBlock, MCCWorld, MCCChunk> {
    private final MCCWorld world;
    private final MCBlockPos blockPos;
    private final MCCChunk mccChunk;

    public MCCBlock(@NotNull MCCWorld world, @NotNull Pos<?> pos, @NotNull MCCChunk mccChunk) {
        Objects.requireNonNull(world, "The world cannot be null");
        Objects.requireNonNull(pos, "The position cannot be null");
        Objects.requireNonNull(mccChunk, "The chunk cannot be null");
        if (pos instanceof MCCLocation) {
            throw new IllegalArgumentException("Please don't use location and world in the same constructor");
        }
        if (!mccChunk.isLoaded()) {
            throw new IllegalArgumentException("The provided chunk of the block is not loaded");
        }
        if (!mccChunk.getWorld().canAccess(pos)) {
            throw new IllegalArgumentException("The provided chunk " + mccChunk.getChunkPos().x() + ", " + mccChunk.getChunkPos().z() + " is not the owner of the provided position " + pos + ". Reason: World mismatch -> " + mccChunk.getWorld().key() + " != " + world.key());
        } else if (!mccChunk.canAccess(pos)) {
            throw new IllegalArgumentException("The provided chunk " + mccChunk.getChunkPos().x() + ", " + mccChunk.getChunkPos().z() + " is not the owner of the provided position " + pos + ". Reason: Chunk coordinate mismatch -> (" + mccChunk.getChunkPos().x() + "," + mccChunk.getChunkPos().z() + ") != (" + pos.toChunkPos().x() + "," + pos.toChunkPos().z() + ")");
        }

        this.world = world;
        this.blockPos = pos.toBlockPos();
        this.mccChunk = mccChunk;
    }

    public MCCBlock(MCCLocation location, @NotNull MCCChunk mccChunk) {
        this(Objects.requireNonNull(location).world(), location.toPos(), mccChunk);
    }

    /**
     * Gets the relative of a block
     */
    public CompletableFuture<MCCBlock> getRelative(int relX, int relY, int relZ) {
        return getWorld().at(new MCBlockPos(blockPos.x() + relX, blockPos.y() + relY, blockPos.z() + relZ), mccBlock -> mccBlock);
    }


    /**
     * Returns the location of this block
     *
     * @return the location of the block
     */

    @Override
    @NotNull
    public MCCLocation getLocation() {
        return new MCCLocation(world, blockPos.toPos());
    }

    MCCCapturedBlockState captureBlock() {
        return new MCCCapturedBlockState(this);
    }

    /**
     * Returns all drops of this block as if it was broken by an entity.
     *
     * @param tool   the tool used to break the block
     * @param entity the entity breaking the block
     * @return the drops of this block as items
     */
    @NotNull
    public Collection<MCCItemStack> getDrops(@Nullable MCCEntity entity, @Nullable MCCItemStack tool) {
        return getBlockState().getDrops(getLocation(), entity, tool);
    }

    public void dropBlockLoot(@Nullable MCCEntity entity, @Nullable MCCItemStack tool) {
        for (MCCItemStack drop : getBlockState().getDrops(getLocation(), entity, tool)) {
            getChunk().dropItemNaturally(getLocation(), drop);
        }
    }

    /**
     * Naturally breaks this block as if a player had broken it.
     *
     * @param tool           the tool used
     * @param triggerEffect  whether to trigger a block break effect
     * @param dropExperience whether to drop Experience
     * @param ignoreTool     whether to ignore the tool
     */
    public void breakBlockNaturally(@Nullable MCCItemStack tool, boolean triggerEffect, boolean dropLoot, boolean dropExperience, boolean ignoreTool) {
        getChunk().breakBlockNaturally(blockPos, tool, triggerEffect, dropLoot, dropExperience, ignoreTool);
    }

    /**
     * Naturally breaks this block as if a player had broken it.
     *
     * @param triggerEffect  whether to trigger a block break effect
     * @param dropExperience whether to drop Experience
     */
    public void breakBlockNaturally(boolean triggerEffect, boolean dropLoot, boolean dropExperience) {
        breakBlockNaturally(null, triggerEffect, dropLoot, dropExperience, true);
    }

    @Override
    public @NotNull Key key() {
        return getBlockState().key();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCCBlock mccBlock = (MCCBlock) o;
        return Objects.equals(world, mccBlock.world) && Objects.equals(blockPos, mccBlock.blockPos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, blockPos);
    }

    @Override
    public Key getRegistryKey() {
        return getBlockType().getRegistryKey();
    }

    @Override
    public MCPos getPos() {
        return blockPos.toPos();
    }

    @Override
    public MCCChunk getChunk() {
        return mccChunk;
    }

    @Override
    public MCCWorld getWorld() {
        return mccChunk.getWorld();
    }

    @Override
    public boolean canAccess(Pos<?> pos) {
        return pos.toPos().equals(getPos());
    }
}
