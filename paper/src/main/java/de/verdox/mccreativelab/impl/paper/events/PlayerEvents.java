package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.event.player.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.*;

import java.util.Set;

public class PlayerEvents extends EventBase {

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerAttemptPickupItemEvent event) {
        callEvent(event, new MCCPlayerAttemptPickupItemEvent(
                wrap(event.getPlayer()),
                wrap(event.getItem()),
                event.getRemaining(),
                event.getFlyAtPlayer(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerBedLeaveEvent event) {
        callEvent(event, new MCCPlayerBedLeaveEvent(
                wrap(event.getPlayer()),
                wrap(event.getBed()),
                ReflectionUtils.readFieldFromClass(event, "setBedSpawn", new TypeToken<>() {}),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerChangedWorldEvent event) {
        callEvent(event, new MCCPlayerChangedWorldEvent(
                wrap(event.getPlayer()),
                wrap(event.getFrom())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerChannelEvent event) {
        callEvent(event, new MCCPlayerChannelEvent(
                wrap(event.getPlayer()),
                event.getChannel()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerCommandPreprocessEvent event) {
        callEvent(event, new MCCPlayerCommandPreprocessEvent(
                wrap(event.getPlayer()),
                event.isCancelled(),
                event.getMessage(),
                wrap(ReflectionUtils.readFieldFromClass(event, "recipients", new TypeToken<Set<Player>>() {}))
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerCommandSendEvent event) {
        callEvent(event, new MCCPlayerCommandSendEvent(
                wrap(event.getPlayer()),
                event.getCommands()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerDropItemEvent event) {
        callEvent(event, new MCCPlayerDropItemEvent(
                wrap(event.getPlayer()),
                wrap(event.getItemDrop()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerEggThrowEvent event) {
        callEvent(event, new MCCPlayerEggThrowEvent(
                wrap(event.getPlayer()),
                wrap(event.getEgg()),
                event.isHatching(),
                wrap(event.getHatchingType()),
                event.getNumHatches()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerExpChangeEvent event) {
        callEvent(event, new MCCPlayerExpChangeEvent(
                wrap(event.getPlayer()),
                wrap(event.getSource()),
                event.getAmount()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerHideEntityEvent event) {
        callEvent(event, new MCCPlayerHideEntityEvent(
                wrap(event.getPlayer()),
                wrap(event.getEntity())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerInteractEntityEvent event) {
        callEvent(event, new MCCPlayerInteractEntityEvent(
                wrap(event.getPlayer()),
                wrap(event.getRightClicked()),
                event.isCancelled(),
                wrap(event.getHand())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerItemHeldEvent event) {
        callEvent(event, new MCCPlayerItemHeldEvent(
                wrap(event.getPlayer()),
                event.isCancelled(),
                event.getPreviousSlot(),
                event.getNewSlot()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerJoinEvent event) {
        callEvent(event, new MCCPlayerJoinEvent(
                wrap(event.getPlayer()),
                event.joinMessage()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerLevelChangeEvent event) {
        callEvent(event, new MCCPlayerLevelChangeEvent(
                wrap(event.getPlayer()),
                event.getOldLevel(),
                event.getNewLevel()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerLocaleChangeEvent event) {
        callEvent(event, new MCCPlayerLocaleChangeEvent(
                wrap(event.getPlayer()),
                event.locale().toString(),
                event.locale()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerMoveEvent event) {
        callEvent(event, new MCCPlayerMoveEvent(
                wrap(event.getPlayer()),
                event.isCancelled(),
                wrap(event.getFrom()),
                wrap(event.getTo())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerRegisterChannelEvent event) {
        callEvent(event, new MCCPlayerRegisterChannelEvent(
                wrap(event.getPlayer()),
                event.getChannel()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerShowEntityEvent event) {
        callEvent(event, new MCCPlayerShowEntityEvent(
                wrap(event.getPlayer()),
                wrap(event.getEntity())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerToggleFlightEvent event) {
        callEvent(event, new MCCPlayerToggleFlightEvent(
                wrap(event.getPlayer()),
                event.isFlying(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerToggleSneakEvent event) {
        callEvent(event, new MCCPlayerToggleSneakEvent(
                wrap(event.getPlayer()),
                event.isSneaking(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerToggleSprintEvent event) {
        callEvent(event, new MCCPlayerToggleSprintEvent(
                wrap(event.getPlayer()),
                event.isSprinting(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerUnregisterChannelEvent event) {
        callEvent(event, new MCCPlayerUnregisterChannelEvent(
                wrap(event.getPlayer()),
                event.getChannel()
        ));
    }
}
