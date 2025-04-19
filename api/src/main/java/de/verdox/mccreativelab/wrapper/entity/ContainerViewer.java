package de.verdox.mccreativelab.wrapper.entity;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainerCloseReason;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainerMenu;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Describes an entity that can open an inventory
 */
public interface ContainerViewer extends MCCWrapped {
    /**
     * Closes the current inventory viewed by this player
     *
     * @param closeReason the reason for why it was closed
     */
    void closeCurrentInventory(MCCContainerCloseReason closeReason);

    /**
     * Returns the container currently viewed if available
     * @return the container or null if no container is viewed
     */
    @Nullable MCCContainerMenu<?,?> getCurrentlyViewedInventory();

    /**
     * Returns the item that is currently held in the cursor.
     * @return the cursor item
     */
    MCCItemStack getCursorItem();

    /**
     * Sets the item that should be held in the cursor
     */
    void setCursorItem(@Nullable MCCItemStack cursorItem);
}
