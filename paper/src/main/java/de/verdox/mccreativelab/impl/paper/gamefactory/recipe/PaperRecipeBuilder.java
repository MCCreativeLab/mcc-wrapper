package de.verdox.mccreativelab.impl.paper.gamefactory.recipe;

import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.NMSIngredient;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.builder.NMSRecipeBuilder;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import org.bukkit.craftbukkit.inventory.CraftRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

public class PaperRecipeBuilder extends NMSRecipeBuilder {

    @Override
    public MCCIngredient createIngredient(MCCItemStack... allowedItemStacks) {
        if(!NMSPlatform.isMixinSupported) {
            ItemStack[] items = new ItemStack[allowedItemStacks.length];
            for (int i = 0; i < allowedItemStacks.length; i++) {
                items[i] = BukkitAdapter.toBukkit(allowedItemStacks[i]);
            }

            RecipeChoice.ExactChoice exactChoice = new RecipeChoice.ExactChoice(items);
            return new NMSIngredient(CraftRecipe.toIngredient(exactChoice, true));
        }
        else {
            return super.createIngredient(allowedItemStacks);
        }
    }
}
