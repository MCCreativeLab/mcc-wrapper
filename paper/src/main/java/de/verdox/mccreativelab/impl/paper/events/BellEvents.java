package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.event.block.MCCBellResonateEvent;
import de.verdox.mccreativelab.wrapper.event.block.MCCBellRingEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BellResonateEvent;
import org.bukkit.event.block.BellRingEvent;

import java.util.ArrayList;

public class BellEvents extends EventBase {

    @EventHandler(ignoreCancelled = true)
    public void handle(BellResonateEvent event) {
        callEvent(event, new MCCBellResonateEvent(
                BukkitAdapter.toMcc(event.getBlock()),
                BukkitAdapter.toMcc(event.getResonatedEntities(), BukkitAdapter::toMcc, ArrayList::new)
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BellRingEvent event) {
        callEvent(event, new MCCBellRingEvent(
               BukkitAdapter.toMcc(event.getBlock()),
               BukkitAdapter.toMcc(event.getDirection()),
               BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled()
        ));
    }
}
