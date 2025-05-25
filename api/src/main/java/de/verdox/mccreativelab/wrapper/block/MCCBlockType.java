package de.verdox.mccreativelab.wrapper.block;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.annotations.MCCBuiltIn;
import de.verdox.mccreativelab.wrapper.annotations.MCCInstantiationSource;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.serialization.MCCSerializers;
import de.verdox.mccreativelab.wrapper.typed.MCCRegistries;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import de.verdox.vserializer.generic.Serializer;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Describes a block type and its block states
 */
@MCCInstantiationSource(sourceClasses = MCCChunk.class)
@MCCBuiltIn(syncState = MCCBuiltIn.SyncState.SYNCED, clientEntersErrorStateOnDesync = true)
public interface MCCBlockType extends MCCKeyedWrapper {
    Serializer<MCCBlockType> SERIALIZER = MCCSerializers.KEYED_WRAPPER("block", new TypeToken<>() {});

    /**
     * Changes a block at the provided location to this block type
     *
     * @param location the location to change the block at
     */
    default CompletableFuture<Void> setBlock(@NotNull MCCLocation location) {
        return setBlock(location, false);
    }

    /**
     * Changes a block at the provided location to this block type
     *
     * @param location     the location to change the block at
     * @param applyPhysics whether a block update is triggered
     */
    default CompletableFuture<Void> setBlock(@NotNull MCCLocation location, boolean applyPhysics) {
        return location.world().at(location, mccBlock -> {
            mccBlock.setBlockType(this, applyPhysics);
        });
    }

    /**
     * Returns all defined block states for this block type
     *
     * @return a list of block states
     */
    List<MCCBlockState> getAllBlockStates();

    /**
     * Returns the default block state of this block
     *
     * @return the default block state
     */
    MCCBlockState getDefaultState();

    default int getIndexOfState(MCCBlockState mccBlockState) {
        return getAllBlockStates().indexOf(mccBlockState);
    }

    /**
     * Gets the drops of a block state at a location.
     *
     * @param blockState             the block state
     * @param mccLocation            the location
     * @param entityBreakingTheBlock the entity breaking the block
     * @param tool                   the tool used to break the block
     * @return the drops
     */
    default List<MCCItemStack> getDrops(MCCBlockState blockState, @NotNull MCCLocation mccLocation, @Nullable MCCEntity entityBreakingTheBlock, @Nullable MCCItemStack tool) {
        return blockState.getDrops(mccLocation, entityBreakingTheBlock, tool);
    }

    //TODO: 
    // - Piston Push Reaction
    // - Light Level

    @Override
    default Key getRegistryKey() {
        return MCCRegistries.BLOCK_REGISTRY.key();
    }

    /**
     * Returns the properties of this block
     *
     * @return the block properties
     */
    MCCBlockProperties getBlockProperties();
}
