package de.verdox.mccreativelab.impl.vanilla.gamefactory.advancement;

import de.verdox.mccreativelab.gamefactory.advancement.MCCAdvancementRewardBuilder;
import de.verdox.mccreativelab.wrapper.inventory.recipe.MCCRecipe;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.types.MCCLootTable;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NMSAdvancementRewardBuilder implements MCCAdvancementRewardBuilder {
    int experience;
    final List<MCCTypedKey<MCCLootTable>> lootTables = new ArrayList<>();
    final List<MCCTypedKey<MCCRecipe>> recipes = new ArrayList<>();

    public @NotNull NMSAdvancementRewardBuilder setExperience(int experience) {
        this.experience = experience;
        return this;
    }

    public @NotNull NMSAdvancementRewardBuilder withLootTable(@NotNull MCCTypedKey<MCCLootTable> typedKey) {
        this.lootTables.add(typedKey);
        return this;
    }

    public @NotNull NMSAdvancementRewardBuilder withRecipe(@NotNull MCCTypedKey<MCCRecipe> typedKey) {
        this.recipes.add(typedKey);
        return this;
    }

    public AdvancementRewards build() {
        return new AdvancementRewards(
                this.experience,
                lootTables.stream().map(typedKey -> ResourceKey.create(Registries.LOOT_TABLE, MCCPlatform.getInstance().getConversionService().unwrap(typedKey))).toList(),
                recipes.stream().map(typedKey -> ResourceKey.create(Registries.RECIPE, MCCPlatform.getInstance().getConversionService().unwrap(typedKey))).toList(),
                Optional.empty()
        );
    }
}