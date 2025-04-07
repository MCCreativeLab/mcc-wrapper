package de.verdox.mccreativelab.impl.vanilla.custom;

import de.verdox.mccreativelab.custom.MCCGameFactory;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

public class NMSGameFactory implements MCCGameFactory {
    private final MCCPlatform platform;

    public NMSGameFactory(MCCPlatform platform) {
        this.platform = platform;
    }

    private void createRegistries() {
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.ATTRIBUTE_REGISTRY.key());
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.BLOCK_REGISTRY.key());
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.ITEM_REGISTRY.key());
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.POI_TYPE_REGISTRY.key());
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.VILLAGER_PROFESSION_REGISTRY.key());
    }
}
