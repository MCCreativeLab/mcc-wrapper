package de.verdox.mccreativelab.gamefactory.recipe.standard.smithing;

import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;

import java.util.Optional;

public interface MCCSmithingRecipe extends MCCRecipe {
    Optional<MCCIngredient> templateIngredient();

    Optional<MCCIngredient> baseIngredient();

    Optional<MCCIngredient> additionIngredient();
}
