package de.verdox.mccreativelab.impl.vanilla.platform;

import de.verdox.mccreativelab.wrapper.platform.MCCLifecycleTrigger;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

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

    }
}
