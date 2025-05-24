package de.verdox.mccreativelab.gamefactory;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.gamefactory.block.MCCBlockStateMapping;
import de.verdox.mccreativelab.gamefactory.block.MCCCustomBlockState;
import de.verdox.mccreativelab.gamefactory.block.MCCCustomBlockType;
import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStatePropertyFactory;
import de.verdox.mccreativelab.gamefactory.item.ItemVisuals;
import de.verdox.mccreativelab.gamefactory.item.MCCCustomItemType;
import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.builder.RecipeBuilder;
import de.verdox.mccreativelab.generator.resourcepack.types.ItemTextureData;
import de.verdox.mccreativelab.platform.PlatformResourcePack;
import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.annotations.MCCRequireMixin;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.typed.MCCDataComponentTypes;
import de.verdox.mccreativelab.wrapper.typed.MCCItems;
import de.verdox.mccreativelab.wrapper.types.MCCAttribute;
import de.verdox.mccreativelab.wrapper.types.MCCPoiType;
import de.verdox.mccreativelab.wrapper.types.MCCVillagerProfession;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Entrypoint for the custom data api used to inject custom types into the platform (e.g. blocks, items, attributes, etc...)
 * For now the game factory is only used to inject built in data types. Data driven types are injected via data packs.
 * As this api grows we will implement logic to inject data driven objects aswell.
 */
public interface MCCGameFactory {

    // A registry holding custom attributes
    MCCTypedKey<MCCRegistry<MCCAttribute>> ATTRIBUTE_REGISTRY = registry("attribute", new TypeToken<>() {});
    // A registry holding custom block types
    MCCTypedKey<MCCRegistry<MCCCustomBlockType>> BLOCK_REGISTRY = registry("block", new TypeToken<>() {});
    // A registry holding custom item types
    MCCTypedKey<MCCRegistry<MCCCustomItemType>> ITEM_REGISTRY = registry("item", new TypeToken<>() {});
    // A registry holding custom recipes
    MCCTypedKey<MCCRegistry<MCCRecipe>> RECIPE_REGISTRY = registry("recipe", new TypeToken<>() {});
    // A registry holding custom poi types
    MCCTypedKey<MCCRegistry<MCCPoiType>> POI_TYPE_REGISTRY = registry("poi_type", new TypeToken<>() {});
    // A registry holding custom villager professions
    MCCTypedKey<MCCRegistry<MCCVillagerProfession>> VILLAGER_PROFESSION_REGISTRY = registry("profession", new TypeToken<>() {});

    /**
     * Generic register function
     */
    default <T extends MCCKeyedWrapper> void registerCustom(MCCTypedKey<MCCRegistry<T>> customRegistry, Key key, T customType) {
/*        if (customType.isVanilla()) {
            throw new IllegalArgumentException("Cannot register data that is marked as vanilla: " + key + ", " + customType);
        }*/
        //TODO: Does not work for recipes.
/*        if (!customRegistry.key().equals(customType.getRegistryKey())) {
            throw new IllegalArgumentException("Registry mismatch! The custom type belongs to the registry " + customType.getRegistryKey() + " but you want to register it to the custom registry " + customRegistry.key());
        }*/
        MCCTypedKey<T> typedKey = MCCPlatform.getInstance().getTypedKeyFactory().getKey(key, customType.getRegistryKey());
        customRegistry.getAsReference().get().register(typedKey, customType);
    }

    void registerCustomRecipe(Key key, RecipeBuilder.RecipeDraft<?> recipeDraft);

    /**
     * Used to register a custom item type
     */
    default void registerCustomItemType(Key key, MCCCustomItemType customItemType, ItemVisuals itemVisuals) {
        MCCPlatform.getInstance().checkForMixins();
        registerCustom(ITEM_REGISTRY, key, customItemType);
        PlatformResourcePack.INSTANCE.get().register(itemVisuals.asTextureData(key));
    }

    /**
     * Used to register a custom block type
     */
    @ApiStatus.Experimental
    default void registerCustomBlock(Key key, MCCCustomBlockType customBlockType) {
        MCCPlatform.getInstance().checkForMixins();
        registerCustom(BLOCK_REGISTRY, key, customBlockType);

        for (MCCBlockState customState : customBlockType.getAllBlockStates()) {
            MCCBlockStateMapping.getInstance().requireMapping((MCCCustomBlockState) customState, MCCBlockStateMapping.MappingType.NOTE_BLOCK);
        }
    }

    /**
     * Creates a registry for custom types
     */
    static <T> MCCTypedKey<MCCRegistry<T>> registry(String registryKey, TypeToken<T> type) {
        return MCCPlatform.getInstance().getRegistryStorage().createMinecraftRegistry(Key.key("mcc", registryKey), type).unwrapKey().get();
    }

    /**
     * Instantiates a custom item type with all valid data components for identification
     * @param mccItemType the custom item type
     */
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

    /**
     * Used to extract a potential custom item type from an item stack
     * @param mccItemStack the item stack
     */
    Optional<MCCCustomItemType> extract(MCCItemStack mccItemStack);

    /**
     * Returns the block state property factory
     * @return the factory
     */
    MCCBlockStatePropertyFactory getBlockStatePropertyFactory();

    /**
     * Creates a recipe builder
     */
    RecipeBuilder createRecipe();
}
