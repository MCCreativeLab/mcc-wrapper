package de.verdox.mccreativelab.impl.paper.plugin;

import de.verdox.mccreativelab.impl.paper.platform.PaperPlatform;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import net.kyori.adventure.text.Component;

public class MCCPaperPluginLoader implements PluginBootstrap, PluginLoader {
    @Override
    public void bootstrap(BootstrapContext context) {
        context.getLogger().info("Initializing mcc-platform "+MCCPlatform.INSTANCE);
        MCCPlatform.INSTANCE.setup(createPlatform(), MCCPlatform::init);
        MCCPlatform.getInstance().triggerLifecycleEvent(MCCPlatform.Lifecycle.BOOTSTRAP);
    }

    @Override
    public void classloader(PluginClasspathBuilder classpathBuilder) {
        String version = classpathBuilder.getContext().getConfiguration().getVersion();
        classpathBuilder.getContext().getLogger().info(Component.text("Starting MCC Paper Platform for version: " + version));
    }

    public MCCPlatform createPlatform() {
        return new PaperPlatform();
    }
}
