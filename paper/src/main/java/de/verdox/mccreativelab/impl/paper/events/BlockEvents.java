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
                wrap(event.getBlock(), new TypeToken<>() {}),
                event.getExpToDrop(),
                wrap(event.getPlayer(), new TypeToken<>() {}),
                event.isDropItems(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockBurnEvent event) {
        callEvent(event, new MCCBlockBurnEvent(
                wrap(event.getBlock(), new TypeToken<>() {}),
                event.isCancelled(),
                wrap(event.getIgnitingBlock(), new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockCanBuildEvent event) {
        callEvent(event, new MCCBlockCanBuildEvent(
                wrap(event.getBlock(), new TypeToken<>() {}),
                event.isBuildable(),
                wrap(event.getBlockData(), new TypeToken<>() {}),
                wrap(event.getPlayer(), new TypeToken<>() {}),
                wrap(event.getHand(), new TypeToken<>() {})
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockExpEvent event) {
        callEvent(event, new MCCBlockExpEvent(
                wrap(event.getBlock(), new TypeToken<>() {}),
                event.getExpToDrop()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockFromToEvent event) {
        callEvent(event, new MCCBlockFromToEvent(
                wrap(event.getBlock(), new TypeToken<>() {}),
                wrap(event.getToBlock(), new TypeToken<>() {}),
                wrap(event.getFace(), new TypeToken<>() {}),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BlockPhysicsEvent event) {
        callEvent(event, new MCCBlockPhysicsEvent(
                wrap(event.getBlock(), new TypeToken<>() {}),
                wrap(event.getChangedBlockData(), new TypeToken<>() {}),
                wrap(event.getSourceBlock(), new TypeToken<>() {}),
                event.isCancelled()
        ));
    }

    public static class Piston extends EventBase {

        @EventHandler(ignoreCancelled = true)
        public void handle(BlockPistonExtendEvent event) {
            callEvent(event, new MCCBlockPistonExtendEvent(
                    wrap(event.getBlock(), new TypeToken<>() {}),
                    event.isCancelled(),
                    wrap(event.getDirection(), new TypeToken<>() {}),
                    ReflectionUtils.readFieldFromClass(event, "length", new TypeToken<>() {}),
                    wrap(event.getBlocks(), new TypeToken<>() {})
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(BlockPistonRetractEvent event) {
            callEvent(event, new MCCBlockPistonRetractEvent(
                    wrap(event.getBlock(), new TypeToken<>() {}),
                    event.isCancelled(),
                    wrap(event.getDirection(), new TypeToken<>() {}),
                    wrap(event.getBlocks(), new TypeToken<>() {})
            ));
        }

        @EventHandler(ignoreCancelled = true)
        public void handle(BlockRedstoneEvent event) {
            callEvent(event, new MCCBlockRedstoneEvent(
                    wrap(event.getBlock(), new TypeToken<>() {}),
                    event.getOldCurrent(),
                    event.getNewCurrent()
            ));
        }
    }
}