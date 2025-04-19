package de.verdox.mccreativelab.impl.paper.block.settings;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.settings.AbstractBlockHardnessSettings;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import io.papermc.paper.event.block.BlockBreakProgressUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PaperBlockHardnessSettings extends AbstractBlockHardnessSettings implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        MCCPlayer player = BukkitAdapter.wrap(e.getPlayer(), new TypeToken<>() {});
        if (e.getClickedBlock() == null || e.getAction().isRightClick()) {
            stopBlockBreakAction(player);
        }
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (e.getClickedBlock() != null && e.getAction().isLeftClick()) {

            if (startBlockBreakAction(BukkitAdapter.wrap(e.getPlayer()), BukkitAdapter.wrap(e.getClickedBlock()))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onStartDigging(BlockDamageEvent e) {
        if (startBlockBreakAction(BukkitAdapter.wrap(e.getPlayer()), BukkitAdapter.wrap(e.getBlock()))) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onStopDigging(BlockDamageAbortEvent e) {
        MCCPlayer player = BukkitAdapter.wrap(e.getPlayer(), new TypeToken<>() {});
        stopBlockBreakAction(player);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent e) {
        MCCPlayer player = BukkitAdapter.wrap(e.getPlayer(), new TypeToken<>() {});
        stopBlockBreakAction(player);
    }

    @EventHandler
    public void stopDiggingOnQuit(PlayerQuitEvent e) {
        MCCPlayer player = BukkitAdapter.wrap(e.getPlayer(), new TypeToken<>() {});
        stopBlockBreakAction(player);
    }

    @EventHandler
    public void tickPlayers(ServerTickEndEvent e) {
        Bukkit.getOnlinePlayers().forEach(player -> tick(BukkitAdapter.wrap(player, new TypeToken<>() {})));
    }

    @Override
    protected void onProgress(MCCPlayer player, MCCBlock block, float progress) {
        Block bukkitBlock = BukkitAdapter.unwrap(block, new TypeToken<>() {});
        Player bukkitPlayer = BukkitAdapter.unwrap(player, new TypeToken<>() {});
        BlockBreakProgressUpdateEvent blockBreakProgressUpdateEvent = new BlockBreakProgressUpdateEvent(bukkitBlock, progress, bukkitPlayer);
        blockBreakProgressUpdateEvent.callEvent();
    }
}
