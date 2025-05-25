package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.event.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;

public class EntityEvents extends EventBase {

    // TODO: without MCCEntityEvent, MCCEntityCombustEvent

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityDismountEvent event) {
        callEvent(event, new MCCEntityDismountEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getDismounted()),
                event.isCancellable()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityDropItemEvent event) {
        callEvent(event, new MCCEntityDropItemEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                BukkitAdapter.toMcc(event.getItemDrop()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityAirChangeEvent event) {
        callEvent(event, new MCCEntityAirChangeEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.getAmount(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityBreakDoorEvent event) {
        callEvent(event, new MCCEntityBreakDoorEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                BukkitAdapter.toMcc(event.getBlock()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getBlockData())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityChangeBlockEvent event) {
        callEvent(event, new MCCEntityChangeBlockEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                BukkitAdapter.toMcc(event.getBlock()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getTo().createBlockData())
        ));
    }

    public static class EntityEnterEvents extends EventBase {

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityEnterBlockEvent event) {
            callEvent(event, new MCCEntityEnterBlockEvent(
                    BukkitAdapter.toMcc(event.getEntity()),
                    BukkitAdapter.toMcc(event.getBlock()),
                    event.isCancelled()
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityEnterLoveModeEvent event) {
            callEvent(event, new MCCEntityEnterLoveModeEvent(
                    BukkitAdapter.toMcc(event.getEntity()),
                    event.isCancelled(),
                    BukkitAdapter.toMcc(event.getHumanEntity()),
                    event.getTicksInLove()
            ));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityInteractEvent event) {
        callEvent(event, new MCCEntityInteractEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                BukkitAdapter.toMcc(event.getBlock()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityMountEvent event) {
        callEvent(event, new MCCEntityMountEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getMount())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityPickupItemEvent event) {
        callEvent(event, new MCCEntityPickupItemEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                BukkitAdapter.toMcc(event.getItem()),
                event.isCancelled(),
                event.getRemaining()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityPlaceEvent event) {
        callEvent(event, new MCCEntityPlaceEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getBlock()),
                BukkitAdapter.toMcc(event.getBlockFace()),
                BukkitAdapter.toMcc(event.getHand())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityResurrectEvent event) {
        callEvent(event, new MCCEntityResurrectEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getHand())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntitySpawnEvent event) {
        callEvent(event, new MCCEntitySpawnEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityTeleportEvent event) {
        callEvent(event, new MCCEntityTeleportEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getFrom()),
                BukkitAdapter.toMcc(event.getTo())
        ));
    }

    public static class CombustEvents extends EventBase {

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityCombustByBlockEvent event) {
            callEvent(event, new MCCEntityCombustByBlockEvent(
                    BukkitAdapter.toMcc(event.getEntity()),
                    event.getDuration(),
                    event.isCancelled(),
                    BukkitAdapter.toMcc(event.getCombuster())
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityCombustByEntityEvent event) {
            callEvent(event, new MCCEntityCombustByEntityEvent(
                    BukkitAdapter.toMcc(event.getEntity()),
                    event.getDuration(),
                    event.isCancelled(),
                    BukkitAdapter.toMcc(event.getCombuster())
            ));
        }
    }

    public static class ToggleEvents extends EventBase {

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityToggleSwimEvent event) {
            callEvent(event, new MCCEntityToggleSwimEvent(
                    BukkitAdapter.toMcc(event.getEntity()),
                    event.isCancelled(),
                    event.isSwimming()
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityToggleGlideEvent event) {
            callEvent(event, new MCCEntityToggleGlideEvent(
                    BukkitAdapter.toMcc(event.getEntity()),
                    event.isCancelled(),
                    event.isGliding()
            ));
        }
    }
}