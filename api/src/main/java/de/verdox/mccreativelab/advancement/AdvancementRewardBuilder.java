package de.verdox.mccreativelab.advancement;

import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.types.MCCLootTable;
import org.jetbrains.annotations.NotNull;

public interface AdvancementRewardBuilder {
    @NotNull AdvancementRewardBuilder setExperience(int experience);

    @NotNull AdvancementRewardBuilder withLootTable(@NotNull MCCTypedKey<MCCLootTable> typedKey);

    @NotNull AdvancementRewardBuilder withRecipe(@NotNull MCCTypedKey<MCCLootTable> typedKey);
}