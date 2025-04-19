package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.event.block.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;

public class BlockEvents extends EventBase {
    //TODO: without MCCBlockEvent, BlockPistonEvent

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockBreakEvent event) {
        callEvent(event, new MCCBlockBreakEvent(
                wrap(event.getBlock()),
                event.getExpToDrop(),
                wrap(event.getPlayer()),
                event.isDropItems(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockBurnEvent event) {
        callEvent(event, new MCCBlockBurnEvent(
                wrap(event.getBlock()),
                event.isCancelled(),
                wrap(event.getIgnitingBlock())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockCanBuildEvent event) {
        callEvent(event, new MCCBlockCanBuildEvent(
                wrap(event.getBlock()),
                event.isBuildable(),
                wrap(event.getBlockData()),
                wrap(event.getPlayer()),
                wrap(event.getHand())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockExpEvent event) {
        callEvent(event, new MCCBlockExpEvent(
                wrap(event.getBlock()),
                event.getExpToDrop()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockFromToEvent event) {
        callEvent(event, new MCCBlockFromToEvent(
                wrap(event.getBlock()),
                wrap(event.getToBlock()),
                wrap(event.getFace()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockPhysicsEvent event) {
        callEvent(event, new MCCBlockPhysicsEvent(
                wrap(event.getBlock()),
                wrap(event.getChangedBlockData()),
                wrap(event.getSourceBlock()),
                event.isCancelled()
        ));
    }

    public static class Piston extends EventBase {

        @EventHandler(ignoreCancelled = true)
        public void handle(BlockPistonExtendEvent event) {
            callEvent(event, new MCCBlockPistonExtendEvent(
                    wrap(event.getBlock()),
                    event.isCancelled(),
                    wrap(event.getDirection()),
                    ReflectionUtils.readFieldFromClass(event, "length", new TypeToken<>() {}),
                    wrap(event.getBlocks())
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(BlockPistonRetractEvent event) {
            callEvent(event, new MCCBlockPistonRetractEvent(
                    wrap(event.getBlock()),
                    event.isCancelled(),
                    wrap(event.getDirection()),
                    wrap(event.getBlocks())
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(BlockRedstoneEvent event) {
            callEvent(event, new MCCBlockRedstoneEvent(
                    wrap(event.getBlock()),
                    event.getOldCurrent(),
                    event.getNewCurrent()
            ));
        }
    }
}