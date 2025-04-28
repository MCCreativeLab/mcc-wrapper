package de.verdox.mccreativelab.impl.vanilla.world.level.biome;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.sounds.MCCMusic;
import de.verdox.mccreativelab.wrapper.world.MCCBlockPos;
import de.verdox.mccreativelab.wrapper.world.level.biome.MCCAmbientAdditionsSettings;
import de.verdox.mccreativelab.wrapper.world.level.biome.MCCBiome;
import de.verdox.mccreativelab.wrapper.world.level.biome.MCCBiomeSpecialEffects;
import net.kyori.adventure.sound.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;

import java.util.Optional;

/**
 * Class representing the native Minecraft Biome implementing the MCCBiome interface
 */
public class NMSBiome extends MCCHandle<Biome> implements MCCBiome {
    public static final MCCConverter<Biome, NMSBiome> CONVERTER = converter(NMSBiome.class, Biome.class, NMSBiome::new, MCCHandle::getHandle);

    public NMSBiome(Biome handle) {
        super(handle);
    }

    @Override
    public Boolean hasPrecipitation() {
        return getHandle().hasPrecipitation();
    }

    @Override
    public Precipitation getPrecipitationAt(MCCBlockPos pos, int seaLevel) {
        return Precipitation.valueOf(getHandle().getPrecipitationAt(new BlockPos(pos.blockX(), pos.blockY(), pos.blockZ()), seaLevel).name());
    }

    @Override
    public float getBaseTemperature() {
        return getHandle().getBaseTemperature();
    }


    @Override
    public MCCBiomeSpecialEffects getSpecialEffects() {
        final BiomeSpecialEffects specialEffects = getHandle().getSpecialEffects();
        return new MCCBiomeSpecialEffects(
            specialEffects.getFogColor(),
            specialEffects.getWaterColor(),
            specialEffects.getWaterFogColor(),
            specialEffects.getSkyColor(),
            specialEffects.getFoliageColorOverride(),
            specialEffects.getGrassColorOverride(),
            MCCBiomeSpecialEffects.GrassColorModifier.valueOf(specialEffects.getGrassColorModifier().name())
        );
    }

    @Override
    @MCCReflective
    public TemperatureModifier getTemperatureModifier() {
        var climateSettings = ReflectionUtils.readFieldFromClass(getHandle(), "climateSettings", new TypeToken<>() {});
        return MCCBiome.TemperatureModifier.valueOf(ReflectionUtils.readFieldFromClass(climateSettings, "temperatureModifier", new TypeToken<Biome.TemperatureModifier>() {}).name());
    }

    @Override
    public Optional<MCCReference<Sound>> getAmbientLoop() {
        Optional<Holder<SoundEvent>> ambientLoop = getHandle().getAmbientLoop();
        return conversionService.wrap(ambientLoop, new TypeToken<>() {});
    }

    @Override
    public Optional<MCCAmbientAdditionsSettings> getAmbientAdditions() {
        Optional<AmbientAdditionsSettings> ambientAdditions = getHandle().getAmbientAdditions();

        return ambientAdditions.map(ambientAdditionsSettings -> new MCCAmbientAdditionsSettings(
            conversionService.wrap(ambientAdditionsSettings.getSoundEvent(), new TypeToken<>() {}), ambientAdditionsSettings.getTickChance()
        ));
    }

    @Override
    public Optional<MCCMusic> getBackgroundMusic() {
        return Optional.empty();
    }
}
