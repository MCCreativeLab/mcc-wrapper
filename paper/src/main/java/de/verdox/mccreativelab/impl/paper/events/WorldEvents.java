package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.world.MCCWorldInitEvent;
import de.verdox.mccreativelab.wrapper.event.world.MCCWorldLoadEvent;
import de.verdox.mccreativelab.wrapper.event.world.MCCWorldSaveEvent;
import de.verdox.mccreativelab.wrapper.event.world.MCCWorldUnloadEvent;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.*;

public class WorldEvents extends EventBase {
    // without MCCWorldEvent

    @EventHandler(ignoreCancelled = true)
    public void handle(WorldInitEvent event) {
        callEvent(event, new MCCWorldInitEvent(
                wrap(event.getWorld())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(WorldLoadEvent event) {
        callEvent(event, new MCCWorldLoadEvent(
                wrap(event.getWorld())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(WorldSaveEvent event) {
        callEvent(event, new MCCWorldSaveEvent(
                wrap(event.getWorld())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(WorldUnloadEvent event) {
        callEvent(event, new MCCWorldUnloadEvent(
                wrap(event.getWorld()),
                event.isCancelled()
        ));
    }
}
