package de.verdox.mccreativelab.conversion.world.level.biome;

import de.verdox.mccreativelab.NMSTestBase;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.world.level.biome.MCCBiome;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BiomeConversionTests extends NMSTestBase {

    @Test
    @DisplayName("Biome (native to MCC) Conversion Test")
    public void testBiomeConversion() {
        Biome mcBiome = new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(24F)
                .temperatureAdjustment(Biome.TemperatureModifier.FROZEN)
                .downfall(3)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(21)
                        .waterColor(21)
                        .waterFogColor(21)
                        .skyColor(21)
                        .grassColorOverride(21)
                        .ambientLoopSound(
                                Holder.direct(SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath("minecraft", "ambient.loop"), 1))
                        ).ambientAdditionsSound(
                        new AmbientAdditionsSettings(Holder.direct(SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath("minecraft", "ambient.additions"), 1)), 1)
                    ).build()
                )
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(BiomeGenerationSettings.EMPTY)
                .build();
        MCCBiome mccBiome = MCCPlatform.getInstance().getConversionService().wrap(mcBiome, MCCBiome.class);

        // assertion (eq)
        assert mccBiome.hasPrecipitation() == mcBiome.hasPrecipitation();
        assert mccBiome.getBaseTemperature() == mcBiome.getBaseTemperature();
        assert mccBiome.getSpecialEffects().getFogColor() == mcBiome.getSpecialEffects().getFogColor();
        assert mccBiome.getSpecialEffects().getWaterColor() == mcBiome.getSpecialEffects().getWaterColor();
        assert mccBiome.getSpecialEffects().getWaterFogColor() == mcBiome.getSpecialEffects().getWaterFogColor();
        assert mccBiome.getSpecialEffects().getSkyColor() == mcBiome.getSpecialEffects().getSkyColor();
        assert mccBiome.getSpecialEffects().getGrassColorOverride().get().equals(mcBiome.getSpecialEffects().getGrassColorOverride().get());
        }
}
