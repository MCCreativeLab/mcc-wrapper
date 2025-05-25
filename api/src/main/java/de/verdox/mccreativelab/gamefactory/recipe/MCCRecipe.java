package de.verdox.mccreativelab.gamefactory.recipe;

import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.typed.MCCRegistries;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * Represents a minecraft recipe
 */
public interface MCCRecipe extends MCCKeyedWrapper {
    MCCRecipeBookCategory getCategory();

    @Override
    default Key getRegistryKey() {
        return MCCRegistries.RECIPE_REGISTRY.key();
    }

    @Override
    @NotNull
    default Key key() {
        return MCCRegistries.RECIPE_REGISTRY.get().getKey(this);
    }

    @ApiStatus.Internal
    Stream<MCCIngredient> getAllIngredientsWithoutContext();
}
