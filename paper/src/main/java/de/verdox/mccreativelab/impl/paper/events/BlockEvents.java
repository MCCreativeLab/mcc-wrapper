package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.event.block.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;

import java.util.ArrayList;

public class BlockEvents extends EventBase {
    //TODO: without MCCBlockEvent, BlockPistonEvent

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockBreakEvent event) {
        callEvent(event, new MCCBlockBreakEvent(
                BukkitAdapter.toMcc(event.getBlock()),
                event.getExpToDrop(),
                BukkitAdapter.toMcc(event.getPlayer()),
                event.isDropItems(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockBurnEvent event) {
        callEvent(event, new MCCBlockBurnEvent(
                BukkitAdapter.toMcc(event.getBlock()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getIgnitingBlock())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockCanBuildEvent event) {
        callEvent(event, new MCCBlockCanBuildEvent(
                BukkitAdapter.toMcc(event.getBlock()),
                event.isBuildable(),
                BukkitAdapter.toMcc(event.getBlockData()),
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getHand())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockExpEvent event) {
        callEvent(event, new MCCBlockExpEvent(
                BukkitAdapter.toMcc(event.getBlock()),
                event.getExpToDrop()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockFromToEvent event) {
        callEvent(event, new MCCBlockFromToEvent(
                BukkitAdapter.toMcc(event.getBlock()),
                BukkitAdapter.toMcc(event.getToBlock()),
                BukkitAdapter.toMcc(event.getFace()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockPhysicsEvent event) {
        callEvent(event, new MCCBlockPhysicsEvent(
                BukkitAdapter.toMcc(event.getBlock()),
                BukkitAdapter.toMcc(event.getChangedBlockData()),
                BukkitAdapter.toMcc(event.getSourceBlock()),
                event.isCancelled()
        ));
    }

    public static class Piston extends EventBase {

        @EventHandler(ignoreCancelled = true)
        public void handle(BlockPistonExtendEvent event) {
            callEvent(event, new MCCBlockPistonExtendEvent(
                    BukkitAdapter.toMcc(event.getBlock()),
                    event.isCancelled(),
                    BukkitAdapter.toMcc(event.getDirection()),
                    ReflectionUtils.readFieldFromClass(event, "length", new TypeToken<>() {}),
                    BukkitAdapter.toMcc(event.getBlocks(), BukkitAdapter::toMcc, ArrayList::new)
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(BlockPistonRetractEvent event) {
            callEvent(event, new MCCBlockPistonRetractEvent(
                    BukkitAdapter.toMcc(event.getBlock()),
                    event.isCancelled(),
                    BukkitAdapter.toMcc(event.getDirection()),
                    BukkitAdapter.toMcc(event.getBlocks(), BukkitAdapter::toMcc, ArrayList::new)
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(BlockRedstoneEvent event) {
            callEvent(event, new MCCBlockRedstoneEvent(
                    BukkitAdapter.toMcc(event.getBlock()),
                    event.getOldCurrent(),
                    event.getNewCurrent()
            ));
        }
    }
}