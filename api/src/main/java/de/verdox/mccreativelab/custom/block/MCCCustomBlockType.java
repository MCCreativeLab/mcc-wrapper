package de.verdox.mccreativelab.custom.block;

import de.verdox.mccreativelab.custom.annotation.MCCCustomType;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.level.biome.MCCBiome;

import java.util.List;

@MCCCustomType
public interface MCCCustomBlockType extends MCCBlockType {
    void spawnAfterBreak(MCCBlockState state, MCCLocation location, MCCItemStack stack, boolean dropExperience);

    void attack(MCCBlockState state, MCCLocation location, MCCPlayer player);

    void tick(MCCBlockState state, MCCLocation location, RandomSource random);

    void randomTick(MCCBlockState state, MCCLocation location, RandomSource random);

    boolean canSurvive(MCCBlockState state, MCCLocation location);

    MCCBlockState rotate(MCCBlockState state, float rotation);

    //TODO: Mixin at ServerLevel;tickPrecipitation
    void handlePrecipitation(MCCBlockState state, MCCLocation location, MCCBiome.Precipitation precipitation);

    //TODO: Mixin at Entity;applyEffectsFromBlocks
    void stepOn(MCCLocation location, MCCBlockState state, MCCEntity entity);

    //TODO: Mixin at ServerPlayerGameMode;destroyBlock -> Paper deprecated the playerDestroy method so on paper we might want to use BlockBreakEvent for this?
    void destroy(MCCLocation location, MCCBlockState state);

    void onProjectileHit(MCCBlockState state, MCCLocation location, BlockHitResult hit, MCCProjectile projectile);

    List<MCCItemStack> getDrops(MCCBlockState state, LootParams.Builder params);

    MenuProvider getMenuProvider(MCCBlockState state, MCCLocation location);

    InteractionResult useItemOn(MCCItemStack stack, MCCBlockState state, MCCLocation location, MCCPlayer player, InteractionHand hand, BlockHitResult hitResult);

    InteractionResult useWithoutItem(MCCBlockState state, MCCLocation location, MCCPlayer player, BlockHitResult hitResult);

    @Override
    MCCCustomBlockState getDefaultState();
}
