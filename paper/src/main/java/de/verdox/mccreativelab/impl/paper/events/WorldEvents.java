package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.world.MCCWorldInitEvent;
import de.verdox.mccreativelab.wrapper.event.world.MCCWorldLoadEvent;
import de.verdox.mccreativelab.wrapper.event.world.MCCWorldSaveEvent;
import de.verdox.mccreativelab.wrapper.event.world.MCCWorldUnloadEvent;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.*;

public class WorldEvents implements Listener {
    // without MCCWorldEvent

    @EventHandler
    public void handle(WorldInitEvent event) {
        MCCWorldInitEvent mccWorldInitEvent = new MCCWorldInitEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getWorld())
        );
        mccWorldInitEvent.callEvent();
    }

    @EventHandler
    public void handle(WorldLoadEvent event) {
        MCCWorldLoadEvent mccWorldLoadEvent = new MCCWorldLoadEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getWorld())
        );
        mccWorldLoadEvent.callEvent();
    }

    @EventHandler
    public void handle(WorldSaveEvent event) {
        MCCWorldSaveEvent mccWorldSaveEvent = new MCCWorldSaveEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getWorld())
        );
        mccWorldSaveEvent.callEvent();
    }

    @EventHandler
    public void handle(WorldUnloadEvent event) {
        MCCWorldUnloadEvent mccWorldUnloadEvent = new MCCWorldUnloadEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getWorld()),
                event.isCancelled()
        );
        if (mccWorldUnloadEvent.callEvent()) event.setCancelled(true);
    }
}
