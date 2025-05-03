package de.verdox.mccreativelab.custom.block;

import de.verdox.mccreativelab.custom.annotation.MCCCustomType;
import de.verdox.mccreativelab.custom.block.properties.MCCBlockStateProperty;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.player.MCCInteractionHand;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.entity.types.MCCProjectileEntity;
import de.verdox.mccreativelab.wrapper.inventory.MCCMenuProvider;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.misc.MCCBlockHitResult;
import de.verdox.mccreativelab.wrapper.misc.MCCRandomSource;
import de.verdox.mccreativelab.wrapper.misc.MCCStateMirror;
import de.verdox.mccreativelab.wrapper.misc.MCCStateRotation;
import de.verdox.mccreativelab.wrapper.world.MCCInteractionResult;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.level.biome.MCCBiome;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@MCCCustomType
public final class MCCCustomBlockState implements MCCBlockState {
    private final MCCCustomBlockType parent;
    private final Reference2ObjectArrayMap<MCCBlockStateProperty<?>, Comparable<?>> properties;
    final NeighbourStates neighbourStates = new NeighbourStates();

    MCCCustomBlockState(MCCCustomBlockType parent, Reference2ObjectArrayMap<MCCBlockStateProperty<?>, Comparable<?>> properties) {
        this.parent = parent;
        this.properties = properties;
    }

    /**
     * Returns the custom block type associated with this block state
     *
     * @return the block type
     */
    @Override
    @NotNull
    public MCCCustomBlockType getBlockType() {
        return parent;
    }

    @Override
    public String toBlockDataString() {
        return "";
    }

    @Override
    public float getBlockHardness(MCCPlayer player) {
        return getBlockType().getBlockHardness(player);
    }

    @Override
    public @NotNull List<MCCItemStack> getDrops(@NotNull MCCLocation mccLocation, @Nullable MCCEntity entity, @Nullable MCCItemStack tool) {
        return getBlockType().getDrops(this, mccLocation, entity, tool);
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate.
     *
     * @param rotation the rotation
     * @return the rotated state
     */
    public MCCBlockState rotate(MCCStateRotation rotation) {
        return getBlockType().rotate(this, rotation);
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed blockstate.
     *
     * @param mirror the mirror
     * @return the mirrored state
     */
    public MCCBlockState mirror(MCCStateMirror mirror) {
        return getBlockType().mirror(this, mirror);
    }

    /**
     * Gets the hardness of this Block
     *
     * @param location the location of the block
     * @return the hardness
     */
    public float getBlockHardness(MCCLocation location) {
        return getBlockType().getBlockHardness(location);
    }

    /**
     * Get the hardness of this Block relative to the ability of the given player
     *
     * @param player   the player
     * @param location the location of the block
     * @return the hardness
     */
    public float getBlockHardness(MCCPlayer player, MCCLocation location) {
        return getBlockType().getBlockHardness(this, player, location);
    }

    /**
     * Is called when a block is saved in a {@link de.verdox.mccreativelab.wrapper.world.chunk.MCCChunkSection}.
     *
     * @param location      The location of the block
     * @param oldState      The old block state that was there before
     * @param movedByPiston whether the new block was moved by a piston
     */
    public void onPlace(MCCLocation location, MCCBlockState oldState, boolean movedByPiston) {
        getBlockType().onPlace(location, oldState, movedByPiston);
    }

    /**
     * Is called when a block is removed from a {@link de.verdox.mccreativelab.wrapper.world.chunk.MCCChunkSection}.
     *
     * @param location      The location of the block
     * @param newState      The new block state that replaces the current state
     * @param movedByPiston whether the old block was moved by a piston
     */
    public void onRemove(MCCLocation location, MCCBlockState newState, boolean movedByPiston) {
        getBlockType().onRemove(location, newState, movedByPiston);
    }

    /**
     * Is performed when the block state receives a scheduled tick
     *
     * @param location the location of the block
     * @param random   the random source
     */
    public void tick(MCCLocation location, MCCRandomSource random) {
        getBlockType().tick(this, location, random);
    }

    /**
     * Is called when a projectile hits this block state
     *
     * @param location   the location of the block state
     * @param hit        how the block state was hit
     * @param projectile the projectile
     */
    public void onProjectileHit(MCCLocation location, MCCBlockHitResult hit, MCCProjectileEntity projectile) {
        getBlockType().onProjectileHit(this, location, hit, projectile);
    }

    /**
     * Is performed when the block state receives a random tick
     *
     * @param location the location of the block
     * @param random   the random source
     */
    public void randomTick(MCCLocation location, MCCRandomSource random) {
        getBlockType().randomTick(this, location, random);
    }

    /**
     * Returns a menu provider of the state at the specified location
     * @param location the location
     * @return the menu provider
     */
    @Nullable
    public MCCMenuProvider<?> getMenuProvider(MCCLocation location) {
        return getBlockType().getMenuProvider(this, location);
    }

    /**
     * Is called when an entity is inside the blocks states bounding box.
     *
     * @param location the location of the block
     * @param entity   the entity
     */
    public void entityInside(MCCLocation location, MCCEntity entity) {
        getBlockType().entityInside(this, location, entity);
    }

    /**
     * Performs side effects from block dropping, such as creating silverfish
     *
     * @param location       the location of the block state
     * @param stack          the tool used to break the block
     * @param dropExperience whether experience should be dropped
     */
    public void spawnAfterBreak(MCCLocation location, MCCItemStack stack, boolean dropExperience) {
        getBlockType().spawnAfterBreak(this, location, stack, dropExperience);
    }

    /**
     * Called when the player starts digging the block
     * @param location the location
     * @param player the player
     */
    public void attack(MCCLocation location, MCCPlayer player) {
        getBlockType().attack(this, location, player);
    }

    /**
     * Is called by the server to check if the block state can persist at the given location.
     *
     * @param location the location
     * @return true if it can persist
     */
    public boolean canSurvive(MCCLocation location) {
        return getBlockType().canSurvive(this, location);
    }

    /**
     * Is called by the server to check if the block should receive a random tick
     *
     * @return true if the block state is randomly ticking
     */
    public boolean isRandomlyTicking() {
        return getBlockType().getBlockProperties().isRandomlyTicking();
    }

    /**
     * Is called when a player starts digging a block.
     * If the function returns true the server knows that particular items do exist that are the correct tool for the drop.
     *
     * @return true if it requires a tool for drops
     */
    public boolean requiresCorrectToolForDrops() {
        return getBlockType().getBlockProperties().requiresCorrectToolForDrops();
    }

    /**
     * Is called when a player uses an item on a block
     *
     * @param stack     the item used
     * @param level     the world
     * @param player    the player
     * @param hand      the interaction hand
     * @param hitResult how the block was interacted with
     * @return the interaction result
     */
    public MCCInteractionResult useItemOn(MCCItemStack stack, MCCWorld level, MCCPlayer player, MCCInteractionHand hand, MCCBlockHitResult hitResult) {
        return getBlockType().useItemOn(stack, this, new MCCLocation(level, hitResult.getBlockPos().toPos()), player, hand, hitResult);
    }

    /**
     * Is called when a player interacts with a block without an item. This does not mean that the player does not hold any item in hand.
     * It only means that the item is not used for the interaction
     *
     * @param level     the world
     * @param player    the player
     * @param hitResult how the block was interacted with
     * @return the interaction result
     */
    public MCCInteractionResult useWithoutItem(MCCWorld level, MCCPlayer player, MCCBlockHitResult hitResult) {
        return getBlockType().useWithoutItem(this, new MCCLocation(level, hitResult.getBlockPos().toPos()), player, hitResult);
    }

    public void handlePrecipitation(MCCLocation mccLocation, MCCBiome.Precipitation precipitation) {
        getBlockType().handlePrecipitation(this, mccLocation, precipitation);
    }

    public void stepOn(MCCLocation mccLocation, MCCEntity entity) {
        getBlockType().stepOn(this, mccLocation, entity);
    }

    @Override
    public <T extends Comparable<T>> boolean hasProperty(MCCBlockStateProperty<T> property) {
        return this.properties.containsKey(property);
    }

    @Override
    public final boolean isVanilla() {
        return false;
    }

    @Override
    public <T extends Comparable<T>> T getValue(MCCBlockStateProperty<T> property) {
        Comparable<?> comparable = this.properties.get(property);
        if (comparable == null) {
            throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this);
        } else {
            return (T) comparable;
        }
    }

    @Override
    public <T extends Comparable<T>> MCCBlockState newState(MCCBlockStateProperty<T> property, T value) {
        Comparable<?> comparable = this.properties.get(property);
        if (comparable == null) {
            throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this);
        } else {
            if (comparable.equals(value)) {
                return this;
            } else {
                int i = property.getInternalIndex((T) value);
                if (i < 0) {
                    throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on " + this + ", it is not an allowed value");
                } else {
                    return this.neighbourStates.neighbours.get(property)[i];
                }
            }
        }
    }

    class NeighbourStates {
        private Map<MCCBlockStateProperty<?>, MCCCustomBlockState[]> neighbours;

        public void populateNeighbours(Map<Map<MCCBlockStateProperty<?>, Comparable<?>>, MCCCustomBlockState> possibleStateMap) {
            if (this.neighbours != null) {
                throw new IllegalStateException();
            } else {
                Map<MCCBlockStateProperty<?>, MCCCustomBlockState[]> map = new Reference2ObjectArrayMap<>(properties.size());

                for (Map.Entry<MCCBlockStateProperty<?>, Comparable<?>> entry : properties.entrySet()) {
                    MCCBlockStateProperty<?> property = entry.getKey();
                    map.put(property, (MCCCustomBlockState[]) property.getPossibleValues().map(comparable -> possibleStateMap.get(this.makeNeighbourValues(property, comparable))).toArray());
                }

                this.neighbours = map;
            }
        }

        private Map<MCCBlockStateProperty<?>, Comparable<?>> makeNeighbourValues(MCCBlockStateProperty<?> property, Comparable<?> value) {
            Map<MCCBlockStateProperty<?>, Comparable<?>> map = new Reference2ObjectArrayMap<>(properties);
            map.put(property, value);
            return map;
        }
    }
}
