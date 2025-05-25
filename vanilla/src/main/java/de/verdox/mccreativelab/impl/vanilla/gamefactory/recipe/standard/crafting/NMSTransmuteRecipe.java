package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.crafting;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.gamefactory.recipe.standard.crafting.MCCTransmuteRecipe;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.TransmuteRecipe;

import java.util.stream.Stream;

public class NMSTransmuteRecipe extends NMSCraftingRecipe<TransmuteRecipe> implements MCCTransmuteRecipe {
    public static final MCCConverter<TransmuteRecipe, NMSTransmuteRecipe> CONVERTER = converter(NMSTransmuteRecipe.class, TransmuteRecipe.class, NMSTransmuteRecipe::new, MCCHandle::getHandle);

    public NMSTransmuteRecipe(TransmuteRecipe handle) {
        super(handle);
    }

    public NMSTransmuteRecipe(TransmuteRecipe handle, boolean custom) {
        super(handle, custom);
    }

    @Override
    public MCCIngredient getInput() {
        return conversionService.wrap(ReflectionUtils.readFieldFromClass(handle, "input", new TypeToken<Ingredient>() {}), new TypeToken<>() {});
    }

    @Override
    public MCCIngredient getMaterial() {
        return conversionService.wrap(ReflectionUtils.readFieldFromClass(handle, "material", new TypeToken<Ingredient>() {}), new TypeToken<>() {});
    }

    @Override
    public MCCItemStack getResult() {
        return conversionService.wrap(ReflectionUtils.readFieldFromClass(handle, "result", new TypeToken<Holder<Item>>() {}), new TypeToken<MCCReference<MCCItemType>>() {}).get().createItem();
    }

    @Override
    public Stream<MCCIngredient> getAllIngredientsWithoutContext() {
        return Stream.of(getInput(), getMaterial());
    }
}
