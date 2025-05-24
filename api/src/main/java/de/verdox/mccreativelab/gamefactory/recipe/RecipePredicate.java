package de.verdox.mccreativelab.gamefactory.recipe;

import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public record RecipePredicate(@NotNull Predicate<MCCItemStack> predicate, @NotNull List<MCCItemStack> recipeBookExamples) {
}
