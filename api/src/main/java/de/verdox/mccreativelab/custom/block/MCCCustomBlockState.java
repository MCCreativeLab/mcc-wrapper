package de.verdox.mccreativelab.custom.block;

import de.verdox.mccreativelab.custom.annotation.MCCCustomType;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

@MCCCustomType
public interface MCCCustomBlockState extends MCCBlockState {
    /**
     * Returns the custom block type associated with this block state
     *
     * @return the block type
     */
    @Override
    @NotNull MCCCustomBlockType getBlockType();

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate.
     *
     * @param rotation
     * @return
     */
    default MCCBlockState rotate(Rotation rotation) {
        return getBlockType().rotate(this, rotation);
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed blockstate.
     *
     * @param mirror
     * @return
     */
    default MCCBlockState mirror(Mirror mirror) {
        return getBlockType().mirror(this, mirror);
    }

    /**
     * Gets the hardness of this Block
     *
     * @param location the location of the block
     * @return the hardness
     */
    default float getBlockHardness(MCCLocation location) {
        return getBlockType().getBlockProperties().getBlockHardness();
    }

    /**
     * Get the hardness of this Block relative to the ability of the given player
     *
     * @param player   the player
     * @param location the location of the block
     * @return the hardness
     */
    default float getBlockHardness(MCCPlayer player, MCCLocation location) {
        return getBlockType().getBlockHardness(this, player, location);
    }

    /**
     * Is called when a block is saved in a {@link de.verdox.mccreativelab.wrapper.world.chunk.MCCChunkSection}.
     *
     * @param location      The location of the block
     * @param oldState      The old block state that was there before
     * @param movedByPiston whether the new block was moved by a piston
     */
    default void onPlace(MCCLocation location, MCCBlockState oldState, boolean movedByPiston) {
        getBlockType().onPlace(location, oldState, movedByPiston);
    }

    /**
     * Is called when a block is removed from a {@link de.verdox.mccreativelab.wrapper.world.chunk.MCCChunkSection}.
     *
     * @param location      The location of the block
     * @param newState      The new block state that replaces the current state
     * @param movedByPiston whether the old block was moved by a piston
     */
    default void onRemove(MCCLocation location, MCCBlockState newState, boolean movedByPiston) {
        getBlockType().onRemove(location, newState, movedByPiston);
    }

    /**
     * Is performed when the block state receives a scheduled tick
     *
     * @param location the location of the block
     * @param random   the random source
     */
    default void tick(MCCLocation location, RandomSource random) {
        getBlockType().tick(this, location, random);
    }

    /**
     * Is called when a projectile hits this block state
     *
     * @param location   the location of the block state
     * @param hit        how the block state was hit
     * @param projectile the projectile
     */
    default void onProjectileHit(MCCLocation location, BlockHitResult hit, MCCProjectile projectile) {
        getBlockType().onProjectileHit(this, location, hit, projectile);
    }

    /**
     * Is performed when the block state receives a random tick
     *
     * @param location the location of the block
     * @param random   the random source
     */
    default void randomTick(MCCLocation location, RandomSource random) {
        getBlockType().randomTick(this, location, random);
    }

    default MenuProvider getMenuProvider(MCCLocation location) {
        return getBlockType().getMenuProvider(this, location);
    }

    /**
     * Is called when an entity is inside the blocks states bounding box.
     *
     * @param location the location of the block
     * @param entity   the entity
     */
    default void entityInside(MCCLocation location, MCCEntity entity) {
        getBlockType().entityInside(this, location, entity);
    }

    /**
     * Performs side effects from block dropping, such as creating silverfish
     *
     * @param location       the location of the block state
     * @param stack          the tool used to break the block
     * @param dropExperience whether experience should be dropped
     */
    default void spawnAfterBreak(MCCLocation location, MCCItemStack stack, boolean dropExperience) {
        getBlockType().spawnAfterBreak(this, location, stack, dropExperience);
    }

    default void attack(MCCLocation location, MCCPlayer player) {
        getBlockType().attack(this, location, player);
    }

    /**
     * Is called by the server to check if the block state can persist at the given location.
     *
     * @param location the location
     * @return true if it can persist
     */
    default boolean canSurvive(MCCLocation location) {
        return getBlockType().canSurvive(this, location);
    }

    /**
     * Is called by the server to check if the block should receive a random tick
     *
     * @return true if the block state is randomly ticking
     */
    default boolean isRandomlyTicking() {
        return getBlockType().getBlockProperties().isRandomlyTicking();
    }

    /**
     * Is called when a player starts digging a block.
     * If the function returns true the server knows that particular items do exist that are the correct tool for the drop.
     *
     * @return true if it requires a tool for drops
     */
    default boolean requiresCorrectToolForDrops() {
        return getBlockType().getBlockProperties().requiresCorrectToolForDrops();
    }

    default List<MCCItemStack> getDrops(LootParams.Builder lootParams) {
        return getBlockType().getDrops(this, lootParams);
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
    default InteractionResult useItemOn(MCCItemStack stack, MCCWorld level, MCCPlayer player, InteractionHand hand, BlockHitResult hitResult) {
        return getBlockType().useItemOn(stack, this, level, hitResult.getBlockPos(), player, hand, hitResult);
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
    default InteractionResult useWithoutItem(MCCWorld level, MCCPlayer player, BlockHitResult hitResult) {
        return getBlockType().useWithoutItem(this, level, hitResult.getBlockPos(), player, hitResult);
    }

    /**
     * Returns the note block instrument of this block state
     *
     * @return the note block instrument
     */
    default NoteBlockInstrument instrument() {
        return getBlockType().getBlockProperties().instrument();
    }
}
