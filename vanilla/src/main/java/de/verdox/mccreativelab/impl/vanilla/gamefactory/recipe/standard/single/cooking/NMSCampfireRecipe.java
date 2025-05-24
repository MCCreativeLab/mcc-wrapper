package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single.cooking;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.cooking.MCCCampfireRecipe;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;

public class NMSCampfireRecipe extends NMSCookingRecipe<CampfireCookingRecipe> implements MCCCampfireRecipe {
    public static final MCCConverter<CampfireCookingRecipe, NMSCampfireRecipe> CONVERTER = converter(NMSCampfireRecipe.class, CampfireCookingRecipe.class, NMSCampfireRecipe::new, MCCHandle::getHandle);

    public NMSCampfireRecipe(CampfireCookingRecipe handle) {
        super(handle);
    }
}
