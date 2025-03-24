package de.verdox.mccreativelab.impl.paper.platform;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;

public class PaperPlatformBootstrap {

    public static void setup(MCCPlatform mccPlatform, BootstrapContext bootstrapContext) {
        mccPlatform.getLifecycleTrigger().bootstrap();
    }
}
