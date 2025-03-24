package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.entity.*;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

public class EntityEvents implements Listener {

    // without MCCEntityEvent, MCCEntityCombustEvent

    public static class EntityCombustEvents implements Listener {

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityCombustByBlockEvent event) {
            MCCEntityCombustByBlockEvent mccEntityCombustByBlockEvent = new MCCEntityCombustByBlockEvent(
                    MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                    event.getDuration(),
                    event.isCancelled(),
                    MCCPlatform.getInstance().getConversionService().wrap(event.getCombuster())
            );

            if (mccEntityCombustByBlockEvent.callEvent()) event.setCancelled(true);
        }

        @EventHandler(ignoreCancelled = true)
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

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityDismountEvent event) {
        MCCEntityDismountEvent mccEntityDismountEvent = new MCCEntityDismountEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getDismounted()),
                event.isCancellable()
        );

        if (mccEntityDismountEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityDropItemEvent event) {
        MCCEntityDropItemEvent mccEntityDropItemEvent = new MCCEntityDropItemEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getItemDrop()),
                event.isCancelled()
        );

        if (mccEntityDropItemEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityAirChangeEvent event) {
        MCCEntityAirChangeEvent mccEntityAirChangeEvent = new MCCEntityAirChangeEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.getAmount(),
                event.isCancelled()
        );

        if (mccEntityAirChangeEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityBreakDoorEvent event) {
        MCCEntityBreakDoorEvent mccEntityBreakDoorEvent = new MCCEntityBreakDoorEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getTo())
        );

        if (mccEntityBreakDoorEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityChangeBlockEvent event) {
        MCCEntityChangeBlockEvent mccEntityChangeBlockEvent = new MCCEntityChangeBlockEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getTo())
        );

        if (mccEntityChangeBlockEvent.callEvent()) event.setCancelled(true);
    }

    public static class EntityEnterEvents implements Listener {

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityEnterBlockEvent event) {
            MCCEntityEnterBlockEvent mccEntityEnterBlockEvent = new MCCEntityEnterBlockEvent(
                    MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                    MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                    event.isCancelled()
            );

            if (mccEntityEnterBlockEvent.callEvent()) event.setCancelled(true);
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityEnterLoveModeEvent event) {
            MCCEntityEnterLoveModeEvent mccEntityEnterLoveModeEvent = new MCCEntityEnterLoveModeEvent(
                    MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                    event.isCancelled(),
                    MCCPlatform.getInstance().getConversionService().wrap(event.getHumanEntity()),
                    event.getTicksInLove()
            );

            if (mccEntityEnterLoveModeEvent.callEvent()) event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityInteractEvent event) {
        MCCEntityInteractEvent mccEntityInteractEvent = new MCCEntityInteractEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                event.isCancelled()
        );

        if (mccEntityInteractEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityMountEvent event) {
        MCCEntityMountEvent mccEntityMountEvent = new MCCEntityMountEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getMount())
        );

        if (mccEntityMountEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityPickupItemEvent event) {
        MCCEntityPickupItemEvent mccEntityPickupItemEvent = new MCCEntityPickupItemEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getItem()),
                event.isCancelled(),
                event.getRemaining()
        );

        if (mccEntityPickupItemEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityPlaceEvent event) {
        MCCEntityPlaceEvent mccEntityPlaceEvent = new MCCEntityPlaceEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlockFace()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHand())
        );

        if (mccEntityPlaceEvent.callEvent()) event.setCancelled(true);
    }


    @EventHandler(ignoreCancelled = true)
    public void handle(EntityResurrectEvent event) {
        MCCEntityResurrectEvent mccEntityResurrectEvent = new MCCEntityResurrectEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHand())
        );

        if (mccEntityResurrectEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntitySpawnEvent event) {
        MCCEntitySpawnEvent mccEntitySpawnEvent = new MCCEntitySpawnEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled()
        );

        if (mccEntitySpawnEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityTeleportEvent event) {
        MCCEntityTeleportEvent mccEntityTeleportEvent = new MCCEntityTeleportEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getFrom()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getTo())
        );

        if (mccEntityTeleportEvent.callEvent()) event.setCancelled(true);
    }

    public static class EntityToggleEvents implements Listener {

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityToggleSwimEvent event) {
            MCCEntityToggleSwimEvent mccEntityToggleSwimEvent = new MCCEntityToggleSwimEvent(
                    MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                    event.isCancelled(),
                    event.isSwimming()
            );

            mccEntityToggleSwimEvent.callEvent();
        }

        @EventHandler(ignoreCancelled = true)
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
