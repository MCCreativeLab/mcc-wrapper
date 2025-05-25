package de.verdox.mccreativelab.impl.vanilla.registry;

import de.verdox.mccreativelab.NMSTestBase;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.impl.vanilla.registry.recipe.NMSRecipeManager;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.typed.MCCRegistries;
import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class NMSRecipeTests extends NMSTestBase {

    MCCRegistry<MCCRecipe> recipeRegistry;

    @BeforeEach
    void setup() {
        recipeRegistry = MCCRegistries.RECIPE_REGISTRY.get();
    }

    @Test
    public void testGetRegistryReturnsWrappedRecipeManager() {
        MCCRegistry<MCCRecipe> recipeRegistry = MCCRegistries.RECIPE_REGISTRY.get();
        Assertions.assertInstanceOf(NMSRecipeManager.class, recipeRegistry);
    }

    @Test
    void testRecipeCanBeRetrievedByKey() {
        Optional<MCCReference<MCCRecipe>> anyRef = recipeRegistry.getAny();
        assertTrue(anyRef.isPresent(), "Es sollte mindestens ein Rezept im Registry geben");

        MCCRecipe recipe = anyRef.get().get();
        Key key = recipe.key();

        MCCRecipe byKey = recipeRegistry.get(key);
        assertNotNull(byKey, "Rezept muss über den Key abrufbar sein");
        assertEquals(recipe, byKey, "Das zurückgegebene Rezept sollte identisch sein");
    }

    @Test
    void testRecipeKeyIsCorrectlyResolved() {
        MCCReference<MCCRecipe> ref = recipeRegistry.getAny().orElseThrow();
        MCCRecipe recipe = ref.get();

        Key key = recipeRegistry.getKey(recipe);
        assertNotNull(key, "Der Key des Rezepts darf nicht null sein");
        assertEquals(recipe.key(), key, "Der über getKey zurückgegebene Key sollte mit dem Key des Rezepts übereinstimmen");
    }

    @Test
    void testContainsKeyWorks() {
        MCCReference<MCCRecipe> ref = recipeRegistry.getAny().orElseThrow();
        MCCRecipe recipe = ref.get();

        assertTrue(recipeRegistry.containsKey(recipe.key()), "Registry sollte den Key enthalten");
    }

    @Test
    void testRegistryKeySetContainsRecipeKey() {
        MCCReference<MCCRecipe> ref = recipeRegistry.getAny().orElseThrow();
        MCCRecipe recipe = ref.get();

        Set<MCCTypedKey<MCCRecipe>> keys = recipeRegistry.registryKeySet();
        assertTrue(keys.stream().anyMatch(k -> k.key().equals(recipe.key())), "registryKeySet sollte den Key enthalten");
    }

    @Test
    void testKeySetContainsRecipeKey() {
        MCCReference<MCCRecipe> ref = recipeRegistry.getAny().orElseThrow();
        MCCRecipe recipe = ref.get();

        Set<Key> keys = recipeRegistry.keySet();
        assertTrue(keys.contains(recipe.key()), "keySet sollte den Key des Rezepts enthalten");
    }

    @Test
    void testGetOrThrowReturnsRecipe() {
        MCCReference<MCCRecipe> ref = recipeRegistry.getAny().orElseThrow();
        MCCRecipe recipe = ref.get();

        MCCTypedKey<MCCRecipe> typedKey = ref.unwrapKey().get();
        MCCRecipe result = recipeRegistry.getOrThrow(typedKey);
        assertEquals(recipe, result, "getOrThrow sollte das korrekte Rezept zurückgeben");
    }

    @Test
    void testGetOrThrowThrowsForMissingKey() {
        MCCTypedKey<MCCRecipe> dummyKey = MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key("test", "dummy"), Key.key("minecraft", "registry"));
        assertThrows(NoSuchElementException.class, () -> recipeRegistry.getOrThrow(dummyKey));
    }

    @Test
    void testWrapAsReferenceReturnsValidReference() {
        MCCReference<MCCRecipe> ref = recipeRegistry.getAny().orElseThrow();
        MCCRecipe recipe = ref.get();

        MCCReference<MCCRecipe> result = recipeRegistry.wrapAsReference(recipe);
        assertNotNull(result);
        assertEquals(recipe, result.get(), "wrapAsReference sollte die gleiche Rezeptinstanz zurückgeben");
    }
}
