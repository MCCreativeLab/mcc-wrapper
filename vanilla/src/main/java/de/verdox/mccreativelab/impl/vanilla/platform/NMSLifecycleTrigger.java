package de.verdox.mccreativelab.impl.vanilla.platform;

import de.verdox.mccreativelab.conversion.SwapMap;
import de.verdox.mccreativelab.wrapper.platform.MCCLifecycleTrigger;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.Bukkit;

import java.io.File;

public class NMSLifecycleTrigger implements MCCLifecycleTrigger {

    @Override
    public void bootstrap() {

    }

    @Override
    public void beforeWorldLoad() {
        LOGGER.info("Freezing custom registries");
        MCCPlatform.getInstance().getRegistryStorage().freezeCustomRegistries();
    }

    @Override
    public void afterWorldLoad() {

    }

    @Override
    public void onServerStartupComplete() {
        LOGGER.info("Platform startup complete");
        SwapMap swapMap = MCCPlatform.getInstance().getConversionService().createSwapMap();
        File file = new File("../../versionSwapMaps/" + Bukkit.getBukkitVersion() + "/swap_map.json");
        swapMap.saveToFile(file);
    }
}
