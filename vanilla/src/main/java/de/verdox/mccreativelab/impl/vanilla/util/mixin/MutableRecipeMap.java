package de.verdox.mccreativelab.impl.vanilla.util.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;

public interface MutableRecipeMap {
    void mcc_wrapper$addCustomRecipe(RecipeHolder<?> holder);

    <T extends RecipeInput> boolean mcc_wrapper$removeAnyRecipe(ResourceKey<Recipe<T>> mcKey);
}
