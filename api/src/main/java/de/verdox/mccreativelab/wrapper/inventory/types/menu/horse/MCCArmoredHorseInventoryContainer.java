package de.verdox.mccreativelab.wrapper.inventory.types.menu.horse;

import de.verdox.mccreativelab.wrapper.item.MCCItemStack;

public interface MCCArmoredHorseInventoryContainer {

    /**
     * Gets the item in the horse's armor slot.
     *
     * @return the armor item
     */
    MCCItemStack getArmor();

    /**
     * Sets the item in the horse's armor slot.
     *
     * @param stack the new item
     */
    void setArmor(MCCItemStack stack);
}
