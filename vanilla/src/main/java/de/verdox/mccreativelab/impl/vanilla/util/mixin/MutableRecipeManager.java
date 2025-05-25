package de.verdox.mccreativelab.impl.vanilla.util.mixin;

import net.minecraft.world.item.crafting.RecipeHolder;

public interface MutableRecipeManager {
    void mcc_wrapper$addCustomRecipe(RecipeHolder<?> holder);
}
