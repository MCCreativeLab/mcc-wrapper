package de.verdox.mccreativelab.impl.vanilla.gamefactory;

import de.verdox.mccreativelab.gamefactory.MCCGameFactory;
import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStatePropertyFactory;
import de.verdox.mccreativelab.gamefactory.item.MCCCustomItemType;
import de.verdox.mccreativelab.impl.vanilla.block.properties.NMSBlockStatePropertyFactory;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.typed.MCCDataComponentTypes;
import net.kyori.adventure.key.Key;

import java.util.Optional;

public class NMSGameFactory implements MCCGameFactory {
    private final MCCPlatform platform;
    protected final MCCBlockStatePropertyFactory nmsBlockStatePropertyFactory;

    public NMSGameFactory(MCCPlatform platform) {
        this.platform = platform;
        createRegistries();
        this.nmsBlockStatePropertyFactory = new NMSBlockStatePropertyFactory();
    }

    private void createRegistries() {
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.ATTRIBUTE_REGISTRY.key());
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.BLOCK_REGISTRY.key());
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.ITEM_REGISTRY.key());
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.POI_TYPE_REGISTRY.key());
        platform.getRegistryStorage().createMinecraftRegistry(MCCGameFactory.VILLAGER_PROFESSION_REGISTRY.key());
    }

    @Override
    public Optional<MCCCustomItemType> extract(MCCItemStack mccItemStack) {
        Key itemModelKey = mccItemStack.components().get(MCCDataComponentTypes.ITEM_MODEL.get());
        if (itemModelKey == null) {
            return Optional.empty();
        } else if (itemModelKey.namespace().equals("minecraft")) {
            return Optional.empty();
        }
        return MCCGameFactory.ITEM_REGISTRY.get().getOptional(itemModelKey);
    }

    @Override
    public MCCBlockStatePropertyFactory getBlockStatePropertyFactory() {
        return nmsBlockStatePropertyFactory;
    }
}
