package de.verdox.mccreativelab.wrapper.typed;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.entity.types.MCCDisplayEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCItemEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCMarkerEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import net.kyori.adventure.key.Key;

/**
 * A class with references to all vanilla entity types
 */
public interface MCCEntityTypes {
    Key VANILLA_REGISTRY_KEY  = Key.key("minecraft", "entity_type");

    MCCReference<MCCEntityType<MCCItemEntity>> ITEM = get("item", new TypeToken<>() {});
    MCCReference<MCCEntityType<MCCMarkerEntity>> MARKER = get("marker", new TypeToken<>() {});
    MCCReference<MCCEntityType<MCCPlayer>> PLAYER = get("player", new TypeToken<>() {});

    MCCReference<MCCEntityType<MCCDisplayEntity.Item>> ITEM_DISPLAY = get("item_display", new TypeToken<>() {});
    MCCReference<MCCEntityType<MCCDisplayEntity.Block>> BLOCK_DISPLAY = get("block_display", new TypeToken<>() {});
    MCCReference<MCCEntityType<MCCDisplayEntity.Text>> TEXT_DISPLAY = get("text_display", new TypeToken<>() {});

    private static <T extends MCCEntity> MCCReference<MCCEntityType<T>> get(String key, TypeToken<MCCEntityType<T>> type) {
        return MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("minecraft", key), VANILLA_REGISTRY_KEY, type).getAsReference();
    }
}
