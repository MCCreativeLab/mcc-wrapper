package de.verdox.mccreativelab.impl.vanilla.registry.recipe;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.builder.RecipeBuilder;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.*;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NMSRecipeManager extends MCCHandle<RecipeManager> implements MCCRegistry<MCCRecipe> {
    public static final MCCConverter<RecipeManager, NMSRecipeManager> CONVERTER = converter(NMSRecipeManager.class, RecipeManager.class, NMSRecipeManager::new, MCCHandle::getHandle);

    private final MCCTypedKey<MCCRegistry<MCCRecipe>> RECIPE_REGISTRY_KEY = MCCPlatform.getInstance().getTypedKeyFactory().getRegistryKey("recipe");

    public NMSRecipeManager(RecipeManager handle) {
        super(handle);
    }

    @Override
    public @Nullable Key getKey(MCCRecipe value) {
        Recipe<?> recipe = MCCPlatform.getInstance().getConversionService().unwrap(value, Recipe.class);
        return handle.getRecipes()
                .stream()
                .filter(recipeHolder -> recipeHolder.value().equals(recipe))
                .findFirst()
                .map(recipeHolder -> MCCPlatform.getInstance().getConversionService().wrap(recipeHolder.id(), new TypeToken<Key>() {}))
                .orElse(null);
    }

    @Override
    public Optional<MCCTypedKey<MCCRecipe>> getTypedKey(MCCRecipe value) {
        Key key = getKey(value);
        if (key == null) {
            return Optional.empty();
        }
        return Optional.of(MCCPlatform.getInstance().getTypedKeyFactory().getKey(RECIPE_REGISTRY_KEY.key(), key));
    }

    @Override
    public @Nullable MCCRecipe get(@Nullable MCCTypedKey<MCCRecipe> key) {
        return handle.byKey(conversionService.unwrap(key, new TypeToken<>() {})).map(recipeHolder -> conversionService.wrap(recipeHolder.value(), new TypeToken<MCCRecipe>() {})).orElse(null);
    }

    @Override
    public @Nullable MCCRecipe get(@Nullable Key key) {
        return get(MCCPlatform.getInstance().getTypedKeyFactory().getKey(RECIPE_REGISTRY_KEY.key(), key));
    }

    @Override
    public Optional<MCCReference<MCCRecipe>> getAny() {
        return handle.getRecipes().stream().findAny().map(recipeHolder -> conversionService.wrap(recipeHolder, new TypeToken<>() {}));
    }

    @Override
    public MCCRecipe getOrThrow(MCCTypedKey<MCCRecipe> key) {
        MCCRecipe found = get(key);
        if (found == null) {
            throw new NoSuchElementException("No recipe found for key: " + key.key());
        }
        return found;
    }

    @Override
    public Set<Key> keySet() {
        return registryKeySet().stream().map(Keyed::key).collect(Collectors.toSet());
    }

    @Override
    public Set<MCCTypedKey<MCCRecipe>> registryKeySet() {
        return handle.getRecipes().stream().map(recipeHolder -> conversionService.wrap(recipeHolder.id(), new TypeToken<MCCTypedKey<MCCRecipe>>() {})).collect(Collectors.toSet());
    }

    @Override
    public boolean containsKey(Key key) {
        return containsKey(MCCPlatform.getInstance().getTypedKeyFactory().getKey(RECIPE_REGISTRY_KEY.key(), key));
    }

    @Override
    public boolean containsKey(MCCTypedKey<MCCRecipe> key) {
        return get(key) != null;
    }

    @Override
    public Optional<MCCReference<MCCRecipe>> getReference(Key key) {
        return getReference(MCCPlatform.getInstance().getTypedKeyFactory().getKey(RECIPE_REGISTRY_KEY.key(), key));
    }

    @Override
    public Optional<MCCReference<MCCRecipe>> getReference(MCCTypedKey<MCCRecipe> key) {
        MCCReference<MCCRecipe> reference = handle.byKey(conversionService.unwrap(key, new TypeToken<>() {})).map(recipeHolder -> conversionService.wrap(recipeHolder, new TypeToken<MCCReference<MCCRecipe>>() {})).orElse(null);
        return Optional.ofNullable(reference);
    }

    @Override
    public MCCReference<MCCRecipe> wrapAsReference(MCCRecipe value) {
        return getReference(value.key()).orElseThrow(() -> new RuntimeException("Recipe not registered " + value));
    }

    @Override
    public Optional<MCCReferenceSet<MCCRecipe>> getTagValues(MCCTag<MCCRecipe> tag) {
        throw new UnsupportedOperationException("Recipes don't support tags");
    }

    @Override
    public MCCReferenceSet<MCCRecipe> getOrCreateTag(MCCTag<MCCRecipe> tag) {
        throw new UnsupportedOperationException("Recipes don't support tags");
    }

    @Override
    public Iterable<MCCReference<MCCRecipe>> iterate(MCCTag<MCCRecipe> tag) {
        throw new UnsupportedOperationException("Recipes don't support tags");
    }

    @Override
    public Stream<MCCTag<MCCRecipe>> getTagNames() {
        throw new UnsupportedOperationException("Recipes don't support tags");
    }

    @Override
    public MCCReference<MCCRecipe> register(MCCTypedKey<MCCRecipe> key, MCCRecipe value) {
        MCCPlatform.getInstance().getGameFactory().registerCustomRecipe(key.key(), new RecipeBuilder.RecipeDraft<>(value));
        return getReference(key).orElseThrow(() -> new RuntimeException("Could not register recipe " + key.key()));
    }

    private RecipeMap getRecipeMap() {
        return ReflectionUtils.readFieldFromClass(handle, "recipes", new TypeToken<>() {});
    }
}
