package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.event.MCCEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * The event base class implements all shared logic that the event listeners share.
 */
public class EventBase implements Listener {
    protected <F, T> T wrap(F nativeObject){
        return BukkitAdapter.wrap(nativeObject);
    }

    protected <F, T> T wrap(F nativeObject, Class<T> type){
        return BukkitAdapter.wrap(nativeObject, type);
    }

    protected <F, T> T wrap(F nativeObject, TypeToken<T> type){
        return BukkitAdapter.wrap(nativeObject, type);
    }

    protected <F, T> F unwrap(T apiObject){
        return BukkitAdapter.unwrap(apiObject);
    }

    protected void callEvent(Event bukkitEvent, MCCEvent mccEvent){
        boolean result = mccEvent.callEvent();
        if(bukkitEvent instanceof Cancellable cancellable) {
            cancellable.setCancelled(!result);
        }
    }
}
