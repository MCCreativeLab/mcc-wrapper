package de.verdox.mccreativelab.impl.paper.plugin;

import de.verdox.mccreativelab.impl.paper.platform.PaperPlatform;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MCCPaperPlatformPlugin extends JavaPlugin implements Listener {

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        PaperPlatform platform = (PaperPlatform) MCCPlatform.getInstance();
        platform.enableListeners(this);
    }

    @EventHandler
    public void startup(ServerLoadEvent e){
        if(e.getType().equals(ServerLoadEvent.LoadType.STARTUP)){
            MCCPlatform.getInstance().triggerLifecycleEvent(MCCPlatform.Lifecycle.SERVER_STARTUP_COMPLETE);
        }
    }

    @EventHandler
    public void afterLastWorldWasLoaded(WorldInitEvent e){
        if(Bukkit.getWorlds().getFirst().equals(e.getWorld())){
            MCCPlatform.getInstance().triggerLifecycleEvent(MCCPlatform.Lifecycle.BEFORE_WORLD_LOAD);
        }
    }

    @EventHandler
    public void afterLastWorldWasLoaded(WorldLoadEvent e){
        if(Bukkit.getWorlds().getLast().equals(e.getWorld())){
            MCCPlatform.getInstance().triggerLifecycleEvent(MCCPlatform.Lifecycle.AFTER_WORLD_LOAD);
        }
    }
}
