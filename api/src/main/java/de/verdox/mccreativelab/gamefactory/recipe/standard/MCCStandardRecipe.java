package de.verdox.mccreativelab.gamefactory.recipe.standard;

import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;

/**
 * Describes a basic minecraft recipe with a result.
 */
public interface MCCStandardRecipe extends MCCRecipe {
    MCCItemStack getResult();
    String getGroup();
}
