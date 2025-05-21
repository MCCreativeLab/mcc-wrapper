package de.verdox.mccreativelab.gamefactory.advancement;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public interface MCCAdvancementRequirementsBuilder {

    @NotNull MCCAdvancementRequirementsBuilder withStrategy(@NotNull Strategy strategy);
    @NotNull MCCAdvancementRequirementsBuilder addCriterion(@NotNull String name);


    enum Strategy {
        OR,
        AND,
    }
}