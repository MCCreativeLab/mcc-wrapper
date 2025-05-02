package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
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
                wrap(event.getEntity(), new TypeToken<>() {}),
                wrap(event.getHitEntity(), new TypeToken<>() {}),
                wrap(event.getHitBlock(), new TypeToken<>() {}),
                wrap(event.getHitBlockFace(), new TypeToken<>() {}),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ProjectileLaunchEvent event) {
        // TODO: duplicate: public MCCProjectileLaunchEvent(MCCEntity entity, boolean canceled, boolean cancelled){
        callEvent(event, new MCCProjectileLaunchEvent(
                wrap(event.getEntity(), new TypeToken<>() {}),
                event.isCancelled(),
                event.isCancelled()
        ));
    }
}
