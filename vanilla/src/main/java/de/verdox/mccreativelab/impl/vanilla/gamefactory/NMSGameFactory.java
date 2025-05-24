package de.verdox.mccreativelab.impl.vanilla.gamefactory;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.gamefactory.MCCGameFactory;
import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStatePropertyFactory;
import de.verdox.mccreativelab.gamefactory.item.MCCCustomItemType;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.builder.RecipeBuilder;
import de.verdox.mccreativelab.impl.vanilla.block.properties.NMSBlockStatePropertyFactory;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.builder.NMSRecipeBuilder;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.MutableRecipeManager;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.typed.MCCDataComponentTypes;
import de.verdox.mccreativelab.wrapper.typed.MCCRegistries;
import net.kyori.adventure.key.Key;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Optional;

public class NMSGameFactory implements MCCGameFactory {

    private final NMSPlatform platform;
    protected final MCCBlockStatePropertyFactory nmsBlockStatePropertyFactory;

    public NMSGameFactory(NMSPlatform platform) {
        this.platform = platform;
        this.nmsBlockStatePropertyFactory = new NMSBlockStatePropertyFactory();
    }

    @Override
    public void registerCustomRecipe(Key key, RecipeBuilder.RecipeDraft<?> recipeDraft) {
        MCCPlatform.getInstance().checkForMixins();
        registerCustom(RECIPE_REGISTRY, key, recipeDraft.getDraft());
        MCCTypedKey<MCCRecipe> typedKey = MCCPlatform.getInstance().getTypedKeyFactory().getKey(key, MCCRegistries.RECIPE_REGISTRY.getRegistryKey());

        Recipe<?> recipe = MCCPlatform.getInstance().getConversionService().unwrap(recipeDraft.getDraft(), Recipe.class);
        ResourceKey<Recipe<?>> recipeResourceKey = MCCPlatform.getInstance().getConversionService().unwrap(typedKey, new TypeToken<>() {});

        RecipeHolder<?> recipeHolder = new RecipeHolder<>(recipeResourceKey, recipe);

        RecipeManager recipeManager = platform.getServer().getRecipeManager();
        if (recipeManager instanceof MutableRecipeManager mutableRecipeManager) {
            mutableRecipeManager.mcc_wrapper$addCustomRecipe(recipeHolder);
        }
    }

    @Override
    public Optional<MCCCustomItemType> extract(MCCItemStack mccItemStack) {
        Key itemModelKey = mccItemStack.components().get(MCCDataComponentTypes.ITEM_MODEL.get());
        if (itemModelKey == null) {
            return Optional.empty();
        } else if (itemModelKey.namespace().equals("minecraft")) {
            return Optional.empty();
        }
        return MCCGameFactory.ITEM_REGISTRY.get().getOptional(itemModelKey);
    }

    @Override
    public MCCBlockStatePropertyFactory getBlockStatePropertyFactory() {
        return nmsBlockStatePropertyFactory;
    }

    @Override
    public RecipeBuilder createRecipe() {
        return new NMSRecipeBuilder();
    }
}
