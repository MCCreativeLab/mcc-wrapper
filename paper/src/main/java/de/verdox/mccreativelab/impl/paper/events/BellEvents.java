package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.event.block.MCCBellResonateEvent;
import de.verdox.mccreativelab.wrapper.event.block.MCCBellRingEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BellResonateEvent;
import org.bukkit.event.block.BellRingEvent;

public class BellEvents extends EventBase {

    @EventHandler(ignoreCancelled = true)
    public void handle(BellResonateEvent event) {
        callEvent(event, new MCCBellResonateEvent(
                wrap(event.getBlock(), new TypeToken<>() {}),
                wrap(event.getResonatedEntities(), new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BellRingEvent event) {
        callEvent(event, new MCCBellRingEvent(
                wrap(event.getBlock(), new TypeToken<>() {}),
                wrap(event.getDirection(), new TypeToken<>() {}),
                wrap(event.getEntity(), new TypeToken<>() {}),
                event.isCancelled()
        ));
    }
}
