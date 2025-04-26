package de.verdox.mccreativelab.wrapper.world;

import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.entity.types.MCCItemEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.TempDataHolder;
import de.verdox.mccreativelab.wrapper.util.math.AxisAlignedBoundingBox;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import net.kyori.adventure.audience.ForwardingAudience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface MCCWorld extends MCCKeyedWrapper, TempDataHolder, ForwardingAudience {

    default CompletableFuture<MCCBlockState> getBlockDataAt(int x, int y, int z) {
        return getOrLoadChunk(MCCLocation.calculateChunkX(x), MCCLocation.calculateChunkZ(z)).thenApply(mccChunk -> {
            return mccChunk.getBlockDataAt(x, y, z);
        });
    }

    default CompletableFuture<MCCBlockType> getBlockTypeAt(int x, int y, int z) {
        return getOrLoadChunk(MCCLocation.calculateChunkX(x), MCCLocation.calculateChunkZ(z)).thenApply(mccChunk -> {
            return mccChunk.getBlockTypeAt(x, y, z);
        });
    }

    default CompletableFuture<MCCBlock> getBlockAt(int x, int y, int z) {
        MCCLocation blockLocation = new MCCLocation(this, x, y, z);
        return getOrLoadChunk(blockLocation).thenApply(mccChunk -> new MCCBlock(blockLocation, mccChunk));
    }

    default CompletableFuture<MCCBlockState> getBlockDataAt(MCCLocation location) {
        if (!location.world().equals(this)) {
            throw new IllegalArgumentException("The provided location does not belong to this world.");
        }
        return getBlockDataAt((int) location.x(), (int) location.y(), (int) location.z());
    }

    String getName();

    default CompletableFuture<MCCBlockType> getBlockTypeAt(MCCLocation location) {
        if (!location.world().equals(this)) {
            throw new IllegalArgumentException("The provided location does not belong to this world.");
        }
        return getBlockTypeAt(location.blockX(), location.blockY(), location.blockZ());
    }

    default CompletableFuture<MCCBlock> getBlockAt(MCCLocation location) {
        if (!location.world().equals(this)) {
            throw new IllegalArgumentException("The provided location does not belong to this world.");
        }
        return getBlockAt(location.blockX(), location.blockY(), location.blockZ());
    }

    /**
     * Changes a block at the provided location to this block state
     *
     * @param mccBlockState      the block state
     * @param location           the location to change the block at
     * @param triggerBlockUpdate whether the change should trigger block updates
     */
    default CompletableFuture<Void> setBlock(@NotNull MCCBlockState mccBlockState, @NotNull MCCLocation location, boolean triggerBlockUpdate) {
        return getOrLoadChunk(location).thenApply(mccChunk -> {
            mccChunk.setBlock(mccBlockState, location);
            if (triggerBlockUpdate) {
                triggerBlockUpdate(location);
            }
            return null;
        });
    }

    /**
     * Changes a block at the provided location to this block state
     *
     * @param mccBlockType       the block type
     * @param location           the location to change the block at
     * @param triggerBlockUpdate whether the change should trigger block updates
     */
    default CompletableFuture<Void> setBlock(@NotNull MCCBlockType mccBlockType, @NotNull MCCLocation location, boolean triggerBlockUpdate) {
        return getOrLoadChunk(location).thenApply(mccChunk -> {
            mccChunk.setBlock(mccBlockType, location);
            if (triggerBlockUpdate) {
                triggerBlockUpdate(location);
            }
            return null;
        });
    }

    /**
     * Naturally breaks this block as if a player had broken it.
     *
     * @param tool           the tool used
     * @param triggerEffect  whether to trigger a block break effect
     * @param dropExperience whether to drop Experience
     * @param ignoreTool     whether to ignore the tool
     */
    void breakBlockNaturally(MCCBlock mccBlock, @Nullable MCCItemStack tool, boolean triggerEffect, boolean dropLoot, boolean dropExperience, boolean ignoreTool);

    /**
     * Naturally breaks this block as if a player had broken it.
     *
     * @param triggerEffect  whether to trigger a block break effect
     * @param dropExperience whether to drop Experience
     */
    default void breakBlockNaturally(MCCBlock mccBlock, boolean triggerEffect, boolean dropLoot, boolean dropExperience) {
        breakBlockNaturally(mccBlock, null, triggerEffect, dropLoot, dropExperience, true);
    }

    /**
     * Drops an item at the specified {@link MCCLocation} with a random offset
     * Note that functions will run before the entity is spawned
     *
     * @param location     Location to drop the item
     * @param item         ItemStack to drop
     * @param dropCallback the function to be run before the entity is spawned.
     * @return ItemDrop entity created as a result of this method
     */
    MCCItemEntity dropItemNaturally(MCCLocation location, MCCItemStack item, @Nullable Consumer<MCCItemEntity> dropCallback);

    /**
     * Drops an item at the specified {@link MCCLocation} with a random offset
     * Note that functions will run before the entity is spawned
     *
     * @param location     Location to drop the item
     * @param item         ItemStack to drop
     * @param dropCallback the function to be run before the entity is spawned.
     * @return ItemDrop entity created as a result of this method
     */
    MCCItemEntity dropItem(MCCLocation location, MCCItemStack item, @Nullable Consumer<MCCItemEntity> dropCallback);


    /**
     * Drops an item at the specified {@link MCCLocation} with a random offset
     * Note that functions will run before the entity is spawned
     *
     * @param location Location to drop the item
     * @param item     ItemStack to drop
     * @return ItemDrop entity created as a result of this method
     */
    default MCCItemEntity dropItemNaturally(MCCLocation location, MCCItemStack item) {
        return dropItemNaturally(location, item, null);
    }

    /**
     * Returns all players in this world
     *
     * @return the players
     */
    List<MCCPlayer> getPlayers();

    /**
     * Used to summon an entity at a specified location
     * @param location the location
     * @param mccEntityType the entity type
     * @return a future
     */
    CompletableFuture<MCCEntity> summon(@NotNull MCCLocation location, @NotNull MCCEntityType mccEntityType);

    /**
     * Gets or loads a chunk
     * @param chunkX the chunk x coordinate
     * @param chunkZ the chunk y coordinate
     * @return a future
     */
    CompletableFuture<MCCChunk> getOrLoadChunk(int chunkX, int chunkZ);

    /**
     * Gets or loads a chunk
     * @param location a location that is inside the chunk
     * @return a future
     */
    CompletableFuture<MCCChunk> getOrLoadChunk(MCCLocation location);

    /**
     * Returns a chunk at specified coordinates if it is loaded
     * @param chunkX the chunk x coordinate
     * @param chunkZ the chunk y coordinate
     * @return the chunk or null if it is not loaded
     */
    @Nullable MCCChunk getChunkImmediately(int chunkX, int chunkZ);

    /**
     * Returns a chunk at specified coordinates if it is loaded
     * @param location a location that is inside the chunk
     * @return the chunk or null if it is not loaded
     */
    @Nullable MCCChunk getChunkImmediately(MCCLocation location);

    /**
     * Returns the uuid of the world
     * @return the uuid
     */
    UUID getUUID();

    /**
     * Triggers a block update at a location
     * @param location the location
     */
    void triggerBlockUpdate(MCCLocation location);

    /**
     * Returns the max build height of the world
     *
     * @return the max build height of the world
     */
    int getMaxBuildHeight();

    /**
     * Returns the min build height of the world
     *
     * @return the min build height of the world
     */
    int getMinBuildHeight();

    /**
     * Returns an iterable over all players as audiences
     * @return the iterable
     */
    @Override
    default Iterable<? extends net.kyori.adventure.audience.Audience> audiences() {
        return this.getPlayers();
    }

    /**
     * Gets the entity with the specified uuid or null if no entity has the uuid
     * @param entityUUID the uuid
     * @return the entity or null
     */
    @Nullable
    MCCEntity get(UUID entityUUID);

    /**
     * Gets the entity with the specified id or null if no entity has the uuid
     * @param entityId the id
     * @return the entity or null
     */
    MCCEntity get(int entityId);

    /**
     * Gets all entities within the specified AABB excluding the one passed into it.
     * @param entityToExclude the entity to exclude
     * @param boundingBox the bounding box
     * @param filter a filter
     * @return all found entities
     */
    List<MCCEntity> getEntities(@Nullable MCCEntity entityToExclude, AxisAlignedBoundingBox boundingBox, Predicate<MCCEntity> filter);

    /**
     * Gets the nearest player at a specified location
     * @param x the x location
     * @param y the y location
     * @param z the z location
     * @param distance the distance
     * @param filter the filter
     * @return the found player or null
     */
    @Nullable
    MCCPlayer getNearestPlayer(double x, double y, double z, double distance, Predicate<MCCPlayer> filter);

    /**
     * Checks if any alive player is nearby
     * @param x the x location
     * @param y the y location
     * @param z the z location
     * @param distance the distance
     * @return true or false
     */
    boolean hasNearbyAlivePlayer(double x, double y, double z, double distance);

    /**
     * Gets a player by their uuid
     * @param playerUUID the uuid
     * @return the player or null if no player was found
     */
    @Nullable
    MCCPlayer getPlayer(UUID playerUUID);
}
