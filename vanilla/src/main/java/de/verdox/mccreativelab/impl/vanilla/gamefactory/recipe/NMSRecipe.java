package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe;

import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.crafting.Recipe;

public abstract class NMSRecipe<T extends Recipe<?>> extends MCCHandle<T> implements MCCRecipe {
    public NMSRecipe(T handle) {
        super(handle);
    }
}
