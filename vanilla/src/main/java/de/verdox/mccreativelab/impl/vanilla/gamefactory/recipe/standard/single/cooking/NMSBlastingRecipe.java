package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single.cooking;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.cooking.MCCBlastingRecipe;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.crafting.BlastingRecipe;

public class NMSBlastingRecipe extends NMSCookingRecipe<BlastingRecipe> implements MCCBlastingRecipe {
    public static final MCCConverter<BlastingRecipe, NMSBlastingRecipe> CONVERTER = converter(NMSBlastingRecipe.class, BlastingRecipe.class, NMSBlastingRecipe::new, MCCHandle::getHandle);

    public NMSBlastingRecipe(BlastingRecipe handle) {
        super(handle);
    }
}
