package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.entity.MCCItemDespawnEvent;
import de.verdox.mccreativelab.wrapper.event.entity.MCCItemMergeEvent;
import de.verdox.mccreativelab.wrapper.event.entity.MCCItemSpawnEvent;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemEvents extends EventBase {

    @EventHandler(ignoreCancelled = true)
    public void handle(ItemDespawnEvent event) {
        callEvent(event, new MCCItemDespawnEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                wrap(event.getLocation())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ItemMergeEvent event) {
        callEvent(event, new MCCItemMergeEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                wrap(event.getTarget())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ItemSpawnEvent event) {
        callEvent(event, new MCCItemSpawnEvent(
                wrap(event.getEntity()),
                event.isCancelled()
        ));
    }
}