package de.verdox.mccreativelab.wrapper.platform;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Used to schedule tasks
 */
public interface MCCTaskManager {
    /**
     * Schedules a task on the ticking thread at a specific location (if supported be the platform).
     * If no location is provided or the platform does not support location independent ticking, the task is run on the main ticking thread.
     *
     * @param location     the location of the task
     * @param taskConsumer the task to run
     * @return the created task
     */
    MCCTask runOnTickThread(@Nullable MCCLocation location, Consumer<MCCTask> taskConsumer);

    /**
     * Schedules a task on the ticking thread after a delay at a specific location (if supported be the platform).
     * If no location is provided or the platform does not support location independent ticking, the task is run on the main ticking thread.
     *
     * @param location     the location of the task
     * @param taskConsumer the task to run
     * @param delay        the delay
     * @param timeUnit     the time unit of the delay
     * @return the created task
     */
    MCCTask runLaterOnTickThread(@Nullable MCCLocation location, Consumer<MCCTask> taskConsumer, long delay, TimeUnit timeUnit);

    /**
     * Schedules a task on the ticking thread after a period with a period at a specific location (if supported be the platform).
     * If no location is provided or the platform does not support location independent ticking, the task is run on the main ticking thread.
     *
     * @param location     the location of the task
     * @param taskConsumer the task to run
     * @param period       the period to repeat this task after
     * @param delay        the delay
     * @param timeUnit     the time unit of the delay
     * @return the created task
     */
    MCCTask runTimerOnTickThread(@Nullable MCCLocation location, Consumer<MCCTask> taskConsumer, long delay, long period, TimeUnit timeUnit);

    /**
     * Schedules a task on the global ticking thread.
     *
     * @param taskConsumer the task to run
     * @return the created task
     */
    default MCCTask runOnTickThread(Consumer<MCCTask> taskConsumer) {
        return runOnTickThread(null, taskConsumer);
    }

    /**
     * Schedules a task on the global ticking thread after a delay.
     *
     * @param taskConsumer the task to run
     * @param delay        the delay
     * @param timeUnit     the time unit of the delay
     * @return the created task
     */
    default MCCTask runLaterOnTickThread(Consumer<MCCTask> taskConsumer, long delay, TimeUnit timeUnit) {
        return runLaterOnTickThread(null, taskConsumer, delay, timeUnit);
    }

    /**
     * Schedules a task on the global ticking thread after a period with a period.
     *
     * @param taskConsumer the task to run
     * @param period       the period to repeat this task after
     * @param delay        the delay
     * @param timeUnit     the time unit of the delay
     * @return the created task
     */
    default MCCTask runTimerOnTickThread(Consumer<MCCTask> taskConsumer, long delay, long period, TimeUnit timeUnit) {
        return runTimerOnTickThread(null, taskConsumer, delay, period, timeUnit);
    }

    /**
     * Schedules a task to run async from any ticking threads.
     *
     * @param taskConsumer the task to run
     * @return the created task
     */
    MCCTask runAsync(Consumer<MCCTask> taskConsumer);

    /**
     * Schedules a task to run async after a delay from any ticking threads.
     *
     * @param taskConsumer the task to run
     * @param delay        the delay
     * @param timeUnit     the time unit of the delay
     * @return the created task
     */
    MCCTask runLaterAsync(Consumer<MCCTask> taskConsumer, long delay, TimeUnit timeUnit);

    /**
     * Schedules a task to run async after a delay with a period from any ticking threads.
     *
     * @param taskConsumer the task to run
     * @param delay        the delay
     * @param period       the period to repeat this task after
     * @param timeUnit     the time unit of the delay
     * @return the created task
     */
    MCCTask runTimerAsync(Consumer<MCCTask> taskConsumer, long delay, long period, TimeUnit timeUnit);

    /**
     * Schedules a sync task and waits on the calling thread for the sync task to finish
     * @param taskConsumer the task to run
     * @return a future indicating when the sync task finishes
     */
    default void syncAndWait(Consumer<MCCTask> taskConsumer) {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        runOnTickThread(mccTask -> {
            try {
                taskConsumer.accept(mccTask);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            completableFuture.complete(null);
        });
        completableFuture.join();
    }

    /**
     * Schedules a sync task and gets data from it.
     * @param taskConsumer the task to run
     * @return the data gotten from the sync thread
     */
    default <R> R syncAndWait(Function<MCCTask, R> taskConsumer) {
        CompletableFuture<R> completableFuture = new CompletableFuture<>();
        runOnTickThread(mccTask -> {
            try {
                R data = taskConsumer.apply(mccTask);
                completableFuture.complete(data);
            } catch (Throwable e) {
                e.printStackTrace();
                completableFuture.cancel(true);
            }
        });
        return completableFuture.join();
    }
}
