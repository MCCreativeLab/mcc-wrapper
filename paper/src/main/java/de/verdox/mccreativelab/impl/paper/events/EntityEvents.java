package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.event.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;

public class EntityEvents extends EventBase {

    // TODO: without MCCEntityEvent, MCCEntityCombustEvent

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityDismountEvent event) {
        callEvent(event, new MCCEntityDismountEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                event.isCancelled(),
                wrap(event.getDismounted(), new TypeToken<>() {}),
                event.isCancellable()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityDropItemEvent event) {
        callEvent(event, new MCCEntityDropItemEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                wrap(event.getItemDrop(), new TypeToken<>() {}),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityAirChangeEvent event) {
        callEvent(event, new MCCEntityAirChangeEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                event.getAmount(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityBreakDoorEvent event) {
        callEvent(event, new MCCEntityBreakDoorEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                wrap(event.getBlock(), new TypeToken<>() {}),
                event.isCancelled(),
                wrap(event.getTo(), new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityChangeBlockEvent event) {
        callEvent(event, new MCCEntityChangeBlockEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                wrap(event.getBlock(), new TypeToken<>() {}),
                event.isCancelled(),
                wrap(event.getTo().createBlockData(), new TypeToken<>() {})
        ));
    }

    public static class EntityEnterEvents extends EventBase {

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityEnterBlockEvent event) {
            callEvent(event, new MCCEntityEnterBlockEvent(
                    wrap(event.getEntity(), new TypeToken<>() {}),
                    wrap(event.getBlock(), new TypeToken<>() {}),
                    event.isCancelled()
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityEnterLoveModeEvent event) {
            callEvent(event, new MCCEntityEnterLoveModeEvent(
                    wrap(event.getEntity(), new TypeToken<>() {}),
                    event.isCancelled(),
                    wrap(event.getHumanEntity(), new TypeToken<>() {}),
                    event.getTicksInLove()
            ));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityInteractEvent event) {
        callEvent(event, new MCCEntityInteractEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                wrap(event.getBlock(), new TypeToken<>() {}),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityMountEvent event) {
        callEvent(event, new MCCEntityMountEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                event.isCancelled(),
                wrap(event.getMount(), new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityPickupItemEvent event) {
        callEvent(event, new MCCEntityPickupItemEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                wrap(event.getItem(), new TypeToken<>() {}),
                event.isCancelled(),
                event.getRemaining()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityPlaceEvent event) {
        callEvent(event, new MCCEntityPlaceEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                event.isCancelled(),
                wrap(event.getPlayer(), new TypeToken<>() {}),
                wrap(event.getBlock(), new TypeToken<>() {}),
                wrap(event.getBlockFace(), new TypeToken<>() {}),
                wrap(event.getHand(), new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityResurrectEvent event) {
        callEvent(event, new MCCEntityResurrectEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                event.isCancelled(),
                wrap(event.getHand(), new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntitySpawnEvent event) {
        callEvent(event, new MCCEntitySpawnEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityTeleportEvent event) {
        callEvent(event, new MCCEntityTeleportEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                event.isCancelled(),
                wrap(event.getFrom(), new TypeToken<>() {}),
                wrap(event.getTo(), new TypeToken<>() {})
        ));
    }

    public static class CombustEvents extends EventBase {

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityCombustByBlockEvent event) {
            callEvent(event, new MCCEntityCombustByBlockEvent(
                    wrap(event.getEntity(), new TypeToken<>() {}),
                    event.getDuration(),
                    event.isCancelled(),
                    wrap(event.getCombuster(), new TypeToken<>() {})
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityCombustByEntityEvent event) {
            callEvent(event, new MCCEntityCombustByBlockEvent(
                    wrap(event.getEntity(), new TypeToken<>() {}),
                    event.getDuration(),
                    event.isCancelled(),
                    wrap(event.getCombuster(), new TypeToken<>() {})
            ));
        }
    }

    public static class ToggleEvents extends EventBase {

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityToggleSwimEvent event) {
            callEvent(event, new MCCEntityToggleSwimEvent(
                    wrap(event.getEntity(), new TypeToken<>() {}),
                    event.isCancelled(),
                    event.isSwimming()
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(EntityToggleGlideEvent event) {
            callEvent(event, new MCCEntityToggleGlideEvent(
                    wrap(event.getEntity(), new TypeToken<>() {}),
                    event.isCancelled(),
                    event.isGliding()
            ));
        }
    }
}