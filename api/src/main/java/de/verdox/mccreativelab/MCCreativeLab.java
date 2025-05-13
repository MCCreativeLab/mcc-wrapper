package de.verdox.mccreativelab;

import de.verdox.mccreativelab.advancement.AdvancementBuilder;
import de.verdox.mccreativelab.data.DataPackInterceptor;
import de.verdox.mccreativelab.data.VanillaRegistryManipulator;
import org.jetbrains.annotations.NotNull;

public final class MCCreativeLab {
    private static CreativeLab creativeLab;

    public static void setCreativeLab(@NotNull CreativeLab creativeLab) {
        if (MCCreativeLab.creativeLab != null)
            throw new UnsupportedOperationException("Cannot redefine singleton CreativeLab");

        MCCreativeLab.creativeLab = creativeLab;
    }

    @NotNull
    public static DataPackInterceptor getDataPackInterceptor() {
        return creativeLab.getDataPackInterceptor();
    }

    @NotNull
    public static VanillaRegistryManipulator getRegistryManipulator() {
        return creativeLab.getRegistryManipulator();
    }

    @NotNull
    public static AdvancementBuilder createAdvancement() {
        return creativeLab.createAdvancement();
    }
}