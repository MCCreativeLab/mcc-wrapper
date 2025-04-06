package de.verdox.mccreativelab.custom.block;

import de.verdox.mccreativelab.custom.annotation.MCCCustomType;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.level.biome.MCCBiome;

@MCCCustomType
public abstract class MCCCustomBlockType implements MCCBlockType {

    public MCCCustomBlockType() {

    }

    public abstract void spawnAfterBreak(MCCBlockState state, MCCLocation location, MCCItemStack stack, boolean dropExperience);

    public abstract void attack(MCCBlockState state, MCCLocation location, MCCPlayer player);

    public abstract void tick(MCCBlockState state, MCCLocation location, RandomSource random);

    public abstract void randomTick(MCCBlockState state, MCCLocation location, RandomSource random);

    public abstract boolean canSurvive(MCCBlockState state, MCCLocation location);

    public abstract MCCBlockState rotate(MCCBlockState state, float rotation);

    public abstract boolean dropFromExplosion(Explosion explosion);

    public abstract void handlePrecipitation(MCCBlockState state, MCCLocation location, MCCBiome.Precipitation precipitation);

    public abstract void stepOn(MCCLocation location, MCCBlockState state, Entity entity);

    public abstract void wasExploded(MCCLocation location, Explosion explosion);

    public abstract void destroy(MCCLocation location, MCCBlockState state);
}
