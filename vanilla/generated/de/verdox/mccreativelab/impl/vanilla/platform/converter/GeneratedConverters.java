package de.verdox.mccreativelab.impl.vanilla.platform.converter;

import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSBlockItemStateProperties;
import de.verdox.mccreativelab.wrapper.types.MCCJukeboxSong;
import de.verdox.mccreativelab.wrapper.item.components.MCCCustomModelData;
import de.verdox.mccreativelab.wrapper.types.MCCEnchantment;
import de.verdox.mccreativelab.wrapper.item.components.MCCTool;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.impl.vanilla.types.NMSTrimPattern;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSSuspiciousStewEffects;
import de.verdox.mccreativelab.wrapper.item.components.MCCFireworkExplosion;
import de.verdox.mccreativelab.wrapper.types.MCCArmorMaterial;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSUnbreakable;
import de.verdox.mccreativelab.wrapper.types.MCCPoiType;
import de.verdox.mccreativelab.wrapper.item.components.MCCBlockItemStateProperties;
import de.verdox.mccreativelab.impl.vanilla.types.NMSInstrument;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSDyedItemColor;
import de.verdox.mccreativelab.wrapper.item.components.MCCJukeboxPlayable;
import de.verdox.mccreativelab.wrapper.item.components.MCCArmorTrim;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSFoodProperties;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSMapDecorations;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSJukeboxPlayable;
import de.verdox.mccreativelab.wrapper.types.MCCGameEvent;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSItemAttributeModifiers;
import de.verdox.mccreativelab.impl.vanilla.types.NMSJukeboxSong;
import de.verdox.mccreativelab.wrapper.item.components.MCCLodestoneTracker;
import de.verdox.mccreativelab.wrapper.item.components.MCCUnbreakable;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSMapId;
import de.verdox.mccreativelab.wrapper.types.MCCDecoratedPotPattern;
import de.verdox.mccreativelab.wrapper.item.components.MCCMapDecorations;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSItemLore;
import de.verdox.mccreativelab.wrapper.types.MCCVillagerProfession;
import de.verdox.mccreativelab.wrapper.item.components.MCCFoodProperties;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSLodestoneTracker;
import de.verdox.mccreativelab.impl.vanilla.types.NMSGameEvent;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSPotionContents;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSLockCode;
import de.verdox.mccreativelab.wrapper.item.components.MCCMapId;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSCustomModelData;
import de.verdox.mccreativelab.wrapper.item.components.MCCMapItemColor;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSSeededContainerLoot;
import de.verdox.mccreativelab.impl.vanilla.types.NMSArmorMaterial;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSFireworkExplosion;
import de.verdox.mccreativelab.wrapper.item.components.MCCLockCode;
import de.verdox.mccreativelab.wrapper.item.components.MCCSeededContainerLoot;
import de.verdox.mccreativelab.impl.vanilla.types.NMSDecoratedPotPattern;
import de.verdox.mccreativelab.impl.vanilla.types.NMSPoiType;
import de.verdox.mccreativelab.impl.vanilla.types.NMSEnchantment;
import de.verdox.mccreativelab.wrapper.types.MCCTrimPattern;
import de.verdox.mccreativelab.impl.vanilla.types.NMSFrogVariant;
import de.verdox.mccreativelab.wrapper.item.components.MCCDyedItemColor;
import de.verdox.mccreativelab.wrapper.item.components.MCCSuspiciousStewEffects;
import de.verdox.mccreativelab.wrapper.item.components.MCCResolvableProfile;
import de.verdox.mccreativelab.impl.vanilla.types.NMSTrimMaterial;
import de.verdox.mccreativelab.wrapper.item.components.MCCItemAttributeModifiers;
import de.verdox.mccreativelab.wrapper.types.MCCInstrument;
import de.verdox.mccreativelab.wrapper.item.components.MCCItemLore;
import de.verdox.mccreativelab.wrapper.item.components.MCCPotionContents;
import de.verdox.mccreativelab.wrapper.types.MCCFrogVariant;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSResolvableProfile;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSArmorTrim;
import de.verdox.mccreativelab.wrapper.types.MCCPaintingVariant;
import de.verdox.mccreativelab.impl.vanilla.types.NMSPaintingVariant;
import de.verdox.mccreativelab.wrapper.types.MCCTrimMaterial;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSTool;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSMapItemColor;
import de.verdox.mccreativelab.wrapper.types.MCCDimensionType;
import de.verdox.mccreativelab.impl.vanilla.types.NMSDimensionType;
import de.verdox.mccreativelab.impl.vanilla.types.NMSVillagerProfession;

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
