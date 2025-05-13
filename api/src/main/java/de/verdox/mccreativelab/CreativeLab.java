package de.verdox.mccreativelab;

import de.verdox.mccreativelab.advancement.AdvancementBuilder;
import de.verdox.mccreativelab.data.DataPackInterceptor;
import de.verdox.mccreativelab.data.VanillaRegistryManipulator;
import org.jetbrains.annotations.NotNull;

public interface CreativeLab {
    /**
     * Returns the {@link DataPackInterceptor}
     * @return the data pack interceptor
     */
    @NotNull DataPackInterceptor getDataPackInterceptor();

    /**
     * Returns the {@link VanillaRegistryManipulator}
     * @return the vanilla registry manipulator
     */
    @NotNull VanillaRegistryManipulator getRegistryManipulator();

    /**
     * Returns the {@link @NotNull AdvancementBuilder}
     * @return the advancement builder
     */
    @NotNull AdvancementBuilder createAdvancement();
}