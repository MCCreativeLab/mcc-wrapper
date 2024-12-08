package de.verdox.mccreativelab.wrapper.typed;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.world.level.biome.MCCBiome;
import net.kyori.adventure.key.Key;

public class MCCBiomes  {

	public static final Key VANILLA_REGISTRY_KEY  = Key.key("minecraft", "worldgen/biome");

	public static final MCCTypedKey<MCCBiome> THE_VOID  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "the_void"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> PLAINS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "plains"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> SUNFLOWER_PLAINS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "sunflower_plains"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> SNOWY_PLAINS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "snowy_plains"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> ICE_SPIKES  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "ice_spikes"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> DESERT  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "desert"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> SWAMP  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "swamp"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> MANGROVE_SWAMP  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "mangrove_swamp"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> FOREST  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "forest"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> FLOWER_FOREST  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "flower_forest"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> BIRCH_FOREST  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "birch_forest"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> DARK_FOREST  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "dark_forest"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> OLD_GROWTH_BIRCH_FOREST  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "old_growth_birch_forest"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> OLD_GROWTH_PINE_TAIGA  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "old_growth_pine_taiga"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> OLD_GROWTH_SPRUCE_TAIGA  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "old_growth_spruce_taiga"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> TAIGA  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "taiga"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> SNOWY_TAIGA  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "snowy_taiga"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> SAVANNA  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "savanna"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> SAVANNA_PLATEAU  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "savanna_plateau"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> WINDSWEPT_HILLS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "windswept_hills"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> WINDSWEPT_GRAVELLY_HILLS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "windswept_gravelly_hills"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> WINDSWEPT_FOREST  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "windswept_forest"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> WINDSWEPT_SAVANNA  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "windswept_savanna"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> JUNGLE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "jungle"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> SPARSE_JUNGLE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "sparse_jungle"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> BAMBOO_JUNGLE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "bamboo_jungle"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> BADLANDS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "badlands"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> ERODED_BADLANDS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "eroded_badlands"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> WOODED_BADLANDS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "wooded_badlands"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> MEADOW  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "meadow"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> CHERRY_GROVE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "cherry_grove"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> GROVE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "grove"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> SNOWY_SLOPES  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "snowy_slopes"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> FROZEN_PEAKS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "frozen_peaks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> JAGGED_PEAKS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "jagged_peaks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> STONY_PEAKS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "stony_peaks"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> RIVER  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "river"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> FROZEN_RIVER  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "frozen_river"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> BEACH  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "beach"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> SNOWY_BEACH  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "snowy_beach"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> STONY_SHORE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "stony_shore"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> WARM_OCEAN  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "warm_ocean"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> LUKEWARM_OCEAN  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "lukewarm_ocean"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> DEEP_LUKEWARM_OCEAN  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "deep_lukewarm_ocean"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> OCEAN  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "ocean"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> DEEP_OCEAN  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "deep_ocean"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> COLD_OCEAN  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "cold_ocean"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> DEEP_COLD_OCEAN  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "deep_cold_ocean"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> FROZEN_OCEAN  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "frozen_ocean"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> DEEP_FROZEN_OCEAN  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "deep_frozen_ocean"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> MUSHROOM_FIELDS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "mushroom_fields"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> DRIPSTONE_CAVES  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "dripstone_caves"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> LUSH_CAVES  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "lush_caves"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> DEEP_DARK  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "deep_dark"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> NETHER_WASTES  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "nether_wastes"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> WARPED_FOREST  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "warped_forest"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> CRIMSON_FOREST  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "crimson_forest"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> SOUL_SAND_VALLEY  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "soul_sand_valley"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> BASALT_DELTAS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "basalt_deltas"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> THE_END  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "the_end"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> END_HIGHLANDS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "end_highlands"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> END_MIDLANDS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "end_midlands"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> SMALL_END_ISLANDS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "small_end_islands"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

	public static final MCCTypedKey<MCCBiome> END_BARRENS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "end_barrens"), VANILLA_REGISTRY_KEY, new TypeToken<>(){});

}
