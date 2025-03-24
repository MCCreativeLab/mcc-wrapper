package de.verdox.mccreativelab.impl.vanilla.platform.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSDataComponentType;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.*;
import de.verdox.mccreativelab.wrapper.item.components.*;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.types.MCCInstrument;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.LockCode;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.saveddata.maps.MapId;

import java.util.List;

public class DataComponentTypeConverter implements MCCConverter<DataComponentType, NMSDataComponentType>  {

	public MCCConverter.ConversionResult<NMSDataComponentType> wrap(DataComponentType nativeType){
		if(nativeType.equals(DataComponents.MAX_STACK_SIZE)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>(){}, new TypeToken<Integer>(){}, null));
		}
		if(nativeType.equals(DataComponents.MAX_DAMAGE)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>(){}, new TypeToken<Integer>(){}, null));
		}
		if(nativeType.equals(DataComponents.DAMAGE)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>(){}, new TypeToken<Integer>(){}, null));
		}
		if(nativeType.equals(DataComponents.UNBREAKABLE)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<Unbreakable>(){}, new TypeToken<MCCUnbreakable>(){}, () -> new NMSUnbreakable(null)));
		}
		if(nativeType.equals(DataComponents.CUSTOM_NAME)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<Component>(){}, new TypeToken<net.kyori.adventure.text.Component>(){}, null));
		}
		if(nativeType.equals(DataComponents.ITEM_NAME)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<Component>(){}, new TypeToken<net.kyori.adventure.text.Component>(){}, null));
		}
		if(nativeType.equals(DataComponents.LORE)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<ItemLore>(){}, new TypeToken<MCCItemLore>(){}, () -> new NMSItemLore(null)));
		}
		if(nativeType.equals(DataComponents.RARITY)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<Rarity>(){}, new TypeToken<MCCRarity>(){}, null));
		}
		if(nativeType.equals(DataComponents.ATTRIBUTE_MODIFIERS)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<ItemAttributeModifiers>(){}, new TypeToken<MCCItemAttributeModifiers>(){}, () -> new NMSItemAttributeModifiers(null)));
		}
		if(nativeType.equals(DataComponents.CUSTOM_MODEL_DATA)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<CustomModelData>(){}, new TypeToken<MCCCustomModelData>(){}, () -> new NMSCustomModelData(null)));
		}
		if(nativeType.equals(DataComponents.REPAIR_COST)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>(){}, new TypeToken<Integer>(){}, null));
		}
		if(nativeType.equals(DataComponents.ENCHANTMENT_GLINT_OVERRIDE)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<Boolean>(){}, new TypeToken<Boolean>(){}, null));
		}
		if(nativeType.equals(DataComponents.FOOD)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<FoodProperties>(){}, new TypeToken<MCCFoodProperties>(){}, () -> new NMSFoodProperties(null)));
		}
		if(nativeType.equals(DataComponents.TOOL)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<Tool>(){}, new TypeToken<MCCTool>(){}, () -> new NMSTool(null)));
		}
		if(nativeType.equals(DataComponents.DYED_COLOR)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<DyedItemColor>(){}, new TypeToken<MCCDyedItemColor>(){}, () -> new NMSDyedItemColor(null)));
		}
		if(nativeType.equals(DataComponents.MAP_COLOR)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<MapItemColor>(){}, new TypeToken<MCCMapItemColor>(){}, () -> new NMSMapItemColor(null)));
		}
		if(nativeType.equals(DataComponents.MAP_ID)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<MapId>(){}, new TypeToken<MCCMapId>(){}, () -> new NMSMapId(null)));
		}
		if(nativeType.equals(DataComponents.MAP_DECORATIONS)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<MapDecorations>(){}, new TypeToken<MCCMapDecorations>(){}, () -> new NMSMapDecorations(null)));
		}
		if(nativeType.equals(DataComponents.MAP_POST_PROCESSING)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<MapPostProcessing>(){}, new TypeToken<MCCMapPostProcessing>(){}, null));
		}
		if(nativeType.equals(DataComponents.POTION_CONTENTS)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<PotionContents>(){}, new TypeToken<MCCPotionContents>(){}, () -> new NMSPotionContents(null)));
		}
		if(nativeType.equals(DataComponents.SUSPICIOUS_STEW_EFFECTS)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<SuspiciousStewEffects>(){}, new TypeToken<MCCSuspiciousStewEffects>(){}, () -> new NMSSuspiciousStewEffects(null)));
		}
		if(nativeType.equals(DataComponents.TRIM)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<ArmorTrim>(){}, new TypeToken<MCCArmorTrim>(){}, () -> new NMSArmorTrim(null)));
		}
		if(nativeType.equals(DataComponents.INSTRUMENT)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<Holder<Instrument>>(){}, new TypeToken<MCCReference<MCCInstrument>>(){}, null));
		}
		if(nativeType.equals(DataComponents.OMINOUS_BOTTLE_AMPLIFIER)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>(){}, new TypeToken<Integer>(){}, null));
		}
		if(nativeType.equals(DataComponents.JUKEBOX_PLAYABLE)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<JukeboxPlayable>(){}, new TypeToken<MCCJukeboxPlayable>(){}, () -> new NMSJukeboxPlayable(null)));
		}
		if(nativeType.equals(DataComponents.RECIPES)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<List<ResourceLocation>>(){}, new TypeToken<List<Key>>(){}, null));
		}
		if(nativeType.equals(DataComponents.LODESTONE_TRACKER)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<LodestoneTracker>(){}, new TypeToken<MCCLodestoneTracker>(){}, () -> new NMSLodestoneTracker(null)));
		}
		if(nativeType.equals(DataComponents.FIREWORK_EXPLOSION)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<FireworkExplosion>(){}, new TypeToken<MCCFireworkExplosion>(){}, () -> new NMSFireworkExplosion(null)));
		}
		if(nativeType.equals(DataComponents.PROFILE)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<ResolvableProfile>(){}, new TypeToken<MCCResolvableProfile>(){}, () -> new NMSResolvableProfile(null)));
		}
		if(nativeType.equals(DataComponents.NOTE_BLOCK_SOUND)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<ResourceLocation>(){}, new TypeToken<Key>(){}, null));
		}
		if(nativeType.equals(DataComponents.BASE_COLOR)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<DyeColor>(){}, new TypeToken<MCCDyeColor>(){}, null));
		}
		if(nativeType.equals(DataComponents.BLOCK_STATE)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<BlockItemStateProperties>(){}, new TypeToken<MCCBlockItemStateProperties>(){}, () -> new NMSBlockItemStateProperties(null)));
		}
		if(nativeType.equals(DataComponents.LOCK)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<LockCode>(){}, new TypeToken<MCCLockCode>(){}, () -> new NMSLockCode(null)));
		}
		if(nativeType.equals(DataComponents.CONTAINER_LOOT)) {
			return done(new NMSDataComponentType<>(nativeType, new TypeToken<SeededContainerLoot>(){}, new TypeToken<MCCSeededContainerLoot>(){}, () -> new NMSSeededContainerLoot(null)));
		}
		return notDone(null);
	}

	public MCCConverter.ConversionResult<DataComponentType> unwrap(NMSDataComponentType platformImplType){
		return done((DataComponentType) platformImplType.getHandle());
	}

	public Class<NMSDataComponentType> apiImplementationClass(){
		return NMSDataComponentType.class;
	}

	public Class<DataComponentType> nativeMinecraftType(){
		return DataComponentType.class;
	}

}
