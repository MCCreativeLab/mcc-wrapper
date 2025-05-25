package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.crafting;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.gamefactory.recipe.standard.crafting.MCCShapelessRecipe;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.List;
import java.util.stream.Stream;

public class NMSShapelessRecipe extends NMSCraftingRecipe<ShapelessRecipe> implements MCCShapelessRecipe {
    public static final MCCConverter<ShapelessRecipe, NMSShapelessRecipe> CONVERTER = converter(NMSShapelessRecipe.class, ShapelessRecipe.class, NMSShapelessRecipe::new, MCCHandle::getHandle);

    public NMSShapelessRecipe(ShapelessRecipe handle) {
        super(handle);
    }

    public NMSShapelessRecipe(ShapelessRecipe handle, boolean custom) {
        super(handle, custom);
    }

    @Override
    public MCCItemStack getResult() {
        return conversionService.wrap(ReflectionUtils.readFieldFromClass(handle, "result", new TypeToken<ItemStack>() {}), new TypeToken<>() {});
    }

    @Override
    public List<MCCIngredient> getIngredients() {
        return conversionService.wrap(ReflectionUtils.readFieldFromClass(handle, "ingredients", new TypeToken<List<Ingredient>>() {}), new TypeToken<>() {});
    }

    @Override
    public Stream<MCCIngredient> getAllIngredientsWithoutContext() {
        return getIngredients().stream();
    }
}
