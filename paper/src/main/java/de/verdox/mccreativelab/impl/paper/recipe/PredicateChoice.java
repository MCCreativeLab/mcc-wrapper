package de.verdox.mccreativelab.impl.paper.recipe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public interface PredicateChoice extends RecipeChoice {

    /**
     * Returns the Item predicate
     * @return - The item predicate
     */
    Predicate<ItemStack> predicate();

    /**
     * Returns the recipe book examples
     * @return the examples
     */
    List<ItemStack> recipeBookExamples();

    static PredicateChoice create(@NotNull Predicate<ItemStack> predicate, ItemStack... recipeBookExamples){
        Objects.requireNonNull(predicate, "The item predicate cannot be null!");
        Objects.requireNonNull(predicate, "The mustHaveRecipeBookExample cannot be null!");
        if(recipeBookExamples.length == 0)
            throw new IllegalArgumentException("Please provide at least one recipe book example item!");
        return new PredicateChoiceImpl(predicate, List.of(recipeBookExamples));
    }

    static PredicateChoice create(@NotNull Predicate<ItemStack> predicate, java.util.Collection<ItemStack> recipeBookExamples){
        Objects.requireNonNull(predicate, "The item predicate cannot be null!");
        Objects.requireNonNull(predicate, "The mustHaveRecipeBookExample cannot be null!");
        if(recipeBookExamples.isEmpty())
            throw new IllegalArgumentException("Please provide at least one recipe book example item!");
        return new PredicateChoiceImpl(predicate, List.copyOf(recipeBookExamples));
    }

}
