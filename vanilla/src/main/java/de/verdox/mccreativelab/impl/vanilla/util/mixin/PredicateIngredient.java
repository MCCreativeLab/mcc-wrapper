package de.verdox.mccreativelab.impl.vanilla.util.mixin;

import de.verdox.mccreativelab.gamefactory.recipe.RecipePredicate;

public interface PredicateIngredient {
    RecipePredicate mcc_wrapper$getItemPredicate();

    void mcc_wrapper$setItemPredicate(RecipePredicate predicate);
}
