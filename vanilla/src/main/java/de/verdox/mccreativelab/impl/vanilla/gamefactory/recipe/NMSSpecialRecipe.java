package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipeBookCategory;
import de.verdox.mccreativelab.gamefactory.recipe.MCCSpecialRecipe;
import net.minecraft.world.item.crafting.CustomRecipe;

public class NMSSpecialRecipe<T extends CustomRecipe> extends NMSRecipe<T> implements MCCSpecialRecipe {
    public static final MCCConverter<CustomRecipe, NMSSpecialRecipe> CONVERTER = converter(NMSSpecialRecipe.class, CustomRecipe.class, NMSSpecialRecipe::new, nmsSpecialRecipe -> (CustomRecipe) nmsSpecialRecipe.handle);

    public NMSSpecialRecipe(T handle) {
        super(handle);
    }

    @Override
    public MCCRecipeBookCategory getCategory() {
        return conversionService.wrap(handle.category(), MCCRecipeBookCategory.class);
    }
}
