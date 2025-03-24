package de.verdox.mccreativelab.impl.vanilla.platform.converter;

import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.impl.vanilla.types.*;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.*;
import de.verdox.mccreativelab.wrapper.item.components.*;
import de.verdox.mccreativelab.wrapper.types.*;

public class GeneratedConverters  {

	public static void init(ConversionService conversionService){
		conversionService.registerConverterForNewImplType(MCCEnchantment.EnchantmentDefinition.class, NMSEnchantment.NMSEnchantmentDefinition.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCGameEvent.class, NMSGameEvent.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCDecoratedPotPattern.class, NMSDecoratedPotPattern.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCItemAttributeModifiers.Entry.class, NMSItemAttributeModifiers.NMSEntry.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCFoodProperties.class, NMSFoodProperties.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCArmorMaterial.class, NMSArmorMaterial.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCFoodProperties.PossibleEffect.class, NMSFoodProperties.NMSPossibleEffect.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCDataComponentType.class, new DataComponentTypeConverter());
		conversionService.registerConverterForNewImplType(MCCBlockItemStateProperties.class, NMSBlockItemStateProperties.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCResolvableProfile.class, NMSResolvableProfile.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCInstrument.class, NMSInstrument.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCMapItemColor.class, NMSMapItemColor.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCMapId.class, NMSMapId.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCPoiType.class, NMSPoiType.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCItemLore.class, NMSItemLore.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCTrimMaterial.class, NMSTrimMaterial.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCDyedItemColor.class, NMSDyedItemColor.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCPaintingVariant.class, NMSPaintingVariant.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCEnchantment.class, NMSEnchantment.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCItemAttributeModifiers.Builder.class, NMSItemAttributeModifiers.NMSBuilder.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCDimensionType.class, NMSDimensionType.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCPotionContents.class, NMSPotionContents.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCEnchantment.Builder.class, NMSEnchantment.NMSBuilder.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCTrimPattern.class, NMSTrimPattern.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCMapDecorations.Entry.class, NMSMapDecorations.NMSEntry.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCFrogVariant.class, NMSFrogVariant.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCJukeboxSong.class, NMSJukeboxSong.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCEnchantment.Cost.class, NMSEnchantment.NMSCost.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCItemAttributeModifiers.class, NMSItemAttributeModifiers.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCSeededContainerLoot.class, NMSSeededContainerLoot.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCGameEvent.ListenerInfo.class, NMSGameEvent.NMSListenerInfo.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCArmorMaterial.Layer.class, NMSArmorMaterial.NMSLayer.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCUnbreakable.class, NMSUnbreakable.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCCustomModelData.class, NMSCustomModelData.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCTool.class, NMSTool.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCJukeboxPlayable.class, NMSJukeboxPlayable.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCSuspiciousStewEffects.Entry.class, NMSSuspiciousStewEffects.NMSEntry.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCLockCode.class, NMSLockCode.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCFireworkExplosion.class, NMSFireworkExplosion.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCVillagerProfession.class, NMSVillagerProfession.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCDimensionType.MonsterSettings.class, NMSDimensionType.NMSMonsterSettings.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCMapDecorations.class, NMSMapDecorations.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCFoodProperties.Builder.class, NMSFoodProperties.NMSBuilder.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCTool.Rule.class, NMSTool.NMSRule.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCGameEvent.Context.class, NMSGameEvent.NMSContext.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCSuspiciousStewEffects.class, NMSSuspiciousStewEffects.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCArmorTrim.class, NMSArmorTrim.CONVERTER);
		conversionService.registerConverterForNewImplType(MCCLodestoneTracker.class, NMSLodestoneTracker.CONVERTER);
	}

}
