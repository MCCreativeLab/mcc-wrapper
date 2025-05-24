package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.crafting.Ingredient;

public class NMSIngredient extends MCCHandle<Ingredient> implements MCCIngredient {
    public static final MCCConverter<Ingredient, NMSIngredient> CONVERTER = converter(NMSIngredient.class, Ingredient.class, NMSIngredient::new, nmsSpecialRecipe -> nmsSpecialRecipe.handle);

    public NMSIngredient(Ingredient handle) {
        super(handle);
    }
}
