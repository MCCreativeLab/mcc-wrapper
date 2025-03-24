package de.verdox.mccreativelab.impl.vanilla.platform.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSCustomModelData;

import java.lang.Boolean;

import de.verdox.mccreativelab.wrapper.item.components.*;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.saveddata.maps.MapId;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSMapItemColor;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Unbreakable;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSEnchantable;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSDyedItemColor;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSLodestoneTracker;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSUseCooldown;
import net.minecraft.core.Holder;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSDataComponentType;
import net.kyori.adventure.key.Key;

import java.util.List;

import net.minecraft.world.item.component.MapItemColor;

import java.lang.Class;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.item.enchantment.Repairable;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSSuspiciousStewEffects;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSTool;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSMapId;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.component.UseRemainder;
import net.minecraft.world.item.component.ChargedProjectiles;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import net.minecraft.world.item.component.OminousBottleAmplifier;
import net.minecraft.world.item.component.LodestoneTracker;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSRepairable;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSBlockItemStateProperties;
import net.minecraft.world.item.component.Tool;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSChargedProjectiles;
import net.minecraft.world.item.AdventureModePredicate;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.JukeboxPlayable;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSJukeboxPlayable;
import net.minecraft.world.item.Rarity;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSUnbreakable;
import net.minecraft.world.item.enchantment.Enchantable;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.CustomModelData;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSItemLore;
import net.minecraft.world.item.component.UseCooldown;
import net.minecraft.world.item.component.DebugStickState;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.component.BlockItemStateProperties;

import java.lang.Integer;

import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSFoodProperties;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSUseRemainder;
import de.verdox.mccreativelab.wrapper.types.MCCInstrument;
import net.minecraft.world.item.Instrument;

public class DataComponentTypeConverter implements MCCConverter<DataComponentType, NMSDataComponentType> {
    public MCCConverter.ConversionResult<NMSDataComponentType> wrap(DataComponentType nativeType) {
        if (nativeType.equals(DataComponents.CUSTOM_DATA)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<CustomData>() {}, new TypeToken<CustomData>() {}, null));
        }
        if (nativeType.equals(DataComponents.MAX_STACK_SIZE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>() {}, new TypeToken<Integer>() {}, null));
        }
        if (nativeType.equals(DataComponents.MAX_DAMAGE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>() {}, new TypeToken<Integer>() {}, null));
        }
        if (nativeType.equals(DataComponents.DAMAGE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>() {}, new TypeToken<Integer>() {}, null));
        }
        if (nativeType.equals(DataComponents.UNBREAKABLE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Unbreakable>() {}, new TypeToken<MCCUnbreakable>() {}, () -> new NMSUnbreakable(null)));
        }
        if (nativeType.equals(DataComponents.CUSTOM_NAME)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Component>() {}, new TypeToken<net.kyori.adventure.text.Component>() {}, null));
        }
        if (nativeType.equals(DataComponents.ITEM_NAME)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Component>() {}, new TypeToken<net.kyori.adventure.text.Component>() {}, null));
        }
        if (nativeType.equals(DataComponents.ITEM_MODEL)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ResourceLocation>() {}, new TypeToken<Key>() {}, null));
        }
        if (nativeType.equals(DataComponents.LORE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ItemLore>() {}, new TypeToken<MCCItemLore>() {}, () -> new NMSItemLore(null)));
        }
        if (nativeType.equals(DataComponents.RARITY)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Rarity>() {}, new TypeToken<Rarity>() {}, null));
        }
        if (nativeType.equals(DataComponents.ENCHANTMENTS)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ItemEnchantments>() {}, new TypeToken<ItemEnchantments>() {}, null));
        }
        if (nativeType.equals(DataComponents.CAN_PLACE_ON)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<AdventureModePredicate>() {}, new TypeToken<AdventureModePredicate>() {}, null));
        }
        if (nativeType.equals(DataComponents.CAN_BREAK)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<AdventureModePredicate>() {}, new TypeToken<AdventureModePredicate>() {}, null));
        }
        if (nativeType.equals(DataComponents.CUSTOM_MODEL_DATA)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<CustomModelData>() {}, new TypeToken<MCCCustomModelData>() {}, () -> new NMSCustomModelData(null)));
        }
        if (nativeType.equals(DataComponents.REPAIR_COST)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>() {}, new TypeToken<Integer>() {}, null));
        }
        if (nativeType.equals(DataComponents.ENCHANTMENT_GLINT_OVERRIDE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Boolean>() {}, new TypeToken<Boolean>() {}, null));
        }
        if (nativeType.equals(DataComponents.FOOD)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<FoodProperties>() {}, new TypeToken<MCCFoodProperties>() {}, () -> new NMSFoodProperties(null)));
        }
        if (nativeType.equals(DataComponents.USE_REMAINDER)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<UseRemainder>() {}, new TypeToken<MCCUseRemainder>() {}, () -> new NMSUseRemainder(null)));
        }
        if (nativeType.equals(DataComponents.USE_COOLDOWN)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<UseCooldown>() {}, new TypeToken<MCCUseCooldown>() {}, () -> new NMSUseCooldown(null)));
        }
        if (nativeType.equals(DataComponents.TOOL)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Tool>() {}, new TypeToken<MCCTool>() {}, () -> new NMSTool(null)));
        }
        if (nativeType.equals(DataComponents.ENCHANTABLE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Enchantable>() {}, new TypeToken<MCCEnchantable>() {}, () -> new NMSEnchantable(null)));
        }
        if (nativeType.equals(DataComponents.REPAIRABLE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Repairable>() {}, new TypeToken<MCCRepairable>() {}, () -> new NMSRepairable(null)));
        }
        if (nativeType.equals(DataComponents.TOOLTIP_STYLE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ResourceLocation>() {}, new TypeToken<Key>() {}, null));
        }
        if (nativeType.equals(DataComponents.STORED_ENCHANTMENTS)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ItemEnchantments>() {}, new TypeToken<ItemEnchantments>() {}, null));
        }
        if (nativeType.equals(DataComponents.DYED_COLOR)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<DyedItemColor>() {}, new TypeToken<MCCDyedItemColor>() {}, () -> new NMSDyedItemColor(null)));
        }
        if (nativeType.equals(DataComponents.MAP_COLOR)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<MapItemColor>() {}, new TypeToken<MCCMapItemColor>() {}, () -> new NMSMapItemColor(null)));
        }
        if (nativeType.equals(DataComponents.MAP_ID)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<MapId>() {}, new TypeToken<MCCMapId>() {}, () -> new NMSMapId(null)));
        }
        if (nativeType.equals(DataComponents.CHARGED_PROJECTILES)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ChargedProjectiles>() {}, new TypeToken<MCCChargedProjectiles>() {}, () -> new NMSChargedProjectiles(null)));
        }
        if (nativeType.equals(DataComponents.BUNDLE_CONTENTS)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<BundleContents>() {}, new TypeToken<BundleContents>() {}, null));
        }
        if (nativeType.equals(DataComponents.SUSPICIOUS_STEW_EFFECTS)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<SuspiciousStewEffects>() {}, new TypeToken<MCCSuspiciousStewEffects>() {}, () -> new NMSSuspiciousStewEffects(null)));
        }
        if (nativeType.equals(DataComponents.WRITABLE_BOOK_CONTENT)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<WritableBookContent>() {}, new TypeToken<WritableBookContent>() {}, null));
        }
        if (nativeType.equals(DataComponents.WRITTEN_BOOK_CONTENT)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<WrittenBookContent>() {}, new TypeToken<WrittenBookContent>() {}, null));
        }
        if (nativeType.equals(DataComponents.TRIM)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ArmorTrim>() {}, new TypeToken<ArmorTrim>() {}, null));
        }
        if (nativeType.equals(DataComponents.DEBUG_STICK_STATE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<DebugStickState>() {}, new TypeToken<DebugStickState>() {}, null));
        }
        if (nativeType.equals(DataComponents.ENTITY_DATA)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<CustomData>() {}, new TypeToken<CustomData>() {}, null));
        }
        if (nativeType.equals(DataComponents.BUCKET_ENTITY_DATA)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<CustomData>() {}, new TypeToken<CustomData>() {}, null));
        }
        if (nativeType.equals(DataComponents.BLOCK_ENTITY_DATA)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<CustomData>() {}, new TypeToken<CustomData>() {}, null));
        }
        if (nativeType.equals(DataComponents.INSTRUMENT)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Holder<Instrument>>() {}, new TypeToken<MCCReference<MCCInstrument>>() {}, null));
        }
        if (nativeType.equals(DataComponents.OMINOUS_BOTTLE_AMPLIFIER)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<OminousBottleAmplifier>() {}, new TypeToken<OminousBottleAmplifier>() {}, null));
        }
        if (nativeType.equals(DataComponents.JUKEBOX_PLAYABLE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<JukeboxPlayable>() {}, new TypeToken<MCCJukeboxPlayable>() {}, () -> new NMSJukeboxPlayable(null)));
        }
        if (nativeType.equals(DataComponents.RECIPES)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<List<ResourceKey<Recipe<?>>>>() {}, new TypeToken<List<MCCTypedKey<Recipe<?>>>>() {}, null));
        }
        if (nativeType.equals(DataComponents.LODESTONE_TRACKER)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<LodestoneTracker>() {}, new TypeToken<MCCLodestoneTracker>() {}, () -> new NMSLodestoneTracker(null)));
        }
        if (nativeType.equals(DataComponents.FIREWORKS)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Fireworks>() {}, new TypeToken<Fireworks>() {}, null));
        }
        if (nativeType.equals(DataComponents.NOTE_BLOCK_SOUND)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ResourceLocation>() {}, new TypeToken<Key>() {}, null));
        }
        if (nativeType.equals(DataComponents.BANNER_PATTERNS)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<BannerPatternLayers>() {}, new TypeToken<BannerPatternLayers>() {}, null));
        }
        if (nativeType.equals(DataComponents.BASE_COLOR)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<DyeColor>() {}, new TypeToken<DyeColor>() {}, null));
        }
        if (nativeType.equals(DataComponents.POT_DECORATIONS)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<PotDecorations>() {}, new TypeToken<PotDecorations>() {}, null));
        }
        if (nativeType.equals(DataComponents.CONTAINER)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ItemContainerContents>() {}, new TypeToken<ItemContainerContents>() {}, null));
        }
        if (nativeType.equals(DataComponents.BLOCK_STATE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<BlockItemStateProperties>() {}, new TypeToken<MCCBlockItemStateProperties>() {}, () -> new NMSBlockItemStateProperties(null)));
        }
        if (nativeType.equals(DataComponents.BEES)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<List<BeehiveBlockEntity.Occupant>>() {}, new TypeToken<List<BeehiveBlockEntity.Occupant>>() {}, null));
        }
        return notDone(null);
    }

    public MCCConverter.ConversionResult<DataComponentType> unwrap(NMSDataComponentType platformImplType) {
        return done((DataComponentType) platformImplType.getHandle());
    }

    public Class<NMSDataComponentType> apiImplementationClass() {
        return NMSDataComponentType.class;
    }

    public Class<DataComponentType> nativeMinecraftType() {
        return DataComponentType.class;
    }
}