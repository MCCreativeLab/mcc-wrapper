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
            }), true);
        } else {
            return new PaperMCCTask(Bukkit.getGlobalRegionScheduler().run(javaPlugin, (scheduledTask) -> {
                taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
            }), true);
        }
    }

    @Override
    public MCCTask runLaterOnTickThread(@Nullable MCCLocation location, Consumer<MCCTask> taskConsumer, long delay, TimeUnit timeUnit) {
        long ticks = timeUnit.toSeconds(delay) / 20;
        if (location != null) {
            Location bukkitLocation = BukkitAdapter.unwrap(location);
            return new PaperMCCTask(Bukkit.getRegionScheduler().runDelayed(javaPlugin, bukkitLocation, (scheduledTask) -> {
                taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
            }, ticks <= 0 ? 1 : ticks), true);
        } else {
            return new PaperMCCTask(Bukkit.getGlobalRegionScheduler().runDelayed(javaPlugin, (scheduledTask) -> {
                taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
            }, ticks <= 0 ? 1 : ticks), true);
        }
    }

    @Override
    public MCCTask runTimerOnTickThread(@Nullable MCCLocation location, Consumer<MCCTask> taskConsumer, long delay, long period, TimeUnit timeUnit) {
        long ticksDelay = Math.min(1, timeUnit.toSeconds(delay) / 20);
        long ticksPeriod = Math.min(1, timeUnit.toSeconds(period) / 20);
        if (location != null) {
            Location bukkitLocation = BukkitAdapter.unwrap(location);
            return new PaperMCCTask(Bukkit.getRegionScheduler().runAtFixedRate(javaPlugin, bukkitLocation, (scheduledTask) -> {
                taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
            }, ticksDelay <= 0 ? 1 : ticksDelay, ticksPeriod <= 0 ? 1 : ticksPeriod), true);
        } else {
            return new PaperMCCTask(Bukkit.getGlobalRegionScheduler().runAtFixedRate(javaPlugin, (scheduledTask) -> {
                taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
            }, ticksDelay <= 0 ? 1 : ticksDelay, ticksPeriod <= 0 ? 1 : ticksPeriod), true);
        }
    }

    @Override
    public MCCTask runAsync(Consumer<MCCTask> taskConsumer) {
        return new PaperMCCTask(Bukkit.getAsyncScheduler().runNow(javaPlugin, scheduledTask -> {
            taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
        }), false);
    }

    @Override
    public MCCTask runLaterAsync(Consumer<MCCTask> taskConsumer, long delay, TimeUnit timeUnit) {
        return new PaperMCCTask(Bukkit.getAsyncScheduler().runDelayed(javaPlugin, scheduledTask -> {
            taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
        }, delay <= 0 ? 1 : delay, timeUnit), false);
    }

    @Override
    public MCCTask runTimerAsync(Consumer<MCCTask> taskConsumer, long delay, long period, TimeUnit timeUnit) {
        return new PaperMCCTask(Bukkit.getAsyncScheduler().runAtFixedRate(javaPlugin, scheduledTask -> {
            taskConsumer.accept(new PaperMCCTask(scheduledTask, false));
        }, delay <= 0 ? 1 : delay, period <= 0 ? 1 : period, timeUnit), false);
    }
}
