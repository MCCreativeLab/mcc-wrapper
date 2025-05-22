package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single.cooking;

import de.verdox.mccreativelab.gamefactory.recipe.standard.single.cooking.MCCCookingRecipe;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.single.NMSSingleRecipe;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;

public abstract class NMSCookingRecipe<T extends AbstractCookingRecipe> extends NMSSingleRecipe<T> implements MCCCookingRecipe {
    public NMSCookingRecipe(T handle) {
        super(handle);
    }

    @Override
    public float getExperience() {
        return handle.experience();
    }

    @Override
    public int getCookingTime() {
        return handle.cookingTime();
    }
}
