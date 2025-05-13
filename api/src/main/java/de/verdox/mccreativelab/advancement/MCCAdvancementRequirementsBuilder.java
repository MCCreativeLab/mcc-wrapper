package de.verdox.mccreativelab.advancement;

import org.jetbrains.annotations.NotNull;

public interface MCCAdvancementRequirementsBuilder {

    @NotNull MCCAdvancementRequirementsBuilder withStrategy(@NotNull Strategy strategy);
    @NotNull MCCAdvancementRequirementsBuilder addCriterion(@NotNull String name);


    enum Strategy {
        OR,
        AND,
    }
}