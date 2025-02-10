package de.verdox.mccreativelab.wrapper.typed;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCTag;
import net.kyori.adventure.key.Key;

public class MCCItemTags  {

	public static final Key VANILLA_REGISTRY_KEY  = Key.key("minecraft", "item");

	public static final MCCTag<MCCItemType> WOOL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wool"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> PLANKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "planks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> STONE_BRICKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "stone_bricks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WOODEN_BUTTONS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_buttons"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> STONE_BUTTONS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "stone_buttons"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> BUTTONS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "buttons"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WOOL_CARPETS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wool_carpets"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WOODEN_DOORS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_doors"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WOODEN_STAIRS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_stairs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WOODEN_SLABS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_slabs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WOODEN_FENCES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_fences"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> FENCE_GATES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "fence_gates"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WOODEN_PRESSURE_PLATES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_pressure_plates"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WOODEN_TRAPDOORS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wooden_trapdoors"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> DOORS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "doors"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SAPLINGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "saplings"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> LOGS_THAT_BURN  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "logs_that_burn"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> DARK_OAK_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "dark_oak_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> OAK_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "oak_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> BIRCH_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "birch_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> ACACIA_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "acacia_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CHERRY_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "cherry_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> JUNGLE_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "jungle_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SPRUCE_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "spruce_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> MANGROVE_LOGS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "mangrove_logs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CRIMSON_STEMS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "crimson_stems"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WARPED_STEMS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "warped_stems"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> BAMBOO_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "bamboo_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WART_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wart_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> BANNERS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "banners"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SAND  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "sand"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SMELTS_TO_GLASS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "smelts_to_glass"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> STAIRS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "stairs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SLABS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "slabs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WALLS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "walls"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> ANVIL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "anvil"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> RAILS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "rails"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> LEAVES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "leaves"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> TRAPDOORS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "trapdoors"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SMALL_FLOWERS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "small_flowers"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> BEDS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "beds"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> FENCES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "fences"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> TALL_FLOWERS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "tall_flowers"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> FLOWERS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "flowers"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> PIGLIN_REPELLENTS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "piglin_repellents"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> PIGLIN_LOVED  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "piglin_loved"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> IGNORED_BY_PIGLIN_BABIES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "ignored_by_piglin_babies"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> MEAT  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "meat"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SNIFFER_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "sniffer_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> PIGLIN_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "piglin_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> FOX_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "fox_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> COW_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "cow_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> GOAT_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "goat_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SHEEP_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "sheep_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WOLF_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "wolf_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CAT_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "cat_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> HORSE_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "horse_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> HORSE_TEMPT_ITEMS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "horse_tempt_items"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CAMEL_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "camel_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> ARMADILLO_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "armadillo_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> BEE_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "bee_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CHICKEN_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "chicken_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> FROG_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "frog_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> HOGLIN_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "hoglin_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> LLAMA_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "llama_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> LLAMA_TEMPT_ITEMS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "llama_tempt_items"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> OCELOT_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "ocelot_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> PANDA_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "panda_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> PIG_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "pig_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> RABBIT_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "rabbit_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> STRIDER_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "strider_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> STRIDER_TEMPT_ITEMS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "strider_tempt_items"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> TURTLE_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "turtle_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> PARROT_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "parrot_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> PARROT_POISONOUS_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "parrot_poisonous_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> AXOLOTL_FOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "axolotl_food"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> GOLD_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "gold_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> IRON_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "iron_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> DIAMOND_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "diamond_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> REDSTONE_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "redstone_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> LAPIS_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "lapis_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> COAL_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "coal_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> EMERALD_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "emerald_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> COPPER_ORES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "copper_ores"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> NON_FLAMMABLE_WOOD  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "non_flammable_wood"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SOUL_FIRE_BASE_BLOCKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "soul_fire_base_blocks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CANDLES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "candles"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> DIRT  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "dirt"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> TERRACOTTA  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "terracotta"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> COMPLETES_FIND_TREE_TUTORIAL  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "completes_find_tree_tutorial"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> BOATS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "boats"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CHEST_BOATS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "chest_boats"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> FISHES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "fishes"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SIGNS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "signs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CREEPER_DROP_MUSIC_DISCS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "creeper_drop_music_discs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> COALS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "coals"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> ARROWS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "arrows"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> LECTERN_BOOKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "lectern_books"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> BOOKSHELF_BOOKS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "bookshelf_books"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> BEACON_PAYMENT_ITEMS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "beacon_payment_items"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> STONE_TOOL_MATERIALS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "stone_tool_materials"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> STONE_CRAFTING_MATERIALS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "stone_crafting_materials"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> FREEZE_IMMUNE_WEARABLES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "freeze_immune_wearables"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> DAMPENS_VIBRATIONS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "dampens_vibrations"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CLUSTER_MAX_HARVESTABLES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "cluster_max_harvestables"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> COMPASSES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "compasses"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> HANGING_SIGNS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "hanging_signs"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CREEPER_IGNITERS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "creeper_igniters"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> NOTE_BLOCK_TOP_INSTRUMENTS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "noteblock_top_instruments"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> FOOT_ARMOR  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "foot_armor"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> LEG_ARMOR  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "leg_armor"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CHEST_ARMOR  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "chest_armor"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> HEAD_ARMOR  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "head_armor"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SKULLS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "skulls"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> TRIMMABLE_ARMOR  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "trimmable_armor"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> TRIM_MATERIALS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "trim_materials"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> TRIM_TEMPLATES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "trim_templates"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> DECORATED_POT_SHERDS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "decorated_pot_sherds"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> DECORATED_POT_INGREDIENTS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "decorated_pot_ingredients"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SWORDS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "swords"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> AXES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "axes"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> HOES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "hoes"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> PICKAXES  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "pickaxes"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SHOVELS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "shovels"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> BREAKS_DECORATED_POTS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "breaks_decorated_pots"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> VILLAGER_PLANTABLE_SEEDS  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "villager_plantable_seeds"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> DYEABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "dyeable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> FOOT_ARMOR_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/foot_armor"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> LEG_ARMOR_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/leg_armor"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CHEST_ARMOR_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/chest_armor"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> HEAD_ARMOR_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/head_armor"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> ARMOR_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/armor"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SWORD_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/sword"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> FIRE_ASPECT_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/fire_aspect"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> SHARP_WEAPON_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/sharp_weapon"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> WEAPON_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/weapon"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> MINING_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/mining"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> MINING_LOOT_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/mining_loot"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> FISHING_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/fishing"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> TRIDENT_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/trident"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> DURABILITY_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/durability"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> BOW_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/bow"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> EQUIPPABLE_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/equippable"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> CROSSBOW_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/crossbow"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> VANISHING_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/vanishing"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTag<MCCItemType> MACE_ENCHANTABLE  = MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key("minecraft", "enchantable/mace"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

}
