package de.verdox.mccreativelab.factory.advancement;

import de.verdox.mccreativelab.wrapper.inventory.recipe.MCCRecipe;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.types.MCCLootTable;
import org.jetbrains.annotations.NotNull;

public interface MCCAdvancementRewardBuilder {
    @NotNull MCCAdvancementRewardBuilder setExperience(int experience);

    @NotNull MCCAdvancementRewardBuilder withLootTable(@NotNull MCCTypedKey<MCCLootTable> typedKey);

    @NotNull MCCAdvancementRewardBuilder withRecipe(@NotNull MCCTypedKey<MCCRecipe> typedKey);
}