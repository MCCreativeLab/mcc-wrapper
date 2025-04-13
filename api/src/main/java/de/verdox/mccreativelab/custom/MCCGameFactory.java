package de.verdox.mccreativelab.custom;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.custom.block.MCCBlockStateMapping;
import de.verdox.mccreativelab.custom.block.MCCCustomBlockState;
import de.verdox.mccreativelab.custom.block.MCCCustomBlockType;
import de.verdox.mccreativelab.custom.item.MCCCustomItemType;
import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.platform.factory.TypedKeyFactory;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.typed.MCCDataComponentTypes;
import de.verdox.mccreativelab.wrapper.typed.MCCItems;
import de.verdox.mccreativelab.wrapper.types.MCCAttribute;
import net.kyori.adventure.key.Key;

import java.util.Optional;

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

    default <T extends MCCKeyedWrapper> void registerCustom(MCCTypedKey<MCCRegistry<T>> customRegistry, Key key, T customType) {
        if (customType.isVanilla()) {
            throw new IllegalArgumentException("Cannot register data that is marked as vanilla");
        }
        if (customRegistry.key().equals(customType.getRegistryKey())) {
            throw new IllegalArgumentException("Registry mismatch! The custom type belongs to the registry " + customType.getRegistryKey() + " but you want to register it to the custom registry " + customRegistry);
        }
        MCCTypedKey<T> typedKey = MCCPlatform.getInstance().getTypedKeyFactory().getKey(key, customType.getRegistryKey());
        customRegistry.getAsReference().get().register(typedKey, customType);
    }

    default void registerCustomItemType(Key key, MCCCustomItemType customItemType) {
        registerCustom(ITEM_REGISTRY, key, customItemType);
    }

    default void registerCustomBlock(Key key, MCCCustomBlockType customBlockType) {
        registerCustom(BLOCK_REGISTRY, key, customBlockType);

        for (MCCBlockState customState : customBlockType.getAllBlockStates()) {
            MCCBlockStateMapping.getInstance().requireMapping((MCCCustomBlockState) customState, MCCBlockStateMapping.MappingType.NOTE_BLOCK);
        }
    }

    static <T> MCCTypedKey<T> registry(String registryKey, TypeToken<T> type) {
        return MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("mccreativelab", registryKey), TypedKeyFactory.REGISTRY_OF_REGISTRIES, type);
    }

    default MCCItemStack instantiateCustomItem(MCCCustomItemType mccItemType) {
        if (mccItemType.isVanilla()) {
            throw new IllegalArgumentException("Please provide a custom item type");
        }
        MCCItemStack stack = MCCItems.STICK.get().createItem();
        stack.components().set(MCCDataComponentTypes.ITEM_MODEL.get(), mccItemType.key());
        var standardComponents = mccItemType.getItemStandardComponentMap();
        for (MCCDataComponentType<?> mccDataComponentType : mccItemType.getItemStandardComponentMap()) {
            stack.components().copyFrom(mccDataComponentType, standardComponents);
        }
        return stack;
    }

    Optional<MCCCustomItemType> extract(MCCItemStack mccItemStack);
}
