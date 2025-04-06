package de.verdox.mccreativelab.impl.paper.plugin;

import de.verdox.mccreativelab.impl.paper.pack.PaperGeneratorHelper;
import de.verdox.mccreativelab.impl.paper.platform.PaperPlatform;
import de.verdox.mccreativelab.platform.GeneratorPlatformHelper;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class MCCPaperPlatformPlugin extends JavaPlugin implements Listener {

    private PaperPlatform platform;

    @Override
    public void onLoad() {
        platform = (PaperPlatform) MCCPlatform.getInstance();
        GeneratorPlatformHelper.INSTANCE.setup(platform.getResourcePackManager().getHelper(), platformHelper -> {
        });
        try {
            platform.getResourcePackManager().init(platform);
            platform.getResourcePackManager().getResourcePack().initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (platform.getResourcePackManager().getHelper() instanceof PaperGeneratorHelper helper) {
            helper.setJavaPlugin(this);
        }
    }

    @Override
    public void onEnable() {
        platform.enableListeners(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void startup(ServerLoadEvent e) {
        if (e.getType().equals(ServerLoadEvent.LoadType.STARTUP)) {
            MCCPlatform.getInstance().triggerLifecycleEvent(MCCPlatform.Lifecycle.SERVER_STARTUP_COMPLETE);
        }
    }

    @EventHandler
    public void afterLastWorldWasLoaded(WorldInitEvent e) {
        if (Bukkit.getWorlds().getFirst().equals(e.getWorld())) {
            MCCPlatform.getInstance().triggerLifecycleEvent(MCCPlatform.Lifecycle.BEFORE_WORLD_LOAD);
        }
    }

    @EventHandler
    public void afterLastWorldWasLoaded(WorldLoadEvent e) {
        if (Bukkit.getWorlds().getLast().equals(e.getWorld())) {
            MCCPlatform.getInstance().triggerLifecycleEvent(MCCPlatform.Lifecycle.AFTER_WORLD_LOAD);
        }
    }
}
