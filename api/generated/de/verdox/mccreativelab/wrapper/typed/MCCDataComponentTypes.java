package de.verdox.mccreativelab.wrapper.typed;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.item.components.*;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.types.MCCInstrument;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

public class MCCDataComponentTypes {
    public static final Key VANILLA_REGISTRY_KEY = Key.key("minecraft", "data_component_type");

    //public static final MCCReference<MCCDataComponentType<CustomData>> CUSTOM_DATA = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "custom_data"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<CustomData>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<Integer>> MAX_STACK_SIZE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "max_stack_size"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Integer>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<Integer>> MAX_DAMAGE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "max_damage"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Integer>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<Integer>> DAMAGE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "damage"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Integer>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCUnbreakable>> UNBREAKABLE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "unbreakable"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCUnbreakable>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<Component>> CUSTOM_NAME = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "custom_name"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Component>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<Component>> ITEM_NAME = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "item_name"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Component>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<Key>> ITEM_MODEL = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "item_model"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Key>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCItemLore>> LORE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "lore"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCItemLore>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<Rarity>> RARITY = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "rarity"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Rarity>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<ItemEnchantments>> ENCHANTMENTS = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "enchantments"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<ItemEnchantments>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<AdventureModePredicate>> CAN_PLACE_ON = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "can_place_on"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<AdventureModePredicate>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<AdventureModePredicate>> CAN_BREAK = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "can_break"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<AdventureModePredicate>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCItemAttributeModifiers>> ATTRIBUTE_MODIFIERS = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "attribute_modifiers"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCItemAttributeModifiers>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCCustomModelData>> CUSTOM_MODEL_DATA = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "custom_model_data"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCCustomModelData>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCUnit>> HIDE_ADDITIONAL_TOOLTIP = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "hide_additional_tooltip"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCUnit>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCUnit>> HIDE_TOOLTIP = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "hide_tooltip"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCUnit>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<Integer>> REPAIR_COST = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "repair_cost"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Integer>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCUnit>> CREATIVE_SLOT_LOCK = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "creative_slot_lock"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCUnit>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<Boolean>> ENCHANTMENT_GLINT_OVERRIDE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "enchantment_glint_override"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Boolean>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCUnit>> INTANGIBLE_PROJECTILE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "intangible_projectile"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCUnit>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCFoodProperties>> FOOD = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "food"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCFoodProperties>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<Consumable>> CONSUMABLE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "consumable"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Consumable>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCUseRemainder>> USE_REMAINDER = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "use_remainder"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCUseRemainder>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCUseCooldown>> USE_COOLDOWN = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "use_cooldown"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCUseCooldown>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<DamageResistant>> DAMAGE_RESISTANT = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "damage_resistant"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<DamageResistant>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCTool>> TOOL = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "tool"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCTool>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCEnchantable>> ENCHANTABLE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "enchantable"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCEnchantable>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCEquippable>> EQUIPPABLE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "equippable"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCEquippable>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCRepairable>> REPAIRABLE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "repairable"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCRepairable>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCUnit>> GLIDER = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "glider"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCUnit>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<Key>> TOOLTIP_STYLE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "tooltip_style"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Key>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<DeathProtection>> DEATH_PROTECTION = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "death_protection"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<DeathProtection>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<ItemEnchantments>> STORED_ENCHANTMENTS = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "stored_enchantments"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<ItemEnchantments>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCDyedItemColor>> DYED_COLOR = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "dyed_color"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCDyedItemColor>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCMapItemColor>> MAP_COLOR = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "map_color"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCMapItemColor>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCMapId>> MAP_ID = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "map_id"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCMapId>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<MCCMapDecorations>> MAP_DECORATIONS = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "map_decorations"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCMapDecorations>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCChargedProjectiles>> CHARGED_PROJECTILES = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "charged_projectiles"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCChargedProjectiles>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<BundleContents>> BUNDLE_CONTENTS = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "bundle_contents"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<BundleContents>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<MCCPotionContents>> POTION_CONTENTS = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "potion_contents"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCPotionContents>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCSuspiciousStewEffects>> SUSPICIOUS_STEW_EFFECTS = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "suspicious_stew_effects"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCSuspiciousStewEffects>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<WritableBookContent>> WRITABLE_BOOK_CONTENT = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "writable_book_content"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<WritableBookContent>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<WrittenBookContent>> WRITTEN_BOOK_CONTENT = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "written_book_content"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<WrittenBookContent>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<ArmorTrim>> TRIM = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "trim"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<ArmorTrim>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<DebugStickState>> DEBUG_STICK_STATE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "debug_stick_state"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<DebugStickState>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<CustomData>> ENTITY_DATA = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "entity_data"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<CustomData>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<CustomData>> BUCKET_ENTITY_DATA = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "bucket_entity_data"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<CustomData>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<CustomData>> BLOCK_ENTITY_DATA = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_entity_data"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<CustomData>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCReference<MCCInstrument>>> INSTRUMENT = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "instrument"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCReference<MCCInstrument>>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<OminousBottleAmplifier>> OMINOUS_BOTTLE_AMPLIFIER = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "ominous_bottle_amplifier"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<OminousBottleAmplifier>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCJukeboxPlayable>> JUKEBOX_PLAYABLE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "jukebox_playable"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCJukeboxPlayable>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<List<MCCTypedKey<Recipe<?>>>>> RECIPES = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "recipes"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<List<MCCTypedKey<Recipe<?>>>>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCLodestoneTracker>> LODESTONE_TRACKER = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "lodestone_tracker"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCLodestoneTracker>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<MCCFireworkExplosion>> FIREWORK_EXPLOSION = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "firework_explosion"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCFireworkExplosion>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<Fireworks>> FIREWORKS = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "fireworks"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Fireworks>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<MCCResolvableProfile>> PROFILE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "profile"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCResolvableProfile>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<Key>> NOTE_BLOCK_SOUND = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "note_block_sound"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<Key>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<BannerPatternLayers>> BANNER_PATTERNS = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "banner_patterns"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<BannerPatternLayers>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<DyeColor>> BASE_COLOR = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "base_color"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<DyeColor>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<PotDecorations>> POT_DECORATIONS = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "pot_decorations"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<PotDecorations>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<ItemContainerContents>> CONTAINER = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "container"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<ItemContainerContents>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCBlockItemStateProperties>> BLOCK_STATE = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_state"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCBlockItemStateProperties>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<List<BeehiveBlockEntity.Occupant>>> BEES = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "bees"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<List<BeehiveBlockEntity.Occupant>>>() {}).getAsReference();

    //public static final MCCReference<MCCDataComponentType<MCCLockCode>> LOCK = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "lock"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCLockCode>>() {}).getAsReference();

    public static final MCCReference<MCCDataComponentType<MCCSeededContainerLoot>> CONTAINER_LOOT = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "container_loot"), VANILLA_REGISTRY_KEY, new TypeToken<MCCDataComponentType<MCCSeededContainerLoot>>() {}).getAsReference();
}