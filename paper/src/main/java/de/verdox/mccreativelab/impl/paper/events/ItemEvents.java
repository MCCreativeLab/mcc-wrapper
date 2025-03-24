package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.entity.MCCItemDespawnEvent;
import de.verdox.mccreativelab.wrapper.event.entity.MCCItemMergeEvent;
import de.verdox.mccreativelab.wrapper.event.entity.MCCItemSpawnEvent;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemEvents implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void handle(ItemDespawnEvent event) {
        MCCItemDespawnEvent mccItemDespawnEvent = new MCCItemDespawnEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getLocation())
        );

        if (mccItemDespawnEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ItemMergeEvent event) {
        MCCItemMergeEvent mccItemMergeEvent = new MCCItemMergeEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getTarget())
        );

        if (mccItemMergeEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ItemSpawnEvent event) {
        MCCItemSpawnEvent mccItemSpawnEvent = new MCCItemSpawnEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled()
        );

        if (mccItemSpawnEvent.callEvent()) event.setCancelled(true);
    }
}
