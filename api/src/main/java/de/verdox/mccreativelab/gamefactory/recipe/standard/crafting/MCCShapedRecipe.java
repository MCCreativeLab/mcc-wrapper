package de.verdox.mccreativelab.gamefactory.recipe.standard.crafting;

import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;

import java.util.List;
import java.util.Map;

public interface MCCShapedRecipe extends MCCCraftingRecipe {
    RecipePattern getPattern();

    boolean showsNotification();

    record RecipePattern(Map<Character, MCCIngredient> mapping, List<String> pattern, int height, int width) {}
}
