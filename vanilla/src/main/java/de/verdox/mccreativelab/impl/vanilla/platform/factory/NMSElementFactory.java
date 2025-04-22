package de.verdox.mccreativelab.impl.vanilla.platform.factory;

import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.platform.factory.MCCElementFactory;
import net.minecraft.core.component.DataComponentMap;

public class NMSElementFactory implements MCCElementFactory {
    @Override
    public MCCDataComponentMap createEmptyDataComponentMap() {
        return MCCPlatform.getInstance().getConversionService().wrap(DataComponentMap.builder().build());
    }
}
