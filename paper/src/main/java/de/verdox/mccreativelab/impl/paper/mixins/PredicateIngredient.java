package de.verdox.mccreativelab.impl.paper.mixins;

import de.verdox.mccreativelab.impl.paper.recipe.PredicateChoice;

public interface PredicateIngredient {
    PredicateChoice getItemPredicate();

    void setItemPredicate(PredicateChoice predicate);
}
