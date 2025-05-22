package de.verdox.mccreativelab.impl.paper.recipe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

/**
 * Package private implementation for {@link PredicateChoice}
 * @param predicate - The Item predicate
 * @param choices - The recipe book choices
 */
public record PredicateChoiceImpl(@NotNull Predicate<ItemStack> predicate, @NotNull List<ItemStack> choices) implements PredicateChoice {
    @Override
    public @NotNull ItemStack getItemStack() {
        ItemStack stack = new ItemStack(choices.get(0));
        // For compat
        if (choices.size() > 1) {
            stack.setDurability(Short.MAX_VALUE);
            return stack;
        }
        return stack;
    }

    @Override
    public @NotNull RecipeChoice clone() {
        return new PredicateChoiceImpl(predicate, recipeBookExamples());
    }

    @Override
    public boolean test(@NotNull final ItemStack itemStack) {
        return predicate.test(itemStack);
    }

    @Override
    @NotNull
    public List<ItemStack> recipeBookExamples() {
        return List.copyOf(choices);
    }
}
