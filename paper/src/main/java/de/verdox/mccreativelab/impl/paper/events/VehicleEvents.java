package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.event.vehicle.*;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.*;

public class VehicleEvents implements Listener {
    // without MCCVehicleEvent

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleBlockCollisionEvent event) {
        MCCVehicleCollisionEvent mccVehicleCollisionEvent = new MCCVehicleCollisionEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getVehicle())
        );

        mccVehicleCollisionEvent.callEvent();
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleCreateEvent event) {
        MCCVehicleCreateEvent mccVehicleCreateEvent = new MCCVehicleCreateEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getVehicle()),
                event.isCancelled()
        );

        if (mccVehicleCreateEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleDamageEvent event) {
        MCCVehicleDamageEvent mccVehicleDamageEvent = new MCCVehicleDamageEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getVehicle()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getAttacker()),
                event.getDamage(),
                event.isCancelled()
        );

        if (mccVehicleDamageEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleDestroyEvent event) {
        MCCVehicleDestroyEvent mccVehicleDestroyEvent = new MCCVehicleDestroyEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getVehicle()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getAttacker()),
                event.isCancelled()
        );

        if (mccVehicleDestroyEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleEnterEvent event) {
        MCCVehicleEnterEvent mccVehicleEnterEvent = new MCCVehicleEnterEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getVehicle()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntered())
        );

        if (mccVehicleEnterEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleEntityCollisionEvent event) {
        MCCVehicleEntityCollisionEvent mccVehicleEntityCollisionEvent = new MCCVehicleEntityCollisionEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getVehicle()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                ReflectionUtils.readFieldFromClass(event, "cancelledPickup", new TypeToken<>() {}),
                ReflectionUtils.readFieldFromClass(event, "cancelledCollision", new TypeToken<>() {})
        );

        if (mccVehicleEntityCollisionEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleExitEvent event) {
        MCCVehicleExitEvent mccVehicleExitEvent = new MCCVehicleExitEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getVehicle()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getExited()),
                event.isCancellable()
        );

        if (mccVehicleExitEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleMoveEvent event) {
        MCCVehicleMoveEvent mccVehicleMoveEvent = new MCCVehicleMoveEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getVehicle()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getFrom()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getTo())
        );

        mccVehicleMoveEvent.callEvent();
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleUpdateEvent event) {
        MCCVehicleUpdateEvent mccVehicleUpdateEvent = new MCCVehicleUpdateEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getVehicle())
        );

        mccVehicleUpdateEvent.callEvent();
    }
}
