package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.builder;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.gamefactory.recipe.RecipePredicate;
import de.verdox.mccreativelab.gamefactory.recipe.builder.RecipeBuilder;
import de.verdox.mccreativelab.gamefactory.recipe.standard.crafting.MCCShapedRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.crafting.MCCShapelessRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.MCCStonecutterRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.cooking.MCCBlastingRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.cooking.MCCCampfireRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.cooking.MCCFurnaceRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.cooking.MCCSmokingRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.smithing.MCCSmithingTransformRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.smithing.MCCSmithingTrimRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.NMSIngredient;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.crafting.NMSShapedRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.crafting.NMSShapelessRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single.NMSStonecutterRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single.cooking.NMSBlastingRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single.cooking.NMSCampfireRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single.cooking.NMSFurnaceRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single.cooking.NMSSmokingRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.smithing.NMSSmithingTransformRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.smithing.NMSSmithingTrimRecipe;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.PredicateIngredient;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.storage.PlayerDataStorage;

import java.util.Arrays;
import java.util.function.Predicate;

public class NMSRecipeBuilder implements RecipeBuilder {

    @Override
    public MCCIngredient createIngredient(MCCItemType... allowedTypes) {
        if (NMSPlatform.isMixinSupported) {
            return RecipeBuilder.super.createIngredient(allowedTypes);
        }

        Item[] items = new Item[allowedTypes.length];
        for (int i = 0; i < allowedTypes.length; i++) {
            MCCItemType allowedType = allowedTypes[i];
            if (!allowedType.isVanilla()) {
                allowedType.requireVanilla();
                break;
            }
            items[i] = MCCPlatform.getInstance().getConversionService().unwrap(allowedType, Item.class);
        }
        Ingredient ingredient = Ingredient.of(items);
        return new NMSIngredient(ingredient);
    }

    @Override
    public MCCIngredient createIngredient(Predicate<MCCItemStack> ingredientPredicate, MCCItemStack... recipeBookExamples) {
        MCCPlatform.getInstance().checkForMixins();
        Ingredient ingredient = Ingredient.of(Arrays.stream(recipeBookExamples).map(mccItemStack -> MCCPlatform.getInstance().getConversionService().unwrap(mccItemStack, ItemStack.class)).map(ItemStack::getItem));
        Object ingredientAsObject = ingredient;
        if (!(ingredientAsObject instanceof PredicateIngredient predicateIngredient)) {
            throw new IllegalStateException("Ingredients should implement " + PredicateIngredient.class + " through mixins.");
        }
        predicateIngredient.mcc_wrapper$setItemPredicate(new RecipePredicate(ingredientPredicate, Arrays.stream(recipeBookExamples).toList()));
        return MCCPlatform.getInstance().getConversionService().wrap(ingredient, MCCIngredient.class);
    }

    @Override
    public Standard.Crafting.Shaped createShapedRecipe() {
        return new Shaped();
    }

    @Override
    public Standard.Crafting.Shapeless createShapelessRecipe() {
        return new Shapeless();
    }

    @Override
    public Standard.Single.Cooking.Blasting createBlastingRecipe() {
        return new Blasting();
    }

    @Override
    public Standard.Single.Cooking.Furnace createFurnaceRecipe() {
        return new Furnace();
    }

    @Override
    public Standard.Single.Cooking.Smoker createSmokerRecipe() {
        return new Smoker();
    }

    @Override
    public Standard.Single.Cooking.Campfire createCampfireRecipe() {
        return new Campfire();
    }

    @Override
    public Standard.Single.Stonecutter createStonecutterRecipe() {
        return new Stonecutter();
    }

    @Override
    public Smithing.SmithingTransform createSmithingTransformRecipe() {
        return new SmithingTransform();
    }

    @Override
    public Smithing.SmithingTrim createSmithingTrimRecipe() {
        return new SmithingTrim();
    }

    public static class Shaped extends Standard.Crafting.Shaped {

        @Override
        public RecipeDraft<MCCShapedRecipe> build() {
            ShapedRecipe recipe = new ShapedRecipe(
                    group,
                    conversionService().unwrap(category, CraftingBookCategory.class),
                    NMSShapedRecipe.fromApi(recipePattern),
                    conversionService().unwrap(result, ItemStack.class),
                    showNotification
            );
            return new RecipeDraft<>(new NMSShapedRecipe(recipe, true));
        }
    }

    public static class Shapeless extends Standard.Crafting.Shapeless {

        @Override
        public RecipeDraft<MCCShapelessRecipe> build() {
            ShapelessRecipe recipe = new ShapelessRecipe(
                    group,
                    conversionService().unwrap(category, CraftingBookCategory.class),
                    conversionService().unwrap(result, ItemStack.class),
                    conversionService().unwrap(ingredients, new TypeToken<>() {})
            );
            return new RecipeDraft<>(new NMSShapelessRecipe(recipe, true));
        }
    }

    public static class Blasting extends Standard.Single.Cooking.Blasting {

        @Override
        public RecipeDraft<MCCBlastingRecipe> build() {
            BlastingRecipe recipe = new BlastingRecipe(
                    group,
                    conversionService().unwrap(category, CookingBookCategory.class),
                    conversionService().unwrap(ingredient, Ingredient.class),
                    conversionService().unwrap(result, ItemStack.class),
                    experience,
                    cookingTime
            );
            return new RecipeDraft<>(new NMSBlastingRecipe(recipe, true));
        }
    }

    public static class Furnace extends Standard.Single.Cooking.Furnace {

        @Override
        public RecipeDraft<MCCFurnaceRecipe> build() {
            SmeltingRecipe recipe = new SmeltingRecipe(
                    group,
                    conversionService().unwrap(category, CookingBookCategory.class),
                    conversionService().unwrap(ingredient, Ingredient.class),
                    conversionService().unwrap(result, ItemStack.class),
                    experience,
                    cookingTime
            );
            return new RecipeDraft<>(new NMSFurnaceRecipe(recipe, true));
        }
    }

    public static class Smoker extends Standard.Single.Cooking.Smoker {

        @Override
        public RecipeDraft<MCCSmokingRecipe> build() {
            SmokingRecipe recipe = new SmokingRecipe(
                    group,
                    conversionService().unwrap(category, CookingBookCategory.class),
                    conversionService().unwrap(ingredient, Ingredient.class),
                    conversionService().unwrap(result, ItemStack.class),
                    experience,
                    cookingTime
            );
            return new RecipeDraft<>(new NMSSmokingRecipe(recipe, true));
        }
    }

    public static class Campfire extends Standard.Single.Cooking.Campfire {

        @Override
        public RecipeDraft<MCCCampfireRecipe> build() {
            CampfireCookingRecipe recipe = new CampfireCookingRecipe(
                    group,
                    conversionService().unwrap(category, CookingBookCategory.class),
                    conversionService().unwrap(ingredient, Ingredient.class),
                    conversionService().unwrap(result, ItemStack.class),
                    experience,
                    cookingTime
            );
            return new RecipeDraft<>(new NMSCampfireRecipe(recipe, true));
        }
    }

    public static class Stonecutter extends Standard.Single.Cooking.Stonecutter {

        @Override
        public RecipeDraft<MCCStonecutterRecipe> build() {
            StonecutterRecipe recipe = new StonecutterRecipe(
                    group,
                    conversionService().unwrap(ingredient, Ingredient.class),
                    conversionService().unwrap(result, ItemStack.class)
            );
            return new RecipeDraft<>(new NMSStonecutterRecipe(recipe, true));
        }
    }

    public static class SmithingTransform extends Smithing.SmithingTransform {

        @Override
        public RecipeDraft<MCCSmithingTransformRecipe> build() {
            SmithingTransformRecipe recipe = new SmithingTransformRecipe(
                    conversionService().unwrap(templateIngredient, new TypeToken<>() {}),
                    conversionService().unwrap(baseIngredient, new TypeToken<>() {}),
                    conversionService().unwrap(additionIngredient, new TypeToken<>() {}),
                    conversionService().unwrap(result, ItemStack.class)
            );
            return new RecipeDraft<>(new NMSSmithingTransformRecipe(recipe, true));
        }
    }

    public static class SmithingTrim extends Smithing.SmithingTrim {

        @Override
        public RecipeDraft<MCCSmithingTrimRecipe> build() {
            SmithingTrimRecipe recipe = new SmithingTrimRecipe(
                    conversionService().unwrap(templateIngredient, new TypeToken<>() {}),
                    conversionService().unwrap(baseIngredient, new TypeToken<>() {}),
                    conversionService().unwrap(additionIngredient, new TypeToken<>() {})
            );
            return new RecipeDraft<>(new NMSSmithingTrimRecipe(recipe, true));
        }
    }
}
