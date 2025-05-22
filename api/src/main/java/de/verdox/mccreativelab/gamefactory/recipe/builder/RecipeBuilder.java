package de.verdox.mccreativelab.gamefactory.recipe.builder;

import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.gamefactory.recipe.MCCCookingBookCategory;
import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipeBookCategory;
import de.verdox.mccreativelab.gamefactory.recipe.standard.MCCStandardRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.crafting.MCCCraftingRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.crafting.MCCShapedRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.crafting.MCCShapelessRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.MCCSingleItemRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.MCCStonecutterRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.cooking.*;
import de.verdox.mccreativelab.gamefactory.recipe.standard.smithing.MCCSmithingRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.smithing.MCCSmithingTransformRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.standard.smithing.MCCSmithingTrimRecipe;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface RecipeBuilder {
    Standard.Crafting.Shaped createShapedRecipe();

    Standard.Crafting.Shapeless createShapelessRecipe();

    Standard.Single.Cooking.Blasting createBlastingRecipe();

    Standard.Single.Cooking.Furnace createFurnaceRecipe();

    Standard.Single.Cooking.Smoker createSmokerRecipe();

    Standard.Single.Cooking.Campfire createCampfireRecipe();

    Standard.Single.Stonecutter createStonecutterRecipe();

    Smithing.SmithingTransform createSmithingTransformRecipe();

    Smithing.SmithingTrim createSmithingTrimRecipe();

    class RecipeDraft<T extends MCCRecipe> {
        private final T recipe;

        public RecipeDraft(T recipe) {
            this.recipe = recipe;
        }

        T getDraft() {
            return recipe;
        }
    }

    // TODO: Have no recipe book
    abstract class Smithing<T extends MCCSmithingRecipe, SELF extends Smithing<T, SELF>> implements RecipeConstructor<T, SELF> {
        protected Optional<MCCIngredient> templateIngredient = Optional.empty();
        protected Optional<MCCIngredient> baseIngredient = Optional.empty();
        protected Optional<MCCIngredient> additionIngredient = Optional.empty();

        public Smithing<T, SELF> templateIngredient(Optional<MCCIngredient> templateIngredient) {
            this.templateIngredient = templateIngredient;
            return this;
        }

        public Smithing<T, SELF> baseIngredient(Optional<MCCIngredient> baseIngredient) {
            this.baseIngredient = baseIngredient;
            return this;
        }

        public Smithing<T, SELF> additionIngredient(Optional<MCCIngredient> additionIngredient) {
            this.additionIngredient = additionIngredient;
            return this;
        }

        public static abstract class SmithingTransform extends Smithing<MCCSmithingTransformRecipe, SmithingTransform> {
            protected MCCItemStack result;

            public SmithingTransform result(MCCItemStack result) {
                this.result = result;
                return this;
            }
        }

        public static abstract class SmithingTrim extends Smithing<MCCSmithingTrimRecipe, SmithingTrim> {}
    }

    abstract class Standard<T extends MCCStandardRecipe, SELF extends Standard<T, SELF, CATEGORY>, CATEGORY> implements RecipeConstructor<T, SELF> {
        protected MCCItemStack result;
        protected String group = "";
        protected CATEGORY category;

        public SELF result(MCCItemStack result) {
            this.result = result;
            return asSelf();
        }

        public SELF category(CATEGORY category) {
            this.category = category;
            return asSelf();
        }

        public SELF group(String group) {
            this.group = group;
            return asSelf();
        }

        public static abstract class Crafting<T extends MCCCraftingRecipe, SELF extends Crafting<T, SELF>> extends Standard<T, SELF, MCCRecipeBookCategory> {
            public static abstract class Shaped extends Crafting<MCCShapedRecipe, Shaped> {
                protected boolean showNotification = true;
                protected MCCShapedRecipe.RecipePattern recipePattern;

                public Shaped showNotification(boolean showNotification) {
                    this.showNotification = showNotification;
                    return asSelf();
                }

                public Shaped pattern(MCCShapedRecipe.RecipePattern recipePattern) {
                    this.recipePattern = recipePattern;
                    return asSelf();
                }
            }

            public static abstract class Shapeless extends Crafting<MCCShapelessRecipe, Shapeless> {
                protected List<MCCIngredient> ingredients = new ArrayList<>();

                public Shapeless ingredients(MCCIngredient... ingredients) {
                    this.ingredients = Arrays.stream(ingredients).toList();
                    return asSelf();
                }
            }
        }


        public static abstract class Single<T extends MCCSingleItemRecipe, SELF extends Single<T, SELF, CATEGORY>, CATEGORY> extends Standard<T, SELF, CATEGORY> {

            protected MCCIngredient ingredient;

            public SELF ingredient(MCCIngredient ingredient) {
                this.ingredient = ingredient;
                return asSelf();
            }

            public static abstract class Cooking<T extends MCCCookingRecipe, SELF extends Cooking<T, SELF>> extends Single<T, SELF, MCCCookingBookCategory> {
                protected float experience;
                protected int cookingTime;

                public Cooking<T, SELF> cookingTime(int cookingTime) {
                    this.cookingTime = cookingTime;
                    return this;
                }

                public Cooking<T, SELF> experience(float experience) {
                    this.experience = experience;
                    return this;
                }

                public static abstract class Blasting extends Cooking<MCCBlastingRecipe, Blasting> {

                }

                public static abstract class Furnace extends Cooking<MCCFurnaceRecipe, Furnace> {

                }

                public static abstract class Smoker extends Cooking<MCCSmokingRecipe, Smoker> {

                }

                public static abstract class Campfire extends Cooking<MCCCampfireRecipe, Campfire> {

                }
            }

            // TODO: Have no recipe book
            public static abstract class Stonecutter extends Single<MCCStonecutterRecipe, Stonecutter, MCCRecipeBookCategory> {

            }
        }
    }

    interface RecipeConstructor<T extends MCCRecipe, SELF extends RecipeConstructor<T, SELF>> {
        RecipeDraft<T> build();

        default SELF asSelf() {
            return (SELF) this;
        }

        default ConversionService conversionService() {
            return MCCPlatform.getInstance().getConversionService();
        }
    }
}
