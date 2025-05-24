package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.MCCEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * The event base class implements all shared logic that the event listeners share.
 */
public class EventBase implements Listener {
    protected void callEvent(Event bukkitEvent, MCCEvent mccEvent){
        boolean result = mccEvent.callEvent();
        if(bukkitEvent instanceof Cancellable cancellable) {
            cancellable.setCancelled(!result);
        }
    }
}
