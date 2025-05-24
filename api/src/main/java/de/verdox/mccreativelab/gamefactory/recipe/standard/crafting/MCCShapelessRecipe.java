package de.verdox.mccreativelab.gamefactory.recipe.standard.crafting;

import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;

import java.util.List;

public interface MCCShapelessRecipe extends MCCCraftingRecipe {
    List<MCCIngredient> getIngredients();
}
