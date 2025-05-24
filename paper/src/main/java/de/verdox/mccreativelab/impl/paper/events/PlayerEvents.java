package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.event.player.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.*;

import java.util.HashSet;
import java.util.Set;

public class PlayerEvents extends EventBase {

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerAttemptPickupItemEvent event) {
        callEvent(event, new MCCPlayerAttemptPickupItemEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getItem()),
                event.getRemaining(),
                event.getFlyAtPlayer(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerBedLeaveEvent event) {
        callEvent(event, new MCCPlayerBedLeaveEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getBed()),
                ReflectionUtils.readFieldFromClass(event, "setBedSpawn", new TypeToken<>() {}),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerChangedWorldEvent event) {
        callEvent(event, new MCCPlayerChangedWorldEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getFrom())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerChannelEvent event) {
        callEvent(event, new MCCPlayerChannelEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.getChannel()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerCommandPreprocessEvent event) {
        callEvent(event, new MCCPlayerCommandPreprocessEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.isCancelled(),
                event.getMessage(),
                BukkitAdapter.toMcc(ReflectionUtils.readFieldFromClass(event, "recipients", new TypeToken<Set<Player>>() {}), BukkitAdapter::toMcc, HashSet::new)
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerCommandSendEvent event) {
        callEvent(event, new MCCPlayerCommandSendEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.getCommands()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerDropItemEvent event) {
        callEvent(event, new MCCPlayerDropItemEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getItemDrop()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerEggThrowEvent event) {
        callEvent(event, new MCCPlayerEggThrowEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getEgg()),
                event.isHatching(),
                BukkitAdapter.toMcc(event.getHatchingType()),
                event.getNumHatches()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerExpChangeEvent event) {
        callEvent(event, new MCCPlayerExpChangeEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getSource()),
                event.getAmount()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerHideEntityEvent event) {
        callEvent(event, new MCCPlayerHideEntityEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getEntity())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerInteractEntityEvent event) {
        callEvent(event, new MCCPlayerInteractEntityEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getRightClicked()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getHand())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerItemHeldEvent event) {
        callEvent(event, new MCCPlayerItemHeldEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.isCancelled(),
                event.getPreviousSlot(),
                event.getNewSlot()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerJoinEvent event) {
        callEvent(event, new MCCPlayerJoinEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.joinMessage()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerLevelChangeEvent event) {
        callEvent(event, new MCCPlayerLevelChangeEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.getOldLevel(),
                event.getNewLevel()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerLocaleChangeEvent event) {
        callEvent(event, new MCCPlayerLocaleChangeEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.locale().toString(),
                event.locale()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerMoveEvent event) {
        callEvent(event, new MCCPlayerMoveEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getFrom()),
                BukkitAdapter.toMcc(event.getTo())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerRegisterChannelEvent event) {
        callEvent(event, new MCCPlayerRegisterChannelEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.getChannel()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerShowEntityEvent event) {
        callEvent(event, new MCCPlayerShowEntityEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getEntity())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerToggleFlightEvent event) {
        callEvent(event, new MCCPlayerToggleFlightEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.isFlying(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerToggleSneakEvent event) {
        callEvent(event, new MCCPlayerToggleSneakEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.isSneaking(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerToggleSprintEvent event) {
        callEvent(event, new MCCPlayerToggleSprintEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.isSprinting(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerUnregisterChannelEvent event) {
        callEvent(event, new MCCPlayerUnregisterChannelEvent(
                BukkitAdapter.toMcc(event.getPlayer()),
                event.getChannel()
        ));
    }
}
