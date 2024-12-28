package de.verdox.mccreativelab.wrapper.platform;

/**
 * A task scheduled by the {@link MCCTaskManager}
 */
public interface MCCTask {
    /**
     * Cancels this task
     */
    void cancel();

    /**
     * Returns true when this task is running on the tick thread. The tick thread might be different based on the context of the task.
     * @return true or false
     */
    boolean runsOnTickThread();

    /**
     * Returns true if the task is still running
     *
     * @return true if the task is running
     */
    boolean isRunning();
}
