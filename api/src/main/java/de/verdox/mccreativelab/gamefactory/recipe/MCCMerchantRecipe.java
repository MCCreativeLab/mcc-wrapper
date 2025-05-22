package de.verdox.mccreativelab.gamefactory.recipe;

import de.verdox.mccreativelab.gamefactory.recipe.standard.MCCStandardRecipe;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import org.jetbrains.annotations.NotNull;

public record MCCMerchantRecipe(@NotNull MCCItemStack result, int uses, int maxUses, boolean experienceReward,
                                int villagerExperience, float priceMultiplier, int demand, int specialPrice,
                                boolean ignoreDiscounts) implements MCCStandardRecipe {
    @Override
    public @NotNull MCCItemStack getResult() {

        return result;
    }

    @Override
    public String getGroup() {
        return "";
    }

    @Override
    public MCCRecipeBookCategory getCategory() {
        return null;
    }
}
