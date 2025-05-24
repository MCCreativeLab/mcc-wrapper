package de.verdox.mccreativelab.gamefactory.block;

import com.google.common.collect.Maps;
import de.verdox.mccreativelab.gamefactory.MCCGameFactory;
import de.verdox.mccreativelab.gamefactory.annotation.MCCCustomType;
import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStateProperty;
import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStatePropertyFactory;
import de.verdox.mccreativelab.wrapper.block.MCCBlockProperties;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.misc.MCCBlockHitResult;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.player.MCCInteractionHand;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.entity.types.MCCProjectileEntity;
import de.verdox.mccreativelab.wrapper.inventory.MCCMenuProvider;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.misc.MCCStateMirror;
import de.verdox.mccreativelab.wrapper.misc.MCCStateRotation;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.misc.MCCRandomSource;
import de.verdox.mccreativelab.wrapper.world.MCCInteractionResult;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.level.biome.MCCBiome;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

@MCCCustomType
public abstract class MCCCustomBlockType implements MCCBlockType {
    private final MCCBlockProperties properties;
    private final List<MCCCustomBlockState> customBlockStates;

    public MCCCustomBlockType(MCCBlockProperties properties) {
        this.properties = properties;
        StateFactory stateFactory = new StateFactory();
        this.customBlockStates = createBlockStatesFromProperties(stateFactory);
        if (customBlockStates.isEmpty()) {
            throw new IllegalArgumentException("Block must have at least one custom block state");
        }
    }

    /**
     * Called when the block is built
     * @param factory the property factory
     * @param stateFactory the state factory
     */
    protected abstract void stateDefinitions(MCCBlockStatePropertyFactory factory, StateFactory stateFactory);

    /**
     * Performs side effects from block dropping, such as creating silverfish
     *
     * @param location       the location of the block state
     * @param stack          the tool used to break the block
     * @param dropExperience whether experience should be dropped
     */
    public void spawnAfterBreak(MCCBlockState state, MCCLocation location, MCCItemStack stack, boolean dropExperience) {
    }

    /**
     * Called when the player starts digging the block
     * @param location the location
     * @param player the player
     */
    public void attack(MCCBlockState state, MCCLocation location, MCCPlayer player) {
    }

    /**
     * Is performed when the block state receives a scheduled tick
     *
     * @param location the location of the block
     * @param random   the random source
     */
    public void tick(MCCBlockState state, MCCLocation location, MCCRandomSource random) {
    }

    /**
     * Is performed when the block state receives a random tick
     *
     * @param location the location of the block
     * @param random   the random source
     */
    public void randomTick(MCCBlockState state, MCCLocation location, MCCRandomSource random) {
    }

    /**
     * Is called by the server to check if the block state can persist at the given location.
     *
     * @param location the location
     * @return true if it can persist
     */
    public boolean canSurvive(MCCBlockState state, MCCLocation location) {
        return true;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate.
     *
     * @param rotation the rotation
     * @return the rotated state
     */
    public MCCBlockState rotate(MCCBlockState state, MCCStateRotation rotation) {
        return state;
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed blockstate.
     *
     * @param mirror the mirror
     * @return the mirrored state
     */
    public MCCBlockState mirror(MCCCustomBlockState state, MCCStateMirror mirror) {
        return state;
    }

    /**
     * Called when precipitation hits the block at a specific location
     * @param state the state
     * @param location the location
     * @param precipitation the precipitation
     */
    public void handlePrecipitation(MCCBlockState state, MCCLocation location, MCCBiome.Precipitation precipitation) {
    }

    /**
     * Called when an entity steps on the block
     * @param state the state
     * @param location the location
     * @param entity the entity
     */
    public void stepOn(MCCBlockState state, MCCLocation location, MCCEntity entity) {
    }

    /**
     * Is called when a projectile hits this block state
     *
     * @param location   the location of the block state
     * @param hit        how the block state was hit
     * @param projectile the projectile
     */
    public void onProjectileHit(MCCBlockState state, MCCLocation location, MCCBlockHitResult hit, MCCProjectileEntity projectile) {
    }

    /**
     * Is called when a block is saved in a {@link de.verdox.mccreativelab.wrapper.world.chunk.MCCChunkSection}.
     *
     * @param location      The location of the block
     * @param oldState      The old block state that was there before
     * @param movedByPiston whether the new block was moved by a piston
     */
    public void onPlace(MCCLocation location, MCCBlockState oldState, boolean movedByPiston) {

    }

    /**
     * Is called when a block is removed from a {@link de.verdox.mccreativelab.wrapper.world.chunk.MCCChunkSection}.
     *
     * @param location      The location of the block
     * @param newState      The new block state that replaces the current state
     * @param movedByPiston whether the old block was moved by a piston
     */
    public void onRemove(MCCLocation location, MCCBlockState newState, boolean movedByPiston) {

    }

    /**
     * Is called when an entity is inside the blocks states bounding box.
     *
     * @param location the location of the block
     * @param entity   the entity
     */
    public void entityInside(MCCCustomBlockState mccCustomBlockState, MCCLocation location, MCCEntity entity) {

    }

    /**
     * Returns a menu provider of the state at the specified location
     * @param location the location
     * @return the menu provider
     */
    @Nullable
    public MCCMenuProvider<?> getMenuProvider(MCCBlockState state, MCCLocation location) {
        return null;
    }

    /**
     * Is called when a player uses an item on a block
     * @param stack the item
     * @param state the state
     * @param location the location of the state
     * @param player the player
     * @param hand the interaction hand
     * @param hitResult the block hit
     * @return the result of the interaction
     */
    public MCCInteractionResult useItemOn(MCCItemStack stack, MCCBlockState state, MCCLocation location, MCCPlayer player, MCCInteractionHand hand, MCCBlockHitResult hitResult) {
        return MCCInteractionResult.PASS;
    }

    /**
     * Gets the block hardness at the specified location
     * @param location the location
     * @return the hardness
     */
    public float getBlockHardness(MCCLocation location) {
        return getBlockProperties().getBlockHardness();
    }

    /**
     * Gets the block hardness from a players perspective
     * @param player the player
     * @return the hardness
     */
    public float getBlockHardness(MCCPlayer player) {
        return MCCPlatform.getInstance().getBlockHardnessSettings().getBlockHardness(player, getDefaultState());
    }

    /**
     * Gets the block hardness
     * @param state the state
     * @param player the player
     * @param location the location
     * @return the hardness
     */
    public final float getBlockHardness(MCCCustomBlockState state, MCCPlayer player, MCCLocation location) {
        float f = state.getBlockHardness(location);
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = player.hasCorrectToolForDrops(state) ? 30 : 100;
            return MCCPlatform.getInstance().getBlockHardnessSettings().getBlockHardness(player, state) / f / (float) i;
        }
    }

    /**
     * Is called when a player interacts with a block without an item. This does not mean that the player does not hold any item in hand.
     * It only means that the item is not used for the interaction
     * @param state the state
     * @param location the location
     * @param player the player
     * @param hitResult the block hit
     * @return the result
     */
    public MCCInteractionResult useWithoutItem(MCCBlockState state, MCCLocation location, MCCPlayer player, MCCBlockHitResult hitResult) {
        return MCCInteractionResult.PASS;
    }

    // Here we prevent some changes to methods that need to be implemented like this

    @Override
    public final List<MCCBlockState> getAllBlockStates() {
        return new LinkedList<>(customBlockStates);
    }

    @Override
    public final MCCBlockState getDefaultState() {
        return customBlockStates.getFirst();
    }

    @Override
    public final Key getRegistryKey() {
        return MCCGameFactory.BLOCK_REGISTRY.key();
    }

    @Override
    @NotNull
    public final Key key() {
        return MCCGameFactory.BLOCK_REGISTRY.get().getKey(this);
    }

    @Override
    public MCCBlockProperties getBlockProperties() {
        return properties;
    }

    @Override
    public final int getIndexOfState(MCCBlockState mccBlockState) {
        return MCCBlockType.super.getIndexOfState(mccBlockState);
    }

    @Override
    public final boolean isVanilla() {
        return false;
    }

    private List<MCCCustomBlockState> createBlockStatesFromProperties(StateFactory stateFactory) {
        stateDefinitions(MCCBlockStateProperty.factory(), stateFactory);

        List<MCCCustomBlockState> states = new LinkedList<>();
        Map<Map<MCCBlockStateProperty<?>, Comparable<?>>, MCCCustomBlockState> map = Maps.newLinkedHashMap();

        // Create a block state for every combination
        createPossibleCombinations(stateFactory.propertyOrder).forEach(combination -> {
            Reference2ObjectArrayMap<MCCBlockStateProperty<?>, Comparable<?>> propMap = new Reference2ObjectArrayMap<>();
            for (Pair<MCCBlockStateProperty<?>, Comparable<?>> pair : combination) {
                propMap.put(pair.first(), pair.second());
            }

            MCCCustomBlockState state = new MCCCustomBlockState(this, propMap);
            map.put(propMap, state);
            states.add(state);
        });

        for (MCCCustomBlockState state : states) {
            state.neighbourStates.populateNeighbours(map);
        }

        return states;
    }

    private @NotNull Stream<List<Pair<MCCBlockStateProperty<?>, Comparable<?>>>> createPossibleCombinations(List<MCCBlockStateProperty<?>> propertiesList) {
        Stream<List<Pair<MCCBlockStateProperty<?>, Comparable<?>>>> stream = Stream.of(Collections.emptyList());

        // Create all possible combinations
        for (MCCBlockStateProperty<?> property : propertiesList) {
            stream = stream.flatMap(list -> property.getPossibleValues().map(value -> {
                List<Pair<MCCBlockStateProperty<?>, Comparable<?>>> newList = new ArrayList<>(list);
                newList.add(Pair.of(property, value));
                return newList;
            }));
        }
        return stream;
    }

    public static class StateFactory {
        private final List<MCCBlockStateProperty<?>> propertyOrder = new LinkedList<>();

        public void addProperty(MCCBlockStateProperty<?> property) {
            propertyOrder.add(property);
        }
    }
}
