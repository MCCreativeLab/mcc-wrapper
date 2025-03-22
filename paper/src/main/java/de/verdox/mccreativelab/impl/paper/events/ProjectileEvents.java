package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.entity.MCCProjectileHitEvent;
import de.verdox.mccreativelab.wrapper.event.entity.MCCProjectileLaunchEvent;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileEvents implements Listener {

    @EventHandler
    public void handle(ProjectileHitEvent event) {
        MCCProjectileHitEvent mccProjectileHitEvent = new MCCProjectileHitEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHitEntity()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHitBlock()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHitBlockFace()),
                event.isCancelled()
        );

        if (mccProjectileHitEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(ProjectileLaunchEvent event) {
        // TODO: duplicate: public MCCProjectileLaunchEvent(MCCEntity entity, boolean canceled, boolean cancelled){
        MCCProjectileLaunchEvent mccProjectileLaunchEvent = new MCCProjectileLaunchEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                event.isCancelled()
        );

        if (mccProjectileLaunchEvent.callEvent()) event.setCancelled(true);
    }
}
