package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.smithing;

import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipeBookCategory;
import de.verdox.mccreativelab.gamefactory.recipe.standard.smithing.MCCSmithingRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.NMSRecipe;
import net.minecraft.world.item.crafting.SmithingRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class NMSSmithingRecipe<T extends SmithingRecipe> extends NMSRecipe<T> implements MCCSmithingRecipe {
    public NMSSmithingRecipe(T handle) {
        super(handle);
    }

    public NMSSmithingRecipe(T handle, boolean custom) {
        super(handle, custom);
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

    @Override
    public Stream<MCCIngredient> getAllIngredientsWithoutContext() {
        List<MCCIngredient> ingredients = new ArrayList<>();
        templateIngredient().ifPresent(ingredients::add);
        baseIngredient().ifPresent(ingredients::add);
        additionIngredient().ifPresent(ingredients::add);
        return ingredients.stream();
    }
}
