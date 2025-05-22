package de.verdox.mccreativelab.impl.vanilla.gamefactory;

import de.verdox.mccreativelab.gamefactory.MCCGameFactory;
import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStatePropertyFactory;
import de.verdox.mccreativelab.gamefactory.item.MCCCustomItemType;
import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.gamefactory.recipe.RecipePredicate;
import de.verdox.mccreativelab.impl.vanilla.block.properties.NMSBlockStatePropertyFactory;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.PredicateIngredient;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.typed.MCCDataComponentTypes;
import net.kyori.adventure.key.Key;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public class NMSGameFactory implements MCCGameFactory {
    private final MCCPlatform platform;
    protected final MCCBlockStatePropertyFactory nmsBlockStatePropertyFactory;

    public NMSGameFactory(MCCPlatform platform) {
        this.platform = platform;
        createRegistries();
        this.nmsBlockStatePropertyFactory = new NMSBlockStatePropertyFactory();
    }

    private void createRegistries() {
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.ATTRIBUTE_REGISTRY.key());
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.BLOCK_REGISTRY.key());
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.ITEM_REGISTRY.key());
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.POI_TYPE_REGISTRY.key());
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.VILLAGER_PROFESSION_REGISTRY.key());
    }

    @Override
    public Optional<MCCCustomItemType> extract(MCCItemStack mccItemStack) {
        Key itemModelKey = mccItemStack.components().get(MCCDataComponentTypes.ITEM_MODEL.get());
        if (itemModelKey == null) {
            return Optional.empty();
        } else if (itemModelKey.namespace().equals("minecraft")) {
            return Optional.empty();
        }
        return MCCGameFactory.ITEM_REGISTRY.get().getOptional(itemModelKey);
    }

    @Override
    public MCCBlockStatePropertyFactory getBlockStatePropertyFactory() {
        return nmsBlockStatePropertyFactory;
    }

    @Override
    public MCCIngredient createIngredient(Predicate<MCCItemStack> ingredientPredicate, MCCItemStack... recipeBookExamples) {
        platform.checkForMixins();
        Ingredient ingredient = Ingredient.of(Items.BEDROCK);
        Object ingredientAsObject = ingredient;
        if (!(ingredientAsObject instanceof PredicateIngredient predicateIngredient)) {
            throw new IllegalStateException("Ingredients should implement " + PredicateIngredient.class + " through mixins.");
        }
        predicateIngredient.setItemPredicate(new RecipePredicate(ingredientPredicate, Arrays.stream(recipeBookExamples).toList()));
        return MCCPlatform.getInstance().getConversionService().wrap(ingredient, MCCIngredient.class);
    }
}
