package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.smithing;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.standard.smithing.MCCSmithingTrimRecipe;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;

public class NMSSmithingTrimRecipe extends NMSSmithingRecipe<SmithingTrimRecipe> implements MCCSmithingTrimRecipe {
    public static final MCCConverter<SmithingTrimRecipe, NMSSmithingTrimRecipe> CONVERTER = converter(NMSSmithingTrimRecipe.class, SmithingTrimRecipe.class, NMSSmithingTrimRecipe::new, MCCHandle::getHandle);

    public NMSSmithingTrimRecipe(SmithingTrimRecipe handle) {
        super(handle);
    }
}
