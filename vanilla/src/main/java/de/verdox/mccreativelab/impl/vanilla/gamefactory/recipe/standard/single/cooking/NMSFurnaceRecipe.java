package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single.cooking;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.cooking.MCCFurnaceRecipe;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class NMSFurnaceRecipe extends NMSCookingRecipe<SmeltingRecipe> implements MCCFurnaceRecipe {
    public static final MCCConverter<SmeltingRecipe, NMSFurnaceRecipe> CONVERTER = converter(NMSFurnaceRecipe.class, SmeltingRecipe.class, NMSFurnaceRecipe::new, MCCHandle::getHandle);

    public NMSFurnaceRecipe(SmeltingRecipe handle) {
        super(handle);
    }
}
