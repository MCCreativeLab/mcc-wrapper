package de.verdox.mccreativelab.impl.vanilla.util.mixin;

import de.verdox.mccreativelab.gamefactory.recipe.RecipePredicate;

public interface PredicateIngredient {
    RecipePredicate getItemPredicate();

    void setItemPredicate(RecipePredicate predicate);
}
