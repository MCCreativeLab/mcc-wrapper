package de.verdox.mccreativelab.impl.paper.plugin;

import de.verdox.mccreativelab.impl.paper.platform.PaperPlatform;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;

public class MCCPaperPluginBootstrapper implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {
        context.getLogger().info("Initializing mcc-platform " + MCCPlatform.INSTANCE);
        MCCPlatform.INSTANCE.setup(createPlatform(), MCCPlatform::init);
        MCCPlatform.getInstance().triggerLifecycleEvent(MCCPlatform.Lifecycle.BOOTSTRAP);
    }

    public MCCPlatform createPlatform() {
        return new PaperPlatform();
    }
}
