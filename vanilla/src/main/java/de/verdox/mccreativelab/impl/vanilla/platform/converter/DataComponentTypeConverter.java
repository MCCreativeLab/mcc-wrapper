package de.verdox.mccreativelab.impl.vanilla.platform.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSCustomModelData;

import java.lang.Boolean;

import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.item.components.*;
import de.verdox.mccreativelab.wrapper.platform.serialization.MCCSerializers;
import de.verdox.vserializer.generic.Serializer;
import net.minecraft.world.level.saveddata.maps.MapId;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSMapItemColor;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Unbreakable;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSEnchantable;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSDyedItemColor;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSLodestoneTracker;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSUseCooldown;
import net.minecraft.core.Holder;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSDataComponentType;

import java.util.List;

import net.minecraft.world.item.component.MapItemColor;

import java.lang.Class;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.component.DataComponents;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import net.minecraft.world.item.enchantment.Repairable;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSSuspiciousStewEffects;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSTool;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSMapId;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.component.UseRemainder;
import net.minecraft.world.item.component.ChargedProjectiles;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import net.minecraft.world.item.component.LodestoneTracker;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSRepairable;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSBlockItemStateProperties;
import net.minecraft.world.item.component.Tool;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSChargedProjectiles;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.JukeboxPlayable;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSJukeboxPlayable;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSUnbreakable;
import net.minecraft.world.item.enchantment.Enchantable;
import net.minecraft.world.item.component.CustomModelData;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSItemLore;
import net.minecraft.world.item.component.UseCooldown;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.component.BlockItemStateProperties;

import java.lang.Integer;

import de.verdox.mccreativelab.impl.vanilla.item.components.NMSFoodProperties;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSUseRemainder;
import de.verdox.mccreativelab.wrapper.types.MCCInstrument;
import net.minecraft.world.item.Instrument;

public class DataComponentTypeConverter implements MCCConverter<DataComponentType, NMSDataComponentType> {
    //TODO:
    // - BeehiveBlockEntity.Occupant
    // - ItemContainerContents
    // - CustomData
    // - Rarity
    // - CAN_PLACE_ON
    // - CAN_BREAK
    // - ENCHANTMENTS
    // - STORED_ENCHANTMENTS
    // - BUNDLE_CONTENTS
    // - WRITABLE_BOOK_CONTENT
    // - WRITTEN_BOOK_CONTENT
    // - TRIM
    // - DEBUG_STICK_STATE
    // - ENTITY_DATA
    // - BUCKET_ENTITY_DATA
    // - BLOCK_ENTITY_DATA
    // - OMINOUS_BOTTLE_AMPLIFIER
    // - FIREWORKS
    // - BANNER_PATTERNS
    // - BASE_COLOR
    // - POT_DECORATIONS
    // - CONTAINER

    public MCCConverter.ConversionResult<NMSDataComponentType> wrap(DataComponentType nativeType) {
        if (nativeType.equals(DataComponents.MAX_STACK_SIZE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>() {}, new TypeToken<>() {}, Serializer.Primitive.INTEGER));
        }
        if (nativeType.equals(DataComponents.MAX_DAMAGE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>() {}, new TypeToken<>() {}, Serializer.Primitive.INTEGER));
        }
        if (nativeType.equals(DataComponents.DAMAGE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>() {}, new TypeToken<>() {}, Serializer.Primitive.INTEGER));
        }
        if (nativeType.equals(DataComponents.UNBREAKABLE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Unbreakable>() {}, new TypeToken<>() {}, MCCUnbreakable.SERIALIZER, () -> new NMSUnbreakable(null)));
        }
        if (nativeType.equals(DataComponents.CUSTOM_NAME)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Component>() {}, new TypeToken<>() {}, MCCSerializers.COMPONENT));
        }
        if (nativeType.equals(DataComponents.ITEM_NAME)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Component>() {}, new TypeToken<>() {}, MCCSerializers.COMPONENT));
        }
        if (nativeType.equals(DataComponents.ITEM_MODEL)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ResourceLocation>() {}, new TypeToken<>() {}, MCCKeyedWrapper.KEY_SERIALIZER));
        }
        if (nativeType.equals(DataComponents.LORE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ItemLore>() {}, new TypeToken<MCCItemLore>() {}, null, () -> new NMSItemLore(null)));
        }
        if (nativeType.equals(DataComponents.CUSTOM_MODEL_DATA)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<CustomModelData>() {}, new TypeToken<MCCCustomModelData>() {}, null, () -> new NMSCustomModelData(null)));
        }
        if (nativeType.equals(DataComponents.REPAIR_COST)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Integer>() {}, new TypeToken<>() {}, Serializer.Primitive.INTEGER));
        }
        if (nativeType.equals(DataComponents.ENCHANTMENT_GLINT_OVERRIDE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Boolean>() {}, new TypeToken<>() {}, Serializer.Primitive.BOOLEAN));
        }
        if (nativeType.equals(DataComponents.FOOD)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<FoodProperties>() {}, new TypeToken<MCCFoodProperties>() {}, null, () -> new NMSFoodProperties(null)));
        }
        if (nativeType.equals(DataComponents.USE_REMAINDER)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<UseRemainder>() {}, new TypeToken<MCCUseRemainder>() {}, null, () -> new NMSUseRemainder(null)));
        }
        if (nativeType.equals(DataComponents.USE_COOLDOWN)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<UseCooldown>() {}, new TypeToken<MCCUseCooldown>() {}, null, () -> new NMSUseCooldown(null)));
        }
        if (nativeType.equals(DataComponents.TOOL)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Tool>() {}, new TypeToken<MCCTool>() {}, null, () -> new NMSTool(null)));
        }
        if (nativeType.equals(DataComponents.ENCHANTABLE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Enchantable>() {}, new TypeToken<MCCEnchantable>() {}, null, () -> new NMSEnchantable(null)));
        }
        if (nativeType.equals(DataComponents.REPAIRABLE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Repairable>() {}, new TypeToken<MCCRepairable>() {}, null, () -> new NMSRepairable(null)));
        }
        if (nativeType.equals(DataComponents.TOOLTIP_STYLE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ResourceLocation>() {}, new TypeToken<>() {}, MCCKeyedWrapper.KEY_SERIALIZER));
        }
        if (nativeType.equals(DataComponents.DYED_COLOR)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<DyedItemColor>() {}, new TypeToken<MCCDyedItemColor>() {}, null, () -> new NMSDyedItemColor(null)));
        }
        if (nativeType.equals(DataComponents.MAP_COLOR)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<MapItemColor>() {}, new TypeToken<MCCMapItemColor>() {}, null, () -> new NMSMapItemColor(null)));
        }
        if (nativeType.equals(DataComponents.MAP_ID)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<MapId>() {}, new TypeToken<MCCMapId>() {}, null, () -> new NMSMapId(null)));
        }
        if (nativeType.equals(DataComponents.CHARGED_PROJECTILES)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ChargedProjectiles>() {}, new TypeToken<MCCChargedProjectiles>() {}, null, () -> new NMSChargedProjectiles(null)));
        }
        if (nativeType.equals(DataComponents.SUSPICIOUS_STEW_EFFECTS)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<SuspiciousStewEffects>() {}, new TypeToken<MCCSuspiciousStewEffects>() {}, null, () -> new NMSSuspiciousStewEffects(null)));
        }
        if (nativeType.equals(DataComponents.INSTRUMENT)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<Holder<Instrument>>() {}, new TypeToken<MCCReference<MCCInstrument>>() {}, null));
        }
        if (nativeType.equals(DataComponents.JUKEBOX_PLAYABLE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<JukeboxPlayable>() {}, new TypeToken<MCCJukeboxPlayable>() {}, null, () -> new NMSJukeboxPlayable(null)));
        }
        if (nativeType.equals(DataComponents.RECIPES)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<List<ResourceKey<Recipe<?>>>>() {}, new TypeToken<List<MCCTypedKey<Recipe<?>>>>() {}, null));
        }
        if (nativeType.equals(DataComponents.LODESTONE_TRACKER)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<LodestoneTracker>() {}, new TypeToken<MCCLodestoneTracker>() {}, null, () -> new NMSLodestoneTracker(null)));
        }
        if (nativeType.equals(DataComponents.NOTE_BLOCK_SOUND)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<ResourceLocation>() {}, new TypeToken<>() {}, MCCKeyedWrapper.KEY_SERIALIZER));
        }
        if (nativeType.equals(DataComponents.BLOCK_STATE)) {
            return done(new NMSDataComponentType<>(nativeType, new TypeToken<BlockItemStateProperties>() {}, new TypeToken<MCCBlockItemStateProperties>() {}, null, () -> new NMSBlockItemStateProperties(null)));
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