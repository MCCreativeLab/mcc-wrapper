package de.verdox.mccreativelab.wrapper.block;

import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStateProperty;
import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.annotations.MCCBuiltIn;
import de.verdox.mccreativelab.wrapper.annotations.MCCInstantiationSource;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Describes a variant of a block
 */
@MCCInstantiationSource(sourceClasses = MCCChunk.class)
@MCCBuiltIn(syncState = MCCBuiltIn.SyncState.SYNCED, clientEntersErrorStateOnDesync = true)
public interface MCCBlockState extends MCCKeyedWrapper {
    /**
     * Returns the parent block type of this block state
     *
     * @return the block type
     */
    @NotNull MCCBlockType getBlockType();

    /**
     * Changes a block at the provided location to this block state
     *
     * @param location the location to change the block at
     */
    default void setBlock(@NotNull MCCLocation location) {
        setBlock(location, true);
    }

    /**
     * Changes a block at the provided location to this block state
     *
     * @param location     the location to change the block at
     * @param applyPhysics whether the change should trigger block updates
     */
    default CompletableFuture<Void> setBlock(@NotNull MCCLocation location, boolean applyPhysics) {
        return location.world().at(location, mccBlock -> {
            mccBlock.setBlockState(this, applyPhysics);
        });
    }

    /**
     * The block state string describing the state and its variables
     *
     * @return the state as string
     */
    String toBlockDataString();

    default boolean isPreferredTool(MCCItemStack nmsItem) {
        return !requiresCorrectToolForDrops() || nmsItem.isCorrectToolForDrops(this);
    }

    /**
     * This method is only used by {@link de.verdox.mccreativelab.wrapper.block.settings.MCCBlockHardnessSettings} to determine the block break speed of a player.
     * @param player the player
     * @return the block hardness
     */
    float getBlockHardness(MCCPlayer player);

    /**
     * Returns all drops of this block as if it was broken by an entity.
     *
     * @param entity the entity breaking the block
     * @param tool   the tool used to break the block
     * @return the drops of this block as items
     */
    @NotNull List<MCCItemStack> getDrops(@NotNull MCCLocation mccLocation, @Nullable MCCEntity entity, @Nullable MCCItemStack tool);


    /**
     * Returns if this block type is randomly ticking
     *
     * @return true if the block type is randomly ticking
     */
    boolean isRandomlyTicking();

    /**
     * Returns if this block type requires correct tools for drops
     *
     * @return true if the block type requires correct tools for drops
     */
    boolean requiresCorrectToolForDrops();

    @Override
    default Key getRegistryKey() {
        return getBlockType().getRegistryKey();
    }

    @Override
    @NotNull
    default Key key() {
        return getBlockType().key();
    }

    <T extends Comparable<T>> boolean hasProperty(MCCBlockStateProperty<T> property);

    <T extends Comparable<T>> T getValue(MCCBlockStateProperty<T> property);

    <T extends Comparable<T>> MCCBlockState newState(MCCBlockStateProperty<T> property, T value);
}
