package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.event.vehicle.*;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.*;

public class VehicleEvents extends EventBase {

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleBlockCollisionEvent event) {
        callEvent(event, new MCCVehicleCollisionEvent(
                wrap(event.getVehicle())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleCreateEvent event) {
        callEvent(event, new MCCVehicleCreateEvent(
                wrap(event.getVehicle()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleDamageEvent event) {
        callEvent(event, new MCCVehicleDamageEvent(
                wrap(event.getVehicle()),
                wrap(event.getAttacker()),
                event.getDamage(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleDestroyEvent event) {
        callEvent(event, new MCCVehicleDestroyEvent(
                wrap(event.getVehicle()),
                wrap(event.getAttacker()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleEnterEvent event) {
        callEvent(event, new MCCVehicleEnterEvent(
                wrap(event.getVehicle()),
                event.isCancelled(),
                wrap(event.getEntered())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleEntityCollisionEvent event) {
        callEvent(event, new MCCVehicleEntityCollisionEvent(
                wrap(event.getVehicle()),
                wrap(event.getEntity()),
                event.isCancelled(),
                ReflectionUtils.readFieldFromClass(event, "cancelledPickup", new TypeToken<>() {}),
                ReflectionUtils.readFieldFromClass(event, "cancelledCollision", new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleExitEvent event) {
        callEvent(event, new MCCVehicleExitEvent(
                wrap(event.getVehicle()),
                event.isCancelled(),
                wrap(event.getExited()),
                event.isCancellable()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleMoveEvent event) {
        callEvent(event, new MCCVehicleMoveEvent(
                wrap(event.getVehicle()),
                wrap(event.getFrom()),
                wrap(event.getTo())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleUpdateEvent event) {
        callEvent(event, new MCCVehicleUpdateEvent(
                wrap(event.getVehicle())
        ));
    }
}
