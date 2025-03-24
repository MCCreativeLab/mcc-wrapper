package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.entity.MCCProjectileHitEvent;
import de.verdox.mccreativelab.wrapper.event.entity.MCCProjectileLaunchEvent;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileEvents extends EventBase {

    @EventHandler(ignoreCancelled = true)
    public void handle(ProjectileHitEvent event) {
        callEvent(event, new MCCProjectileHitEvent(
                wrap(event.getEntity()),
                wrap(event.getHitEntity()),
                wrap(event.getHitBlock()),
                wrap(event.getHitBlockFace()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ProjectileLaunchEvent event) {
        // TODO: duplicate: public MCCProjectileLaunchEvent(MCCEntity entity, boolean canceled, boolean cancelled){
        callEvent(event, new MCCProjectileLaunchEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                event.isCancelled()
        ));
    }
}
