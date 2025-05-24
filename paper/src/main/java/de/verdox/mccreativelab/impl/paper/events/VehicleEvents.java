package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.event.vehicle.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.*;

public class VehicleEvents extends EventBase {

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleBlockCollisionEvent event) {
        callEvent(event, new MCCVehicleCollisionEvent(
                BukkitAdapter.toMcc(event.getVehicle())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleCreateEvent event) {
        callEvent(event, new MCCVehicleCreateEvent(
                BukkitAdapter.toMcc(event.getVehicle()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleDamageEvent event) {
        callEvent(event, new MCCVehicleDamageEvent(
                BukkitAdapter.toMcc(event.getVehicle()),
                BukkitAdapter.toMcc(event.getAttacker()),
                event.getDamage(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleDestroyEvent event) {
        callEvent(event, new MCCVehicleDestroyEvent(
                BukkitAdapter.toMcc(event.getVehicle()),
                BukkitAdapter.toMcc(event.getAttacker()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleEnterEvent event) {
        callEvent(event, new MCCVehicleEnterEvent(
                BukkitAdapter.toMcc(event.getVehicle()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getEntered())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleEntityCollisionEvent event) {
        callEvent(event, new MCCVehicleEntityCollisionEvent(
                BukkitAdapter.toMcc(event.getVehicle()),
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                ReflectionUtils.readFieldFromClass(event, "cancelledPickup", new TypeToken<>() {}),
                ReflectionUtils.readFieldFromClass(event, "cancelledCollision", new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleExitEvent event) {
        callEvent(event, new MCCVehicleExitEvent(
                BukkitAdapter.toMcc(event.getVehicle()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getExited()),
                event.isCancellable()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleMoveEvent event) {
        callEvent(event, new MCCVehicleMoveEvent(
                BukkitAdapter.toMcc(event.getVehicle()),
                BukkitAdapter.toMcc(event.getFrom()),
                BukkitAdapter.toMcc(event.getTo())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(VehicleUpdateEvent event) {
        callEvent(event, new MCCVehicleUpdateEvent(
                BukkitAdapter.toMcc(event.getVehicle())
        ));
    }
}
