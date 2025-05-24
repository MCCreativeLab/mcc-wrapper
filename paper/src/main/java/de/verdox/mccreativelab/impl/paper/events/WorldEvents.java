package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.event.world.MCCWorldInitEvent;
import de.verdox.mccreativelab.wrapper.event.world.MCCWorldLoadEvent;
import de.verdox.mccreativelab.wrapper.event.world.MCCWorldSaveEvent;
import de.verdox.mccreativelab.wrapper.event.world.MCCWorldUnloadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.*;

public class WorldEvents extends EventBase {
    // without MCCWorldEvent

    @EventHandler(ignoreCancelled = true)
    public void handle(WorldInitEvent event) {
        callEvent(event, new MCCWorldInitEvent(
                BukkitAdapter.toMcc(event.getWorld())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(WorldLoadEvent event) {
        callEvent(event, new MCCWorldLoadEvent(
                BukkitAdapter.toMcc(event.getWorld())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(WorldSaveEvent event) {
        callEvent(event, new MCCWorldSaveEvent(
                BukkitAdapter.toMcc(event.getWorld())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(WorldUnloadEvent event) {
        callEvent(event, new MCCWorldUnloadEvent(
                BukkitAdapter.toMcc(event.getWorld()),
                event.isCancelled()
        ));
    }
}
