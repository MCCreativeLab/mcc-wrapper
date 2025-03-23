package de.verdox.mccreativelab.impl.paper.events;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.event.block.*;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

public class BlockEvents implements Listener {
    // without MCCBlockEvent, BlockPistonEvent

    @EventHandler
    public void handle(BlockBreakEvent event) {
        MCCBlockBreakEvent mccBlockBreakEvent = new MCCBlockBreakEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                event.getExpToDrop(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                event.isDropItems(),
                event.isCancelled()
        );

        if (mccBlockBreakEvent.isCancelled()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(BlockBurnEvent event) {
        MCCBlockBurnEvent mccBlockBurnEvent = new MCCBlockBurnEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getIgnitingBlock())
        );

        if (mccBlockBurnEvent.isCancelled()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(BlockCanBuildEvent event) {
        MCCBlockCanBuildEvent mccBlockCanBuildEvent = new MCCBlockCanBuildEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                event.isBuildable(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlockData()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHand())
        );

        mccBlockCanBuildEvent.callEvent();
    }

    @EventHandler
    public void handle(BlockExpEvent event) {
        MCCBlockExpEvent mccBlockExpEvent = new MCCBlockExpEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                event.getExpToDrop()
        );

        mccBlockExpEvent.callEvent();
    }

    @EventHandler
    public void handle(BlockFromToEvent event) {
        MCCBlockFromToEvent mccBlockFromToEvent = new MCCBlockFromToEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getToBlock()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getFace()),
                event.isCancelled()
        );

        if (mccBlockFromToEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler
    public void handle(BlockPhysicsEvent event) {
        MCCBlockPhysicsEvent mccBlockPhysicsEvent = new MCCBlockPhysicsEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getChangedType()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getSourceBlock()),
                event.isCancelled()
        );

        if (mccBlockPhysicsEvent.isCancelled()) event.setCancelled(true);
    }

    public static class BlockPistonEvents implements Listener {

        @EventHandler
        public void handle(BlockPistonExtendEvent event) {
            MCCBlockPistonExtendEvent mccBlockPistonExtendEvent = new MCCBlockPistonExtendEvent(
                    MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                    event.isCancelled(),
                    MCCPlatform.getInstance().getConversionService().wrap(event.getDirection()),
                    ReflectionUtils.readFieldFromClass(event, "length", new TypeToken<>() {}),
                    MCCPlatform.getInstance().getConversionService().wrap(event.getBlocks())
            );

            if (mccBlockPistonExtendEvent.callEvent()) event.setCancelled(true);
        }

        @EventHandler
        public void handle(BlockPistonRetractEvent event) {
            MCCBlockPistonRetractEvent mccBlockPistonRetractEvent = new MCCBlockPistonRetractEvent(
                    MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                    event.isCancelled(),
                    MCCPlatform.getInstance().getConversionService().wrap(event.getDirection()),
                    MCCPlatform.getInstance().getConversionService().wrap(event.getBlocks())
            );

            if (mccBlockPistonRetractEvent.callEvent()) event.setCancelled(true);
        }

        @EventHandler
        public void handle(BlockRedstoneEvent event) {
            MCCBlockRedstoneEvent mccBlockRedstoneEvent = new MCCBlockRedstoneEvent(
                    MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                    event.getOldCurrent(),
                    event.getNewCurrent()
            );

            mccBlockRedstoneEvent.callEvent();
        }
    }
}
