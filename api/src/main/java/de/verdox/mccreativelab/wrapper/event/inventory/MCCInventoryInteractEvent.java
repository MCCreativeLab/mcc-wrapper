package de.verdox.mccreativelab.wrapper.event.inventory;

import de.verdox.mccreativelab.wrapper.entity.ContainerViewer;
import de.verdox.mccreativelab.wrapper.event.MCCCancellable;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainerMenu;

import java.util.Set;

public abstract class MCCInventoryInteractEvent extends MCCInventoryEvent implements MCCCancellable {
    private boolean cancelled;

    public MCCInventoryInteractEvent(MCCContainerMenu<?, ?> containerMenu, Set<ContainerViewer> currentViewers) {
        super(containerMenu, currentViewers);
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
