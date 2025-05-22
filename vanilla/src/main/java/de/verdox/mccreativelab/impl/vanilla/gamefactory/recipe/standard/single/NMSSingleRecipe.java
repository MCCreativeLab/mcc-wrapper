package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single;

import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipeBookCategory;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.MCCSingleItemRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.NMSRecipe;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import net.minecraft.world.item.crafting.*;

public abstract class NMSSingleRecipe<T extends SingleItemRecipe> extends NMSRecipe<T> implements MCCSingleItemRecipe {
    public NMSSingleRecipe(T handle) {
        super(handle);
    }

    @Override
    public MCCIngredient getInput() {
        return conversionService.wrap(handle.input(), MCCIngredient.class);
    }

    @Override
    @MCCReflective
    public MCCItemStack getResult() {
        return conversionService.wrap(ReflectionUtils.invokeMethodInClass(handle, "result"), MCCItemStack.class);
    }

    @Override
    public String getGroup() {
        return handle.group();
    }

    @Override
    public MCCRecipeBookCategory getCategory() {
        return conversionService.wrap(handle.recipeBookCategory(), MCCRecipeBookCategory.class);
    }
}
