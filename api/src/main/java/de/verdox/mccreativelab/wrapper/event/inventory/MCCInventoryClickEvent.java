package de.verdox.mccreativelab.wrapper.event.inventory;

import de.verdox.mccreativelab.wrapper.entity.ContainerViewer;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainerMenu;
import de.verdox.mccreativelab.wrapper.inventory.MCCInventoryAction;
import de.verdox.mccreativelab.wrapper.inventory.MCCInventoryClickType;
import de.verdox.mccreativelab.wrapper.inventory.MCCInventorySlotType;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;

import java.util.Set;

public class MCCInventoryClickEvent extends MCCInventoryInteractEvent {
    private final MCCInventoryClickType clickType;
    private final MCCInventoryAction inventoryAction;
    private final MCCInventorySlotType slotType;
    private final int slot;
    private final int rawSlot;
    private final int hotbarKey;
    private MCCItemStack current;

    public MCCInventoryClickEvent(MCCContainerMenu<?, ?> containerMenu, Set<ContainerViewer> currentViewers, MCCInventoryClickType clickType, MCCInventoryAction inventoryAction, MCCInventorySlotType slotType, int slot, int rawSlot, MCCItemStack current, int hotbarKey) {
        super(containerMenu, currentViewers);
        this.clickType = clickType;
        this.inventoryAction = inventoryAction;
        this.slotType = slotType;
        this.slot = slot;
        this.rawSlot = rawSlot;
        this.current = current;
        this.hotbarKey = hotbarKey;
    }

    public void setCurrent(MCCItemStack current) {
        this.current = current;
    }

    public MCCInventoryClickType getClickType() {
        return clickType;
    }

    public MCCInventoryAction getInventoryAction() {
        return inventoryAction;
    }

    public MCCInventorySlotType getSlotType() {
        return slotType;
    }

    public int getSlot() {
        return slot;
    }

    public int getRawSlot() {
        return rawSlot;
    }

    public int getHotbarKey() {
        return hotbarKey;
    }

    public MCCItemStack getCurrent() {
        return current;
    }
}
