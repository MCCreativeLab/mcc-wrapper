package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.block.MCCBellResonateEvent;
import de.verdox.mccreativelab.wrapper.event.block.MCCBellRingEvent;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BellResonateEvent;
import org.bukkit.event.block.BellRingEvent;

public class BellEvents implements Listener {

    @EventHandler
    public void handle(BellResonateEvent event) {
        MCCBellResonateEvent mccBellResonateEvent = new MCCBellResonateEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getResonatedEntities())
        );

        mccBellResonateEvent.callEvent();
    }

    @EventHandler
    public void handle(BellRingEvent event) {
        MCCBellRingEvent mccBellRingEvent = new MCCBellRingEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getDirection()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled()
        );

        if (mccBellRingEvent.callEvent()) event.setCancelled(true);
    }
}
