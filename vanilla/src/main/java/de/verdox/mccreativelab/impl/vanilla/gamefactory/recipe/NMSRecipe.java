package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe;

import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.crafting.Recipe;

public abstract class NMSRecipe<T extends Recipe<?>> extends MCCHandle<T> implements MCCRecipe {
    private boolean custom;

    public NMSRecipe(T handle) {
        this(handle, false);
    }

    public NMSRecipe(T handle, boolean custom) {
        super(handle);
        this.custom = custom;
    }

    @Override
    public boolean isVanilla() {
        return !custom;
    }
}
