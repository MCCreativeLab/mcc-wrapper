package de.verdox.mccreativelab.impl.paper.platform.task;

import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCTask;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;

public class PaperMCCTask extends MCCHandle<ScheduledTask> implements MCCTask {
    private final boolean onTickThread;
    public PaperMCCTask(ScheduledTask handle, boolean onTickThread) {
        super(handle);
        this.onTickThread = onTickThread;
    }

    @Override
    public void cancel() {
        handle.cancel();
    }

    @Override
    public boolean runsOnTickThread() {
        return onTickThread;
    }

    @Override
    public boolean isRunning() {
        return !handle.isCancelled();
    }
}
