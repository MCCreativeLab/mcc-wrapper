package de.verdox.mccreativelab.wrapper.typed;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import com.google.common.reflect.TypeToken;

import de.verdox.mccreativelab.wrapper.types.MCCAttribute;
import net.kyori.adventure.key.Key;
public class MCCAttributes  {
	public static final Key VANILLA_REGISTRY_KEY  = Key.key("minecraft", "attribute");

	public static final MCCReference<MCCAttribute> ARMOR  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "armor"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> ARMOR_TOUGHNESS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "armor_toughness"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> ATTACK_DAMAGE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "attack_damage"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> ATTACK_KNOCKBACK  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "attack_knockback"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> ATTACK_SPEED  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "attack_speed"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> BLOCK_BREAK_SPEED  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_break_speed"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> BLOCK_INTERACTION_RANGE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_interaction_range"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> BURNING_TIME  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "burning_time"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> EXPLOSION_KNOCKBACK_RESISTANCE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "explosion_knockback_resistance"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> ENTITY_INTERACTION_RANGE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "entity_interaction_range"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> FALL_DAMAGE_MULTIPLIER  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "fall_damage_multiplier"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> FLYING_SPEED  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "flying_speed"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> FOLLOW_RANGE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "follow_range"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> GRAVITY  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "gravity"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> JUMP_STRENGTH  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "jump_strength"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> KNOCKBACK_RESISTANCE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "knockback_resistance"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> LUCK  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "luck"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> MAX_ABSORPTION  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "max_absorption"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> MAX_HEALTH  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "max_health"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> MINING_EFFICIENCY  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "mining_efficiency"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> MOVEMENT_EFFICIENCY  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "movement_efficiency"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> MOVEMENT_SPEED  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "movement_speed"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> OXYGEN_BONUS  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "oxygen_bonus"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> SAFE_FALL_DISTANCE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "safe_fall_distance"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> SCALE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "scale"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> SNEAKING_SPEED  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "sneaking_speed"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> SPAWN_REINFORCEMENTS_CHANCE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "spawn_reinforcements"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> STEP_HEIGHT  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "step_height"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> SUBMERGED_MINING_SPEED  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "submerged_mining_speed"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> SWEEPING_DAMAGE_RATIO  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "sweeping_damage_ratio"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> TEMPT_RANGE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "tempt_range"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();

	public static final MCCReference<MCCAttribute> WATER_MOVEMENT_EFFICIENCY  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "water_movement_efficiency"), VANILLA_REGISTRY_KEY, new TypeToken<MCCAttribute>(){}).getAsReference();
}