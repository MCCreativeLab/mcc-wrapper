package de.verdox.mccreativelab.impl.paper.test;

import de.verdox.mccreativelab.gamefactory.item.ItemVisuals;
import de.verdox.mccreativelab.gamefactory.item.MCCCustomItemType;
import de.verdox.mccreativelab.gamefactory.recipe.MCCCookingBookCategory;
import de.verdox.mccreativelab.gamefactory.recipe.builder.RecipeBuilder;
import de.verdox.mccreativelab.gamefactory.recipe.standard.crafting.MCCShapedRecipe;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.typed.MCCItems;
import net.kyori.adventure.key.Key;

public class GameFactoryTestData {
    public static void init() {
        var factory = MCCPlatform.getInstance().getGameFactory();
        
        RecipeBuilder recipeBuilder = factory.createRecipe();
        var cookingRecipeDraft = recipeBuilder.createBlastingRecipe()
                .category(MCCCookingBookCategory.FOOD)
                .cookingTime(20 * 30)
                .ingredient(recipeBuilder.createIngredient(MCCItems.ACACIA_BOAT.get()))
                .result(MCCItems.DIRT.get().createItem())
                .build();
        factory.registerCustomRecipe(Key.key("test", "blasting"), cookingRecipeDraft);

        var shapedRecipe = recipeBuilder.createShapedRecipe()
                .pattern(
                        MCCShapedRecipe.createPattern().withRow('A', 'A', 'A')
                                .ingredient('A', recipeBuilder.createIngredient(MCCItems.ACACIA_BOAT.get()))
                                .build()
                )
                .result(MCCItems.DIRT.get().createItem())
                .build();
        factory.registerCustomRecipe(Key.key("test", "shaped"), shapedRecipe);

        factory.registerCustomItemType(Key.key("test", "test_item"), new TestItem(MCCPlatform.getInstance().getElementFactory().createEmptyDataComponentMap()), new ItemVisuals());
    }
    
    public static class TestItem extends MCCCustomItemType {
        public TestItem(MCCDataComponentMap standardComponents) {
            super(standardComponents);
        }
    }
}
