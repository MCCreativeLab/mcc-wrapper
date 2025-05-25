package de.verdox.mccreativelab.impl.vanilla.gamefactory;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.gamefactory.MCCGameFactory;
import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStatePropertyFactory;
import de.verdox.mccreativelab.gamefactory.item.MCCCustomItemType;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.RecipePredicate;
import de.verdox.mccreativelab.gamefactory.recipe.builder.RecipeBuilder;
import de.verdox.mccreativelab.impl.vanilla.block.properties.NMSBlockStatePropertyFactory;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.builder.NMSRecipeBuilder;
import de.verdox.mccreativelab.impl.vanilla.item.NMSItemStack;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.impl.vanilla.registry.recipe.NMSRecipeManager;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.ItemStackWithCustomType;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.MutableRecipeManager;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.PredicateIngredient;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.typed.MCCDataComponentTypes;
import de.verdox.mccreativelab.wrapper.typed.MCCRegistries;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class NMSGameFactory implements MCCGameFactory {

    private final NMSPlatform platform;
    protected final MCCBlockStatePropertyFactory nmsBlockStatePropertyFactory;

    public NMSGameFactory(NMSPlatform platform) {
        this.platform = platform;
        this.nmsBlockStatePropertyFactory = new NMSBlockStatePropertyFactory();
    }

    private void convertVanillaIngredients() {
        if (!NMSPlatform.isMixinSupported) {
            return;
        }
        MCCRegistry<MCCRecipe> recipeMCCRegistry = new NMSRecipeManager(platform.getServer().getRecipeManager());

        int amountRegisteredRecipes = recipeMCCRegistry.keySet().size();
        int counter = 0;
        for (Key key : recipeMCCRegistry.keySet()) {
            MCCRecipe mccRecipe = recipeMCCRegistry.get(key);
            if (mccRecipe == null) {
                continue;
            }
            counter++;
            mccRecipe.getAllIngredientsWithoutContext()
                    .map(mccIngredient -> platform.getConversionService().unwrap(mccIngredient, Ingredient.class))
                    .forEach(ingredient -> {
                        if (!(((Object) ingredient) instanceof PredicateIngredient predicateIngredient)) {
                            return;
                        }
                        if (predicateIngredient.mcc_wrapper$getItemPredicate() != null) {
                            return;
                        }
                        Set<MCCItemType> typesOfIngredient = ingredient.items().map(Holder::value).map(item -> platform.getConversionService().wrap(item, MCCItemType.class)).collect(Collectors.toSet());
                        predicateIngredient.mcc_wrapper$setItemPredicate(new RecipePredicate(mccItemStack -> typesOfIngredient.contains(mccItemStack.getType()), typesOfIngredient.stream().map(MCCItemType::createItem).toList()));
                    });
        }
        LOGGER.info("Transformed " + counter + " / " + amountRegisteredRecipes + " recipes to predicate ingredients...");
    }

    @Override
    public void registerCustomRecipe(Key key, RecipeBuilder.RecipeDraft<?> recipeDraft) {
        MCCPlatform.getInstance().checkForMixins();
        MCCTypedKey<MCCRecipe> typedKey = MCCPlatform.getInstance().getTypedKeyFactory().getKey(key, MCCRegistries.RECIPE_REGISTRY.key());

        Recipe<?> recipe = MCCPlatform.getInstance().getConversionService().unwrap(recipeDraft.getDraft(), Recipe.class);
        ResourceKey<Recipe<?>> recipeResourceKey = MCCPlatform.getInstance().getConversionService().unwrap(typedKey, new TypeToken<>() {});

        RecipeHolder<?> recipeHolder = new RecipeHolder<>(recipeResourceKey, recipe);

        RecipeManager recipeManager = platform.getServer().getRecipeManager();
        if (recipeManager instanceof MutableRecipeManager mutableRecipeManager) {
            LOGGER.info("Adding custom recipe using mixins: " + recipeHolder);
            mutableRecipeManager.mcc_wrapper$addCustomRecipe(recipeHolder);
        }
    }

    @Override
    public MCCItemStack createItem(MCCCustomItemType customItemType) {
        MCCPlatform.getInstance().checkForMixins();
        MCCItemStack stack = customItemType.getItemTextureData().createItem();
        var standardComponents = customItemType.getItemStandardComponentMap();
        for (MCCDataComponentType<?> mccDataComponentType : customItemType.getItemStandardComponentMap()) {
            stack.components().copyFrom(mccDataComponentType, standardComponents);
        }
        ItemStack nmsStack = platform.getConversionService().unwrap(stack, ItemStack.class);
        if (!(((Object) nmsStack) instanceof ItemStackWithCustomType itemStackWithCustomType)) {
            throw new IllegalStateException("The ItemStack class should extend " + ItemStackWithCustomType.class.getName());
        }
        itemStackWithCustomType.setMcc_wrapper$customItemType(customItemType);
        return stack;
    }

    @Override
    public Optional<MCCCustomItemType> extract(MCCItemStack mccItemStack) {
        if(!NMSPlatform.isMixinSupported) {
            return Optional.empty();
        }
        ItemStack stack = ((NMSItemStack) mccItemStack).getHandle();
        if (!(((Object) stack) instanceof ItemStackWithCustomType itemStackWithCustomType)) {
            throw new IllegalStateException("The ItemStack class should extend " + ItemStackWithCustomType.class.getName());
        }
        return Optional.ofNullable(itemStackWithCustomType.getMcc_wrapper$customItemType());
    }

    @Override
    public MCCBlockStatePropertyFactory getBlockStatePropertyFactory() {
        return nmsBlockStatePropertyFactory;
    }

    @Override
    public RecipeBuilder createRecipe() {
        return new NMSRecipeBuilder();
    }

    @Override
    public void loadAfterBootstrap() {
        convertVanillaIngredients();
    }
}
