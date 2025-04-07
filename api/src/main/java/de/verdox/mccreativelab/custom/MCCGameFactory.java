package de.verdox.mccreativelab.custom;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.custom.block.MCCCustomBlockType;
import de.verdox.mccreativelab.custom.item.MCCCustomItemType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.platform.factory.TypedKeyFactory;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.types.MCCAttribute;
import net.kyori.adventure.key.Key;

/**
 * Entrypoint for the custom data api used to inject custom types into the platform (e.g. blocks, items, attributes, etc...)
 * For now the game factory is only used to inject built in data types. Data driven types are injected via data packs.
 * As this api grows we will implement logic to inject data driven objects aswell.
 */
public interface MCCGameFactory {

    // A registry holding custom attributes
    MCCTypedKey<MCCRegistry<MCCAttribute>> ATTRIBUTE_REGISTRY = registry("attributes", new TypeToken<>() {});
    // A registry holding custom block types
    MCCTypedKey<MCCRegistry<MCCCustomBlockType>> BLOCK_REGISTRY = registry("attributes", new TypeToken<>() {});
    // A registry holding custom item types
    MCCTypedKey<MCCRegistry<MCCCustomItemType>> ITEM_REGISTRY = registry("attributes", new TypeToken<>() {});
    // A registry holding custom poi types
    MCCTypedKey<MCCRegistry<MCCAttribute>> POI_TYPE_REGISTRY = registry("attributes", new TypeToken<>() {});
    // A registry holding custom villager professions
    MCCTypedKey<MCCRegistry<MCCAttribute>> VILLAGER_PROFESSION_REGISTRY = registry("attributes", new TypeToken<>() {});

    static <T> MCCTypedKey<T> registry(String registryKey, TypeToken<T> type) {
        return MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("mccreativelab", registryKey), TypedKeyFactory.REGISTRY_OF_REGISTRIES, type);
    }
}
