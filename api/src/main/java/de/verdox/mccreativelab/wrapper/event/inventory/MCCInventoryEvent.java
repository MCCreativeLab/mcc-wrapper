package de.verdox.mccreativelab.wrapper.event.inventory;

import de.verdox.mccreativelab.wrapper.entity.ContainerViewer;
import de.verdox.mccreativelab.wrapper.event.MCCEvent;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainerMenu;

import java.util.Set;

public class MCCInventoryEvent implements MCCEvent {
    private final MCCContainerMenu<?, ?> containerMenu;
    private final Set<ContainerViewer> currentViewers;

    public MCCInventoryEvent(MCCContainerMenu<?, ?> containerMenu, Set<ContainerViewer> currentViewers) {
        this.containerMenu = containerMenu;
        this.currentViewers = currentViewers;
    }

    public MCCContainerMenu<?, ?> getInventory() {
        return containerMenu;
    }

    public Set<ContainerViewer> getViewers() {
        return currentViewers;
    }
}
