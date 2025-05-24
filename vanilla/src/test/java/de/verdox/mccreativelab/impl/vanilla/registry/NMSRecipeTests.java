package de.verdox.mccreativelab.impl.vanilla.registry;

import de.verdox.mccreativelab.NMSTestBase;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.impl.vanilla.registry.recipe.NMSRecipeManager;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import de.verdox.mccreativelab.wrapper.typed.MCCRegistries;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NMSRecipeTests extends NMSTestBase {
    @Test
    public void testGetRegistryReturnsWrappedRecipeManager() {
        MCCRegistry<MCCRecipe> recipeRegistry = MCCRegistries.RECIPE_REGISTRY.get();
        Assertions.assertInstanceOf(NMSRecipeManager.class, recipeRegistry);
    }
}
