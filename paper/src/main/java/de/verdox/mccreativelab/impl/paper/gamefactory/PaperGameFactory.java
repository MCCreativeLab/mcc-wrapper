package de.verdox.mccreativelab.impl.paper.gamefactory;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.gamefactory.recipe.builder.RecipeBuilder;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.NMSGameFactory;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.typed.MCCRegistries;
import net.kyori.adventure.key.Key;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;

public class PaperGameFactory extends NMSGameFactory {
    public PaperGameFactory(NMSPlatform platform) {
        super(platform);
    }

    @Override
    public void registerCustomRecipe(Key key, RecipeBuilder.RecipeDraft<?> recipeDraft) {
        registerCustom(RECIPE_REGISTRY, key, recipeDraft.getDraft());
        MCCTypedKey<MCCRecipe> typedKey = MCCPlatform.getInstance().getTypedKeyFactory().getKey(key, MCCRegistries.RECIPE_REGISTRY.getRegistryKey());

        Recipe<?> recipe = MCCPlatform.getInstance().getConversionService().unwrap(recipeDraft.getDraft(), Recipe.class);
        ResourceKey<Recipe<?>> recipeResourceKey = MCCPlatform.getInstance().getConversionService().unwrap(typedKey, new TypeToken<>() {});

        RecipeHolder<?> recipeHolder = new RecipeHolder<>(recipeResourceKey, recipe);

        MinecraftServer.getServer().getRecipeManager().addRecipe(recipeHolder);
    }
}
