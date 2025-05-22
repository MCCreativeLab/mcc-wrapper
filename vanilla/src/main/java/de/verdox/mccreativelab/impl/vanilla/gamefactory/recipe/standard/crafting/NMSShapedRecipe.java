package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.crafting;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.gamefactory.recipe.standard.crafting.MCCShapedRecipe;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

import java.util.HashMap;
import java.util.Map;

public class NMSShapedRecipe extends NMSCraftingRecipe<ShapedRecipe> implements MCCShapedRecipe {
    public static final MCCConverter<ShapedRecipe, NMSShapedRecipe> CONVERTER = converter(NMSShapedRecipe.class, ShapedRecipe.class, NMSShapedRecipe::new, MCCHandle::getHandle);

    public NMSShapedRecipe(ShapedRecipe handle) {
        super(handle);
    }

    @Override
    public RecipePattern getPattern() {
        ShapedRecipePattern shapedRecipePattern = getPatternFromHandle();
        ShapedRecipePattern.Data data = getDataFromPattern(shapedRecipePattern);
        Map<Character, MCCIngredient> mapping = new HashMap<>();
        data.key().forEach((character, ingredient) -> mapping.put(character, conversionService.wrap(ingredient, MCCIngredient.class)));
        return new RecipePattern(Map.copyOf(mapping), data.pattern(), shapedRecipePattern.height(), shapedRecipePattern.width());
    }

    @Override
    public boolean showsNotification() {
        return handle.showNotification();
    }

    @Override
    public MCCItemStack getResult() {
        return conversionService.wrap(ReflectionUtils.readFieldFromClass(handle, "result", new TypeToken<ItemStack>() {}), new TypeToken<>() {});
    }

    private ShapedRecipePattern getPatternFromHandle() {
        return ReflectionUtils.readFieldFromClass(handle, "pattern", new TypeToken<>() {});
    }

    private ShapedRecipePattern.Data getDataFromPattern(ShapedRecipePattern shapedRecipePattern) {
        return ReflectionUtils.readFieldFromClass(shapedRecipePattern, "data", new TypeToken<>() {});
    }
}
