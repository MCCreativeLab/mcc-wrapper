package de.verdox.mccreativelab.impl.vanilla.factory.advancement;

import de.verdox.mccreativelab.factory.advancement.MCCAdvancementRequirementsBuilder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class NMSAdvancementRequirementsBuilder implements MCCAdvancementRequirementsBuilder {
    Map<String, Criterion<?>> criteria = new HashMap<>();
    Strategy strategy = Strategy.OR;

    @Override
    public @NotNull MCCAdvancementRequirementsBuilder withStrategy(@NotNull Strategy strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public @NotNull MCCAdvancementRequirementsBuilder addCriterion(@NotNull String name) {
        this.criteria.put(name, CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance()));
        return this;
    }

    public AdvancementRequirements buildNMS() {
        AdvancementRequirements.Strategy strategy = this.strategy == Strategy.AND ? AdvancementRequirements.Strategy.AND : AdvancementRequirements.Strategy.OR;
        return strategy.create(criteria.keySet());
    }

    public Map<String, Criterion<?>> getCriteria(){
        return criteria;
    }
}