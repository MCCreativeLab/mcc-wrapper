package de.verdox.mccreativelab.advancement;

import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface MCCAdvancementBuilder {
    @NotNull MCCAdvancementBuilder withParent(@NotNull MCCTypedKey typedKey); // TODO: Implement generic
    @NotNull MCCAdvancementBuilder withDisplay(@NotNull Consumer<MCCAdvancementDisplayBuilder> craftAdvancementDisplayBuilder);
    @NotNull MCCAdvancementBuilder withRewards(@NotNull Consumer<MCCAdvancementRewardBuilder> craftAdvancementRewardBuilder);
    @NotNull MCCAdvancementBuilder withRequirements(@NotNull Consumer<MCCAdvancementRequirementsBuilder> craftAdvancementRewardBuilder);
    // TODO: @NotNull Advancement addToBukkit(@NotNull NamespacedKey namespacedKey);
}