package de.verdox.mccreativelab.gamefactory.recipe.standard.crafting;

import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;

/**
 * Represents a recipe which will change the type of the input material when
 * combined with an additional material, but preserve all custom data. Only the
 * item type of the result stack will be used.
 * <br>
 * Used for dyeing shulker boxes in Vanilla.
 */
public interface MCCTransmuteRecipe extends MCCCraftingRecipe {
    MCCIngredient getInput();

    MCCIngredient getMaterial();
}
