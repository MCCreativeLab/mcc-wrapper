package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.entity.MCCEntityCombustByBlockEvent;
import de.verdox.mccreativelab.wrapper.event.entity.MCCEntityToggleGlideEvent;
import de.verdox.mccreativelab.wrapper.event.entity.MCCEntityToggleSwimEvent;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;

public class EntityEvents implements Listener {

    // without MCCEntityEvent, MCCEntityCombustEvent

    public static class EntityCombustEvents implements Listener {

        @EventHandler
        public void handle(EntityCombustByBlockEvent event) {
            MCCEntityCombustByBlockEvent mccEntityCombustByBlockEvent = new MCCEntityCombustByBlockEvent(
                    MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                    event.getDuration(),
                    event.isCancelled(),
                    MCCPlatform.getInstance().getConversionService().wrap(event.getCombuster())
            );

            if (mccEntityCombustByBlockEvent.callEvent()) event.setCancelled(true);
        }

        @EventHandler
        public void handle(EntityCombustByEntityEvent event) {
            MCCEntityCombustByBlockEvent mccEntityCombustByBlockEvent = new MCCEntityCombustByBlockEvent(
                    MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                    event.getDuration(),
                    event.isCancelled(),
                    MCCPlatform.getInstance().getConversionService().wrap(event.getCombuster())
            );

            if (mccEntityCombustByBlockEvent.callEvent()) event.setCancelled(true);
        }
    }

// TODO: MCCEntityAirChangeEvent
// TODO: MCCEntityBreakDoorEvent
// TODO: MCCEntityChangeBlockEvent
// TODO: MCCEntityDismountEvent
// TODO: MCCEntityDropItemEvent

    public static class EntityEnterEvents implements Listener {
        // TODO: MCCEntityEnterBlockEvent
        // TODO: MCCEntityEnterLoveModeEvent
    }

// TODO: MCCEntityInteractEvent
// TODO: MCCEntityMountEvent
// TODO: MCCEntityPickupItemEvent
// TODO: MCCEntityPlaceEvent
// TODO: MCCEntityResurrectEvent
// TODO: MCCEntitySpawnEvent
// TODO: MCCEntityTeleportEvent

    public static class EntityToggleEvents implements Listener {

        @EventHandler
        public void handle(EntityToggleSwimEvent event) {
            MCCEntityToggleSwimEvent mccEntityToggleSwimEvent = new MCCEntityToggleSwimEvent(
                    MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                    event.isCancelled(),
                    event.isSwimming()
            );

            mccEntityToggleSwimEvent.callEvent();
        }

        @EventHandler
        public void handle(EntityToggleGlideEvent event) {
            MCCEntityToggleGlideEvent mccEntityToggleGlideEvent = new MCCEntityToggleGlideEvent(
                    MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                    event.isCancelled(),
                    event.isGliding()
            );

            if (mccEntityToggleGlideEvent.callEvent()) event.setCancelled(true);
        }
    }
}
