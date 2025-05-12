package de.verdox.mccreativelab.advancement;

import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface AdvancementBuilder {
    @NotNull AdvancementBuilder withParent(@NotNull MCCTypedKey typedKey); // TODO: Implement generic
    @NotNull AdvancementBuilder withDisplay(@NotNull Consumer<AdvancementDisplayBuilder> craftAdvancementDisplayBuilder);
    @NotNull AdvancementBuilder withRewards(@NotNull Consumer<AdvancementRewardBuilder> craftAdvancementRewardBuilder);
    @NotNull AdvancementBuilder withRequirements(@NotNull Consumer<AdvancementRequirementsBuilder> craftAdvancementRewardBuilder);
    // TODO: @NotNull Advancement addToBukkit(@NotNull NamespacedKey namespacedKey);
}