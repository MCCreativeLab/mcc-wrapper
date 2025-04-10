package de.verdox.mccreativelab.custom.block;

import de.verdox.mccreativelab.custom.MCCGameFactory;
import de.verdox.mccreativelab.custom.annotation.MCCCustomType;
import de.verdox.mccreativelab.wrapper.block.MCCBlockProperties;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.player.MCCInteractionHand;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.world.MCCInteractionResult;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.level.biome.MCCBiome;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@MCCCustomType
public abstract class MCCCustomBlockType implements MCCBlockType {
    private final MCCBlockProperties properties;
    private final List<MCCBlockState> customBlockStates;

    public MCCCustomBlockType(MCCBlockProperties properties, List<MCCCustomBlockState> customBlockStates) {
        if (customBlockStates.isEmpty()) {
            throw new IllegalArgumentException("Block must have at least one custom block state");
        }
        this.properties = properties;
        this.customBlockStates = List.copyOf(customBlockStates);
    }

    public void spawnAfterBreak(MCCBlockState state, MCCLocation location, MCCItemStack stack, boolean dropExperience) {
    }

    public void attack(MCCBlockState state, MCCLocation location, MCCPlayer player) {
    }

    public void tick(MCCBlockState state, MCCLocation location, RandomSource random) {
    }

    public void randomTick(MCCBlockState state, MCCLocation location, RandomSource random) {
    }

    public boolean canSurvive(MCCBlockState state, MCCLocation location) {
        return true;
    }

    public MCCBlockState rotate(MCCBlockState state, float rotation) {
        return state;
    }

    //TODO: Mixin at ServerLevel;tickPrecipitation
    public void handlePrecipitation(MCCBlockState state, MCCLocation location, MCCBiome.Precipitation precipitation) {
    }

    //TODO: Mixin at Entity;applyEffectsFromBlocks
    public void stepOn(MCCLocation location, MCCBlockState state, MCCEntity entity) {
    }

    //TODO: Mixin at ServerPlayerGameMode;destroyBlock -> Paper deprecated the playerDestroy method so on paper we might want to use BlockBreakEvent for this?
    public void destroy(MCCLocation location, MCCBlockState state) {
    }

    public void onProjectileHit(MCCBlockState state, MCCLocation location, BlockHitResult hit, MCCProjectile projectile) {
    }

    public List<MCCItemStack> getDrops(MCCBlockState state, LootParams.Builder params) {

    }

    public MenuProvider getMenuProvider(MCCBlockState state, MCCLocation location) {
        return null;
    }

    public MCCInteractionResult useItemOn(MCCItemStack stack, MCCBlockState state, MCCLocation location, MCCPlayer player, MCCInteractionHand hand, BlockHitResult hitResult) {
        return MCCInteractionResult.PASS;
    }

    public MCCInteractionResult useWithoutItem(MCCBlockState state, MCCLocation location, MCCPlayer player, BlockHitResult hitResult) {
        return MCCInteractionResult.PASS;
    }

    @Override
    public List<MCCBlockState> getAllBlockStates() {
        return customBlockStates;
    }

    @Override
    public MCCBlockState getDefaultState() {
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

    // Here we prevent some changes to methods that need to be implemented like this
    @Override
    public final void setBlock(@NotNull MCCLocation location) {
        MCCBlockType.super.setBlock(location);
    }

    @Override
    public final void setBlock(@NotNull MCCLocation location, boolean applyPhysics) {
        MCCBlockType.super.setBlock(location, applyPhysics);
    }

    @Override
    public final int getIndexOfState(MCCBlockState mccBlockState) {
        return MCCBlockType.super.getIndexOfState(mccBlockState);
    }
}
