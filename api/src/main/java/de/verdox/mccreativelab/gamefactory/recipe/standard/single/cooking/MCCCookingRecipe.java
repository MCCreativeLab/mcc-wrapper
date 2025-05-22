package de.verdox.mccreativelab.gamefactory.recipe.standard.single.cooking;

import de.verdox.mccreativelab.gamefactory.recipe.standard.single.MCCSingleItemRecipe;

public interface MCCCookingRecipe extends MCCSingleItemRecipe {
    float getExperience();

    int getCookingTime();
}
