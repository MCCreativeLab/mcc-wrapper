package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.event.vehicle.*;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.*;

public class VehicleEvents extends EventBase {

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleBlockCollisionEvent event) {
        callEvent(event, new MCCVehicleCollisionEvent(
                wrap(event.getVehicle(), new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleCreateEvent event) {
        callEvent(event, new MCCVehicleCreateEvent(
                wrap(event.getVehicle(), new TypeToken<>() {}),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleDamageEvent event) {
        callEvent(event, new MCCVehicleDamageEvent(
                wrap(event.getVehicle(), new TypeToken<>() {}),
                wrap(event.getAttacker(), new TypeToken<>() {}),
                event.getDamage(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleDestroyEvent event) {
        callEvent(event, new MCCVehicleDestroyEvent(
                wrap(event.getVehicle(), new TypeToken<>() {}),
                wrap(event.getAttacker(), new TypeToken<>() {}),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleEnterEvent event) {
        callEvent(event, new MCCVehicleEnterEvent(
                wrap(event.getVehicle(), new TypeToken<>() {}),
                event.isCancelled(),
                wrap(event.getEntered(), new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleEntityCollisionEvent event) {
        callEvent(event, new MCCVehicleEntityCollisionEvent(
                wrap(event.getVehicle(), new TypeToken<>() {}),
                wrap(event.getEntity(), new TypeToken<>() {}),
                event.isCancelled(),
                ReflectionUtils.readFieldFromClass(event, "cancelledPickup", new TypeToken<>() {}),
                ReflectionUtils.readFieldFromClass(event, "cancelledCollision", new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleExitEvent event) {
        callEvent(event, new MCCVehicleExitEvent(
                wrap(event.getVehicle(), new TypeToken<>() {}),
                event.isCancelled(),
                wrap(event.getExited(), new TypeToken<>() {}),
                event.isCancellable()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleMoveEvent event) {
        callEvent(event, new MCCVehicleMoveEvent(
                wrap(event.getVehicle(), new TypeToken<>() {}),
                wrap(event.getFrom(), new TypeToken<>() {}),
                wrap(event.getTo(), new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleUpdateEvent event) {
        callEvent(event, new MCCVehicleUpdateEvent(
                wrap(event.getVehicle(), new TypeToken<>() {})
        ));
    }
}
