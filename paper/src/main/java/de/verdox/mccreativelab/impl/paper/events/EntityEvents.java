package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;

public class EntityEvents extends EventBase {

    // TODO: without MCCEntityEvent, MCCEntityCombustEvent

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityDismountEvent event) {
        callEvent(event, new MCCEntityDismountEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                wrap(event.getDismounted()),
                event.isCancellable()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityDropItemEvent event) {
        callEvent(event, new MCCEntityDropItemEvent(
                wrap(event.getEntity()),
                wrap(event.getItemDrop()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityAirChangeEvent event) {
        callEvent(event, new MCCEntityAirChangeEvent(
                wrap(event.getEntity()),
                event.getAmount(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityBreakDoorEvent event) {
        callEvent(event, new MCCEntityBreakDoorEvent(
                wrap(event.getEntity()),
                wrap(event.getBlock()),
                event.isCancelled(),
                wrap(event.getTo())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityChangeBlockEvent event) {
        callEvent(event, new MCCEntityChangeBlockEvent(
                wrap(event.getEntity()),
                wrap(event.getBlock()),
                event.isCancelled(),
                wrap(event.getTo())
        ));
    }

    public static class EntityEnterEvents extends EventBase {

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityEnterBlockEvent event) {
            callEvent(event, new MCCEntityEnterBlockEvent(
                    wrap(event.getEntity()),
                    wrap(event.getBlock()),
                    event.isCancelled()
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityEnterLoveModeEvent event) {
            callEvent(event, new MCCEntityEnterLoveModeEvent(
                    wrap(event.getEntity()),
                    event.isCancelled(),
                    wrap(event.getHumanEntity()),
                    event.getTicksInLove()
            ));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityInteractEvent event) {
        callEvent(event, new MCCEntityInteractEvent(
                wrap(event.getEntity()),
                wrap(event.getBlock()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityMountEvent event) {
        callEvent(event, new MCCEntityMountEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                wrap(event.getMount())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityPickupItemEvent event) {
        callEvent(event, new MCCEntityPickupItemEvent(
                wrap(event.getEntity()),
                wrap(event.getItem()),
                event.isCancelled(),
                event.getRemaining()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityPlaceEvent event) {
        callEvent(event, new MCCEntityPlaceEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                wrap(event.getPlayer()),
                wrap(event.getBlock()),
                wrap(event.getBlockFace()),
                wrap(event.getHand())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityResurrectEvent event) {
        callEvent(event, new MCCEntityResurrectEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                wrap(event.getHand())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntitySpawnEvent event) {
        callEvent(event, new MCCEntitySpawnEvent(
                wrap(event.getEntity()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityTeleportEvent event) {
        callEvent(event, new MCCEntityTeleportEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                wrap(event.getFrom()),
                wrap(event.getTo())
        ));
    }

    public static class CombustEvents extends EventBase {

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityCombustByBlockEvent event) {
            callEvent(event, new MCCEntityCombustByBlockEvent(
                    wrap(event.getEntity()),
                    event.getDuration(),
                    event.isCancelled(),
                    wrap(event.getCombuster())
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityCombustByEntityEvent event) {
            callEvent(event, new MCCEntityCombustByBlockEvent(
                    wrap(event.getEntity()),
                    event.getDuration(),
                    event.isCancelled(),
                    wrap(event.getCombuster())
            ));
        }
    }

    public static class ToggleEvents extends EventBase {

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityToggleSwimEvent event) {
            callEvent(event, new MCCEntityToggleSwimEvent(
                    wrap(event.getEntity()),
                    event.isCancelled(),
                    event.isSwimming()
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityToggleGlideEvent event) {
            callEvent(event, new MCCEntityToggleGlideEvent(
                    wrap(event.getEntity()),
                    event.isCancelled(),
                    event.isGliding()
            ));
        }
    }
}