package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.event.player.*;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.Set;

public class PlayerEvents implements Listener {

    // Without MCCPlayerEvent

    @EventHandler
    public void handle(PlayerAttemptPickupItemEvent event) {
        MCCPlayerAttemptPickupItemEvent mccPlayerAttemptPickupItemEvent = new MCCPlayerAttemptPickupItemEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getItem()),
                event.getRemaining(),
                event.getFlyAtPlayer(),
                event.isCancelled()
        );

        if (mccPlayerAttemptPickupItemEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerBedLeaveEvent event) {
        MCCPlayerBedLeaveEvent mccPlayerBedLeaveEvent = new MCCPlayerBedLeaveEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getBed()),
                ReflectionUtils.readFieldFromClass(event, "setBedSpawn", new TypeToken<>() {}),
                event.isCancelled()
        );

        if (mccPlayerBedLeaveEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerChangedWorldEvent event) {
        MCCPlayerChangedWorldEvent mccPlayerChangedWorldEvent = new MCCPlayerChangedWorldEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getFrom())
        );

        mccPlayerChangedWorldEvent.callEvent();
    }

    @EventHandler
    public void handle(PlayerChannelEvent event) {
        MCCPlayerChannelEvent mccPlayerChannelEvent = new MCCPlayerChannelEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.getChannel()
        );

        mccPlayerChannelEvent.callEvent();
    }

    @EventHandler
    public void handle(PlayerCommandPreprocessEvent event) {
        MCCPlayerCommandPreprocessEvent mccPlayerCommandPreprocessEvent = new MCCPlayerCommandPreprocessEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.isCancelled(),
                event.getMessage(),
                MCCPlatform.getInstance().getConversionService().wrap(ReflectionUtils.readFieldFromClass(event, "recipients", new TypeToken<Set<Player>>() {}))
        );

        if (mccPlayerCommandPreprocessEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerCommandSendEvent event) {
        MCCPlayerCommandSendEvent mccPlayerCommandSendEvent = new MCCPlayerCommandSendEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.getCommands()
        );

        mccPlayerCommandSendEvent.callEvent();
    }

    @EventHandler
    public void handle(PlayerDropItemEvent event) {
        MCCPlayerDropItemEvent mccPlayerDropItemEvent = new MCCPlayerDropItemEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getItemDrop()),
                event.isCancelled()
        );

        if (mccPlayerDropItemEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerEggThrowEvent event) {
        MCCPlayerEggThrowEvent mccPlayerEggThrowEvent = new MCCPlayerEggThrowEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getEgg()),
                event.isHatching(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHatchingType()),
                event.getNumHatches()
        );

        mccPlayerEggThrowEvent.callEvent();
    }

    @EventHandler
    public void handle(PlayerExpChangeEvent event) {
        MCCPlayerExpChangeEvent mccPlayerExpChangeEvent = new MCCPlayerExpChangeEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getSource()),
                event.getAmount()
        );

        mccPlayerExpChangeEvent.callEvent();
    }

    @EventHandler
    public void handle(PlayerHideEntityEvent event) {
        MCCPlayerHideEntityEvent mccPlayerHideEntityEvent = new MCCPlayerHideEntityEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity())
        );

        mccPlayerHideEntityEvent.callEvent();
    }

    @EventHandler
    public void handle(PlayerInteractEntityEvent event) {
        MCCPlayerInteractEntityEvent mccPlayerInteractAtEntityEvent = new MCCPlayerInteractEntityEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getRightClicked()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHand())
        );

        if (mccPlayerInteractAtEntityEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerItemHeldEvent event) {
        MCCPlayerItemHeldEvent mccPlayerItemHeldEvent = new MCCPlayerItemHeldEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.isCancelled(),
                event.getPreviousSlot(),
                event.getNewSlot()
        );

        if (mccPlayerItemHeldEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerJoinEvent event) {
        MCCPlayerJoinEvent mccPlayerJoinEvent = new MCCPlayerJoinEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.joinMessage()
        );

        mccPlayerJoinEvent.callEvent();
    }

    @EventHandler
    public void handle(PlayerLevelChangeEvent event) {
        MCCPlayerLevelChangeEvent mccPlayerLevelChangeEvent = new MCCPlayerLevelChangeEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.getOldLevel(),
                event.getNewLevel()
        );

        mccPlayerLevelChangeEvent.callEvent();
    }

    @EventHandler
    public void handle(PlayerLocaleChangeEvent event) {
        MCCPlayerLocaleChangeEvent mccPlayerLocaleChangeEvent = new MCCPlayerLocaleChangeEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.locale().toString(),
                event.locale()

        );

        mccPlayerLocaleChangeEvent.callEvent();
    }

    @EventHandler
    public void handle(PlayerMoveEvent event) {
        MCCPlayerMoveEvent mccPlayerMoveEvent = new MCCPlayerMoveEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getFrom()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getTo())
        );

        if (mccPlayerMoveEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerRegisterChannelEvent event) {
        MCCPlayerRegisterChannelEvent mccPlayerRegisterChannelEvent = new MCCPlayerRegisterChannelEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.getChannel()
        );

        mccPlayerRegisterChannelEvent.callEvent();
    }

    @EventHandler
    public void handle(PlayerShowEntityEvent event) {
        MCCPlayerShowEntityEvent mccPlayerShowEntityEvent = new MCCPlayerShowEntityEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity())
        );

        mccPlayerShowEntityEvent.callEvent();
    }

    @EventHandler
    public void handle(PlayerToggleFlightEvent event) {
        MCCPlayerToggleFlightEvent mccPlayerToggleFlightEvent = new MCCPlayerToggleFlightEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.isFlying(),
                event.isCancelled()
        );

        if (mccPlayerToggleFlightEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerToggleSneakEvent event) {
        MCCPlayerToggleSneakEvent mccPlayerToggleSneakEvent = new MCCPlayerToggleSneakEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.isSneaking(),
                event.isCancelled()
        );

        if (mccPlayerToggleSneakEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerToggleSprintEvent event) {
        MCCPlayerToggleSprintEvent mccPlayerToggleSprintEvent = new MCCPlayerToggleSprintEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.isSprinting(),
                event.isCancelled()
        );

        if (mccPlayerToggleSprintEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerUnregisterChannelEvent event) {
        MCCPlayerUnregisterChannelEvent mccPlayerUnregisterChannelEvent = new MCCPlayerUnregisterChannelEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.getChannel()
        );

        mccPlayerUnregisterChannelEvent.callEvent();
    }
}
