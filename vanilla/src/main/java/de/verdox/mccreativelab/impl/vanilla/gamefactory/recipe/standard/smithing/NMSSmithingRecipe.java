package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.smithing;

import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipeBookCategory;
import de.verdox.mccreativelab.gamefactory.recipe.standard.smithing.MCCSmithingRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.NMSRecipe;
import net.minecraft.world.item.crafting.SmithingRecipe;

import java.util.Optional;

public abstract class NMSSmithingRecipe<T extends SmithingRecipe> extends NMSRecipe<T> implements MCCSmithingRecipe {
    public NMSSmithingRecipe(T handle) {
        super(handle);
    }

    @Override
    public Optional<MCCIngredient> templateIngredient() {
        return Optional.empty();
    }

    @Override
    public Optional<MCCIngredient> baseIngredient() {
        return Optional.empty();
    }

    @Override
    public Optional<MCCIngredient> additionIngredient() {
        return Optional.empty();
    }

    @Override
    public MCCRecipeBookCategory getCategory() {
        return conversionService.wrap(handle.recipeBookCategory(), MCCRecipeBookCategory.class);
    }
}
