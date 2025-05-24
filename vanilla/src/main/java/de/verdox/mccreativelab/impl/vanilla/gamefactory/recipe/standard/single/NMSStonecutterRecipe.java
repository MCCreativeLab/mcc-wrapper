package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.standard.single.MCCStonecutterRecipe;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.crafting.StonecutterRecipe;

public class NMSStonecutterRecipe extends NMSSingleRecipe<StonecutterRecipe> implements MCCStonecutterRecipe {
    public static final MCCConverter<StonecutterRecipe, NMSStonecutterRecipe> CONVERTER = converter(NMSStonecutterRecipe.class, StonecutterRecipe.class, NMSStonecutterRecipe::new, MCCHandle::getHandle);

    public NMSStonecutterRecipe(StonecutterRecipe handle) {
        super(handle);
    }
}
