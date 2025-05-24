package de.verdox.mccreativelab.gamefactory.recipe.standard.single;

import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.gamefactory.recipe.standard.MCCStandardRecipe;

/**
 * Represents a minecraft recipe
 */
public interface MCCSingleItemRecipe extends MCCStandardRecipe {
    MCCIngredient getInput();
}
