package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.event.entity.MCCProjectileHitEvent;
import de.verdox.mccreativelab.wrapper.event.entity.MCCProjectileLaunchEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileEvents extends EventBase {

    @EventHandler(ignoreCancelled = true)
    public void handle(ProjectileHitEvent event) {
        callEvent(event, new MCCProjectileHitEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                BukkitAdapter.toMcc(event.getHitEntity()),
                BukkitAdapter.toMcc(event.getHitBlock()),
                BukkitAdapter.toMcc(event.getHitBlockFace()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ProjectileLaunchEvent event) {
        // TODO: duplicate: public MCCProjectileLaunchEvent(MCCEntity entity, boolean canceled, boolean cancelled){
        callEvent(event, new MCCProjectileLaunchEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                event.isCancelled()
        ));
    }
}
