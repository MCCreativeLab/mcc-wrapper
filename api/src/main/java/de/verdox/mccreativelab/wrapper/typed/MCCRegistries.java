package de.verdox.mccreativelab.wrapper.typed;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.types.*;
import de.verdox.mccreativelab.wrapper.entity.MCCEffectType;
import de.verdox.mccreativelab.wrapper.inventory.MCCMenuType;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public interface MCCRegistries {
    @NotNull Key REGISTRY_OF_REGISTRIES = Key.key("minecraft", "root");

    //TODO:
    // TrimPattern
    // TrimMaterial
    // Recipe
    // Advancement
    // DimensionType
    // ArmorMaterial
    // DecoratedPotPattern
    // VillagerType
    // StructureType
    // StatType
    // SoundEvent
    // SensorType
    // Schedule
    // RecipeType
    // ParticleType
    // MobEffect
    // MemoryModuleType
    // FrogVariant


    MCCTypedKey<MCCRegistry<MCCAttribute>> ATTRIBUTE_REGISTRY = registry("attribute", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCBlockType>> BLOCK_REGISTRY = registry("block", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCItemType>> ITEM_REGISTRY = registry("item", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCDataComponentType<?>>> DATA_COMPONENT_REGISTRY = registry("data_component_type", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCDataComponentType<?>>> ENCHANTMENT_EFFECT_COMPONENT_TYPE_REGISTRY = registry("enchantment_effect_component_type", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCEffectType>> EFFECT_TYPE_REGISTRY = registry("mob_effect", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCMenuType<?>>> MENU_REGISTRY = registry("menu", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCGameEvent>> GAME_EVENT_REGISTRY = registry("game_event", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCInstrument>> INSTRUMENT_REGISTRY = registry("instrument", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCJukeboxSong>> JUKEBOX_SONG_REGISTRY = registry("jukebox_song", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCPoiType>> POI_TYPE_REGISTRY = registry("point_of_interest_type", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCPotion>> POTION_REGISTRY = registry("potion", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCVillagerProfession>> VILLAGER_PROFESSION_REGISTRY = registry("villager_profession", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCPaintingVariant>> PAINTING_VARIANT_REGISTRY = registry("painting_variant", new TypeToken<>() {});
    MCCTypedKey<MCCRegistry<MCCEnchantment>> ENCHANTMENT_REGISTRY = registry("enchantment", new TypeToken<>() {});

    private static <T> MCCTypedKey<T> registry(String registryKey, TypeToken<T> type) {
        return MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", registryKey), REGISTRY_OF_REGISTRIES, type);
    }
}
