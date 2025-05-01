package de.verdox.mccreativelab.wrapper.world;

import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.platform.TempDataHolder;
import de.verdox.mccreativelab.wrapper.util.MCCTicking;
import de.verdox.mccreativelab.wrapper.util.math.AxisAlignedBoundingBox;
import de.verdox.mccreativelab.wrapper.world.acessor.global.WorldBlockAccessor;
import de.verdox.mccreativelab.wrapper.world.acessor.global.WorldEntityAccessor;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import net.kyori.adventure.audience.ForwardingAudience;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface MCCWorld extends MCCKeyedWrapper, TempDataHolder, ForwardingAudience, MCCTicking, WorldBlockAccessor<MCCWorld, MCCChunk, MCCBlock>, WorldEntityAccessor<MCCWorld, MCCChunk> {
    String getName();
    /**
     * Returns all players in this world
     *
     * @return the players
     */
    List<MCCPlayer> getPlayers();

    /**
     * Returns the uuid of the world
     * @return the uuid
     */
    UUID getUUID();

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

    /**
     * Gets all entities that are currently loaded in this world
     * @return all entities
     */
    List<MCCEntity> getEntities();
}
