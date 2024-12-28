package de.verdox.mccreativelab.impl.paper.platform.task;

import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.platform.MCCTask;
import de.verdox.mccreativelab.wrapper.platform.MCCTaskManager;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


public class PaperTaskScheduler implements MCCTaskManager {
    private final JavaPlugin javaPlugin;

    public PaperTaskScheduler(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }


    @Override
    public MCCTask runOnTickThread(@Nullable MCCLocation location, Consumer<MCCTask> taskConsumer) {
        if (location != null) {
            Location bukkitLocation = BukkitAdapter.unwrap(location);
            return new PaperMCCTask(Bukkit.getRegionScheduler().run(javaPlugin, bukkitLocation, (scheduledTask) -> {
                taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
            }), false);
        } else {
            return new PaperMCCTask(Bukkit.getGlobalRegionScheduler().run(javaPlugin, (scheduledTask) -> {
                taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
            }), false);
        }
    }

    @Override
    public MCCTask runLaterOnTickThread(@Nullable MCCLocation location, Consumer<MCCTask> taskConsumer, long delay, TimeUnit timeUnit) {
        long ticks = timeUnit.toSeconds(delay) / 20;
        if (location != null) {
            Location bukkitLocation = BukkitAdapter.unwrap(location);
            return new PaperMCCTask(Bukkit.getRegionScheduler().runDelayed(javaPlugin, bukkitLocation, (scheduledTask) -> {
                taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
            }, delay), false);
        } else {
            return new PaperMCCTask(Bukkit.getGlobalRegionScheduler().runDelayed(javaPlugin, (scheduledTask) -> {
                taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
            }, ticks), false);
        }
    }

    @Override
    public MCCTask runTimerOnTickThread(@Nullable MCCLocation location, Consumer<MCCTask> taskConsumer, long delay, long period, TimeUnit timeUnit) {
        long ticksDelay = timeUnit.toSeconds(delay) / 20;
        long ticksPeriod = timeUnit.toSeconds(period) / 20;
        if (location != null) {
            Location bukkitLocation = BukkitAdapter.unwrap(location);
            return new PaperMCCTask(Bukkit.getRegionScheduler().runAtFixedRate(javaPlugin, bukkitLocation, (scheduledTask) -> {
                taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
            }, ticksDelay, ticksPeriod), false);
        } else {
            return new PaperMCCTask(Bukkit.getGlobalRegionScheduler().runAtFixedRate(javaPlugin, (scheduledTask) -> {
                taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
            }, ticksDelay, ticksPeriod), false);
        }
    }

    @Override
    public MCCTask runAsync(Consumer<MCCTask> taskConsumer) {
        return new PaperMCCTask(Bukkit.getAsyncScheduler().runNow(javaPlugin, scheduledTask -> {
            taskConsumer.accept(new PaperMCCTask(scheduledTask, true));
        }), true);
    }

    @Override
    public MCCTask runLaterAsync(Consumer<MCCTask> taskConsumer, long delay, TimeUnit timeUnit) {
        return new PaperMCCTask(Bukkit.getAsyncScheduler().runDelayed(javaPlugin, scheduledTask -> {
            taskConsumer.accept(new PaperMCCTask(scheduledTask, true));
        }, delay, timeUnit), true);
    }

    @Override
    public MCCTask runTimerAsync(Consumer<MCCTask> taskConsumer, long delay, long period, TimeUnit timeUnit) {
        return new PaperMCCTask(Bukkit.getAsyncScheduler().runAtFixedRate(javaPlugin, scheduledTask -> {
            taskConsumer.accept(new PaperMCCTask(scheduledTask, true));
        }, delay, period, timeUnit), true);
    }
}
