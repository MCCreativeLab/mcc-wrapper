package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.crafting;

import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipeBookCategory;
import de.verdox.mccreativelab.gamefactory.recipe.standard.crafting.MCCCraftingRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.NMSRecipe;
import net.minecraft.world.item.crafting.CraftingRecipe;

public abstract class NMSCraftingRecipe<T extends CraftingRecipe> extends NMSRecipe<T> implements MCCCraftingRecipe {
    public NMSCraftingRecipe(T handle) {
        super(handle);
    }

    public NMSCraftingRecipe(T handle, boolean custom) {
        super(handle, custom);
    }

    @Override
    public String getGroup() {
        return handle.group();
    }

    @Override
    public MCCRecipeBookCategory getCategory() {
        return conversionService.wrap(handle.category(), MCCRecipeBookCategory.class);
    }
}
