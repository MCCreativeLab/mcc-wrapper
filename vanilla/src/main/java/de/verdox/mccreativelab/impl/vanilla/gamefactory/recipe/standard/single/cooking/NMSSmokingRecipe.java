package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single.cooking;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.cooking.MCCSmokingRecipe;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.crafting.SmokingRecipe;

public class NMSSmokingRecipe extends NMSCookingRecipe<SmokingRecipe> implements MCCSmokingRecipe {
    public static final MCCConverter<SmokingRecipe, NMSSmokingRecipe> CONVERTER = converter(NMSSmokingRecipe.class, SmokingRecipe.class, NMSSmokingRecipe::new, MCCHandle::getHandle);

    public NMSSmokingRecipe(SmokingRecipe handle) {
        super(handle);
    }
}
