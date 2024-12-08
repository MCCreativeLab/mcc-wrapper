package de.verdox.mccreativelab.wrapper.typed;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCTag;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import com.google.common.reflect.TypeToken;
import net.kyori.adventure.key.Key;

public class MCCBlockTags  {

	public static final Key VANILLA_REGISTRY_KEY  = Key.key("minecraft", "block");

	public static final MCCTag<MCCBlockType> WOOL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wool"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> PLANKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "planks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> STONE_BRICKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "stone_bricks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WOODEN_BUTTONS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_buttons"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> STONE_BUTTONS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "stone_buttons"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BUTTONS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "buttons"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WOOL_CARPETS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wool_carpets"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WOODEN_DOORS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_doors"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> MOB_INTERACTABLE_DOORS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "mob_interactable_doors"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WOODEN_STAIRS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_stairs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WOODEN_SLABS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_slabs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WOODEN_FENCES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_fences"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> PRESSURE_PLATES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "pressure_plates"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WOODEN_PRESSURE_PLATES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_pressure_plates"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> STONE_PRESSURE_PLATES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "stone_pressure_plates"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WOODEN_TRAPDOORS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_trapdoors"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> DOORS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "doors"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SAPLINGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "saplings"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> LOGS_THAT_BURN  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "logs_that_burn"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> OVERWORLD_NATURAL_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "overworld_natural_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> DARK_OAK_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "dark_oak_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> OAK_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "oak_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BIRCH_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "birch_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> ACACIA_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "acacia_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CHERRY_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "cherry_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> JUNGLE_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "jungle_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SPRUCE_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "spruce_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> MANGROVE_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "mangrove_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CRIMSON_STEMS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "crimson_stems"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WARPED_STEMS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "warped_stems"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BAMBOO_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "bamboo_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WART_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wart_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BANNERS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "banners"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SAND  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "sand"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SMELTS_TO_GLASS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "smelts_to_glass"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> STAIRS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "stairs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SLABS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "slabs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WALLS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "walls"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> ANVIL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "anvil"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> RAILS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "rails"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> LEAVES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "leaves"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> TRAPDOORS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "trapdoors"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SMALL_FLOWERS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "small_flowers"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BEDS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "beds"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> FENCES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "fences"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> TALL_FLOWERS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "tall_flowers"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> FLOWERS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "flowers"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> PIGLIN_REPELLENTS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "piglin_repellents"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> GOLD_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "gold_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> IRON_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "iron_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> DIAMOND_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "diamond_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> REDSTONE_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "redstone_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> LAPIS_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "lapis_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> COAL_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "coal_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> EMERALD_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "emerald_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> COPPER_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "copper_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CANDLES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "candles"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> DIRT  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "dirt"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> TERRACOTTA  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "terracotta"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BADLANDS_TERRACOTTA  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "badlands_terracotta"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CONCRETE_POWDER  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "concrete_powder"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> COMPLETES_FIND_TREE_TUTORIAL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "completes_find_tree_tutorial"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> FLOWER_POTS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "flower_pots"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> ENDERMAN_HOLDABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enderman_holdable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> ICE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "ice"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> VALID_SPAWN  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "valid_spawn"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> IMPERMEABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "impermeable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> UNDERWATER_BONEMEALS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "underwater_bonemeals"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CORAL_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "coral_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WALL_CORALS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wall_corals"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CORAL_PLANTS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "coral_plants"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CORALS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "corals"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BAMBOO_PLANTABLE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "bamboo_plantable_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> STANDING_SIGNS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "standing_signs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WALL_SIGNS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wall_signs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SIGNS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "signs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CEILING_HANGING_SIGNS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "ceiling_hanging_signs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WALL_HANGING_SIGNS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wall_hanging_signs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> ALL_HANGING_SIGNS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "all_hanging_signs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> ALL_SIGNS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "all_signs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> DRAGON_IMMUNE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "dragon_immune"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> DRAGON_TRANSPARENT  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "dragon_transparent"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WITHER_IMMUNE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wither_immune"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WITHER_SUMMON_BASE_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wither_summon_base_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BEEHIVES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "beehives"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CROPS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "crops"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BEE_GROWABLES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "bee_growables"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> PORTALS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "portals"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> FIRE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "fire"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> NYLIUM  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "nylium"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BEACON_BASE_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "beacon_base_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SOUL_SPEED_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "soul_speed_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WALL_POST_OVERRIDE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wall_post_override"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CLIMBABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "climbable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> FALL_DAMAGE_RESETTING  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "fall_damage_resetting"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SHULKER_BOXES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "shulker_boxes"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> HOGLIN_REPELLENTS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "hoglin_repellents"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SOUL_FIRE_BASE_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "soul_fire_base_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> STRIDER_WARM_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "strider_warm_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CAMPFIRES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "campfires"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> GUARDED_BY_PIGLINS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "guarded_by_piglins"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> PREVENT_MOB_SPAWNING_INSIDE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "prevent_mob_spawning_inside"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> FENCE_GATES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "fence_gates"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> UNSTABLE_BOTTOM_CENTER  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "unstable_bottom_center"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> MUSHROOM_GROW_BLOCK  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "mushroom_grow_block"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> INFINIBURN_OVERWORLD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "infiniburn_overworld"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> INFINIBURN_NETHER  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "infiniburn_nether"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> INFINIBURN_END  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "infiniburn_end"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BASE_STONE_OVERWORLD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "base_stone_overworld"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> STONE_ORE_REPLACEABLES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "stone_ore_replaceables"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> DEEPSLATE_ORE_REPLACEABLES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "deepslate_ore_replaceables"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BASE_STONE_NETHER  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "base_stone_nether"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> OVERWORLD_CARVER_REPLACEABLES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "overworld_carver_replaceables"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> NETHER_CARVER_REPLACEABLES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "nether_carver_replaceables"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CANDLE_CAKES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "candle_cakes"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CAULDRONS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "cauldrons"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CRYSTAL_SOUND_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "crystal_sound_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> INSIDE_STEP_SOUND_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "inside_step_sound_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> COMBINATION_STEP_SOUND_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "combination_step_sound_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CAMEL_SAND_STEP_SOUND_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "camel_sand_step_sound_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> OCCLUDES_VIBRATION_SIGNALS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "occludes_vibration_signals"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> DAMPENS_VIBRATIONS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "dampens_vibrations"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> DRIPSTONE_REPLACEABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "dripstone_replaceable_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CAVE_VINES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "cave_vines"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> MOSS_REPLACEABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "moss_replaceable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> LUSH_GROUND_REPLACEABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "lush_ground_replaceable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> AZALEA_ROOT_REPLACEABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "azalea_root_replaceable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SMALL_DRIPLEAF_PLACEABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "small_dripleaf_placeable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BIG_DRIPLEAF_PLACEABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "big_dripleaf_placeable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SNOW  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "snow"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> MINEABLE_WITH_AXE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "mineable/axe"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> MINEABLE_WITH_HOE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "mineable/hoe"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> MINEABLE_WITH_PICKAXE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "mineable/pickaxe"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> MINEABLE_WITH_SHOVEL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "mineable/shovel"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SWORD_EFFICIENT  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "sword_efficient"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> NEEDS_DIAMOND_TOOL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "needs_diamond_tool"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> NEEDS_IRON_TOOL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "needs_iron_tool"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> NEEDS_STONE_TOOL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "needs_stone_tool"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> INCORRECT_FOR_NETHERITE_TOOL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "incorrect_for_netherite_tool"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> INCORRECT_FOR_DIAMOND_TOOL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "incorrect_for_diamond_tool"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> INCORRECT_FOR_IRON_TOOL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "incorrect_for_iron_tool"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> INCORRECT_FOR_STONE_TOOL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "incorrect_for_stone_tool"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> INCORRECT_FOR_GOLD_TOOL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "incorrect_for_gold_tool"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> INCORRECT_FOR_WOODEN_TOOL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "incorrect_for_wooden_tool"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> FEATURES_CANNOT_REPLACE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "features_cannot_replace"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> LAVA_POOL_STONE_CANNOT_REPLACE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "lava_pool_stone_cannot_replace"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> GEODE_INVALID_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "geode_invalid_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> FROG_PREFER_JUMP_TO  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "frog_prefer_jump_to"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SCULK_REPLACEABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "sculk_replaceable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SCULK_REPLACEABLE_WORLD_GEN  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "sculk_replaceable_world_gen"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> ANCIENT_CITY_REPLACEABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "ancient_city_replaceable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> VIBRATION_RESONATORS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "vibration_resonators"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> ANIMALS_SPAWNABLE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "animals_spawnable_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> ARMADILLO_SPAWNABLE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "armadillo_spawnable_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> AXOLOTLS_SPAWNABLE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "axolotls_spawnable_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> GOATS_SPAWNABLE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "goats_spawnable_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> MOOSHROOMS_SPAWNABLE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "mooshrooms_spawnable_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> PARROTS_SPAWNABLE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "parrots_spawnable_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> POLAR_BEARS_SPAWNABLE_ON_ALTERNATE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "polar_bears_spawnable_on_alternate"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> RABBITS_SPAWNABLE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "rabbits_spawnable_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> FOXES_SPAWNABLE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "foxes_spawnable_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> WOLVES_SPAWNABLE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wolves_spawnable_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> FROGS_SPAWNABLE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "frogs_spawnable_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> AZALEA_GROWS_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "azalea_grows_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> CONVERTABLE_TO_MUD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "convertable_to_mud"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> MANGROVE_LOGS_CAN_GROW_THROUGH  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "mangrove_logs_can_grow_through"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> MANGROVE_ROOTS_CAN_GROW_THROUGH  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "mangrove_roots_can_grow_through"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> DEAD_BUSH_MAY_PLACE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "dead_bush_may_place_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SNAPS_GOAT_HORN  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "snaps_goat_horn"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> REPLACEABLE_BY_TREES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "replaceable_by_trees"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SNOW_LAYER_CANNOT_SURVIVE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "snow_layer_cannot_survive_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SNOW_LAYER_CAN_SURVIVE_ON  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "snow_layer_can_survive_on"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> INVALID_SPAWN_INSIDE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "invalid_spawn_inside"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SNIFFER_DIGGABLE_BLOCK  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "sniffer_diggable_block"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> SNIFFER_EGG_HATCH_BOOST  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "sniffer_egg_hatch_boost"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> TRAIL_RUINS_REPLACEABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "trail_ruins_replaceable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> REPLACEABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "replaceable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> ENCHANTMENT_POWER_PROVIDER  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantment_power_provider"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> ENCHANTMENT_POWER_TRANSMITTER  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantment_power_transmitter"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> MAINTAINS_FARMLAND  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "maintains_farmland"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> BLOCKS_WIND_CHARGE_EXPLOSIONS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "blocks_wind_charge_explosions"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> DOES_NOT_BLOCK_HOPPERS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "does_not_block_hoppers"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCBlockType> AIR  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "air"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

}
