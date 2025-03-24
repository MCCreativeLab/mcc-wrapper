package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.block.MCCBellResonateEvent;
import de.verdox.mccreativelab.wrapper.event.block.MCCBellRingEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BellResonateEvent;
import org.bukkit.event.block.BellRingEvent;

public class BellEvents extends EventBase {

    @EventHandler(ignoreCancelled = true)
    public void handle(BellResonateEvent event) {
        callEvent(event, new MCCBellResonateEvent(
                wrap(event.getBlock()),
                wrap(event.getResonatedEntities())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BellRingEvent event) {
        callEvent(event, new MCCBellRingEvent(
                wrap(event.getBlock()),
                wrap(event.getDirection()),
                wrap(event.getEntity()),
                event.isCancelled()
        ));
    }
}
