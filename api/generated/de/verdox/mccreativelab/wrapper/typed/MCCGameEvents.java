package de.verdox.mccreativelab.wrapper.typed;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.types.MCCGameEvent;
import java.lang.Object;
import net.kyori.adventure.key.Key;
public class MCCGameEvents  {
	public static final Key VANILLA_REGISTRY_KEY  = Key.key("minecraft", "game_event");

	public static final MCCReference<MCCGameEvent> BLOCK_ACTIVATE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_activate"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> BLOCK_ATTACH  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_attach"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> BLOCK_CHANGE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_change"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> BLOCK_CLOSE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_close"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> BLOCK_DEACTIVATE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_deactivate"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> BLOCK_DESTROY  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_destroy"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> BLOCK_DETACH  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_detach"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> BLOCK_OPEN  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_open"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> BLOCK_PLACE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "block_place"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> CONTAINER_CLOSE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "container_close"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> CONTAINER_OPEN  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "container_open"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> DRINK  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "drink"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> EAT  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "eat"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> ELYTRA_GLIDE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "elytra_glide"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> ENTITY_DAMAGE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "entity_damage"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> ENTITY_DIE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "entity_die"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> ENTITY_DISMOUNT  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "entity_dismount"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> ENTITY_INTERACT  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "entity_interact"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> ENTITY_MOUNT  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "entity_mount"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> ENTITY_PLACE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "entity_place"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> ENTITY_ACTION  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "entity_action"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> EQUIP  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "equip"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> EXPLODE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "explode"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> FLAP  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "flap"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> FLUID_PICKUP  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "fluid_pickup"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> FLUID_PLACE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "fluid_place"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> HIT_GROUND  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "hit_ground"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> INSTRUMENT_PLAY  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "instrument_play"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> ITEM_INTERACT_FINISH  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "item_interact_finish"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> ITEM_INTERACT_START  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "item_interact_start"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> JUKEBOX_PLAY  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "jukebox_play"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> JUKEBOX_STOP_PLAY  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "jukebox_stop_play"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> LIGHTNING_STRIKE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "lightning_strike"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> NOTE_BLOCK_PLAY  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "note_block_play"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> PRIME_FUSE  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "prime_fuse"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> PROJECTILE_LAND  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "projectile_land"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> PROJECTILE_SHOOT  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "projectile_shoot"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> SCULK_SENSOR_TENDRILS_CLICKING  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "sculk_sensor_tendrils_clicking"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> SHEAR  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "shear"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> SHRIEK  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "shriek"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> SPLASH  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "splash"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> STEP  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "step"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> SWIM  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "swim"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> TELEPORT  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "teleport"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> UNEQUIP  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "unequip"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_1  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_1"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_2  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_2"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_3  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_3"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_4  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_4"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_5  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_5"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_6  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_6"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_7  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_7"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_8  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_8"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_9  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_9"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_10  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_10"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_11  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_11"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_12  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_12"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_13  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_13"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_14  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_14"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();

	public static final MCCReference<MCCGameEvent> RESONATE_15  = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", "resonate_15"), VANILLA_REGISTRY_KEY, new TypeToken<MCCGameEvent>(){}).getAsReference();
}