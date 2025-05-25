package de.verdox.mccreativelab.gamefactory.advancement;

import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.gamefactory.loottable.MCCLootTable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public interface MCCAdvancementRewardBuilder {
    @NotNull MCCAdvancementRewardBuilder setExperience(int experience);

    @NotNull MCCAdvancementRewardBuilder withLootTable(@NotNull MCCTypedKey<MCCLootTable> typedKey);

    @NotNull MCCAdvancementRewardBuilder withRecipe(@NotNull MCCTypedKey<MCCRecipe> typedKey);
}