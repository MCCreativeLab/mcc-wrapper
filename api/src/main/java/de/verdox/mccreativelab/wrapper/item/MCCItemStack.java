package de.verdox.mccreativelab.wrapper.item;

import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.typed.MCCDataComponentTypes;
import de.verdox.mccreativelab.wrapper.types.MCCEnchantment;
import net.kyori.adventure.text.Component;

/**
 * Represents a minecraft item stack
 */
public interface MCCItemStack {
    /**
     * Returns the data component map of this item stack
     * @return the data component map
     */
    MCCDataComponentMap components();

    boolean hasDataComponentType(MCCDataComponentType<?> type);

    /**
     * Gets the amount of this item stack.
     *
     * @return the amount
     */
    int getAmount();

    /**
     * Sets the amount of this item stack
     *
     * @param amount the amount
     */
    void setAmount(int amount);

    /**
     * Returns the max stack size of this item stack
     *
     * @return the max stack size
     */
    default int getMaxStackSize() {
        return components().get(MCCDataComponentTypes.MAX_STACK_SIZE.get());
    }

    /**
     * Checks whether the two item stacks are similar but does not compare amounts
     *
     * @param mccItemStack the other item stack
     * @return true if they are similar
     */
    default boolean isSimilar(MCCItemStack mccItemStack) {
        var copy1 = this.withAmount(1);
        var copy2 = mccItemStack.withAmount(1);

        return copy1.equals(copy2);
    }

    /**
     * Returns a copy of this item stack but with the provided amount
     *
     * @param amount
     * @return
     */
    MCCItemStack withAmount(int amount);

    /**
     * Returns the name of the item
     *
     * @return the name
     */
    Component name();

    /**
     * Sets the name of the item
     *
     * @param name the name
     */
    void name(Component name);

    /**
     * Returns the custom name of the item
     *
     * @return the name
     */
    Component customName();

    /**
     * Sets the custom name of the item
     *
     * @param name the name
     */
    void customName(Component name);

    /**
     * Returns the type of the item
     *
     * @return the type
     */
    MCCItemType getType();

    /**
     * Copies the item
     *
     * @return a copy of the item
     */
    MCCItemStack copy();

    boolean isCorrectToolForDrops(MCCBlockState blockState);

    float getDestroySpeed(MCCBlockState mccBlockState);

    boolean isEmpty();

    int getEnchantmentLevel(MCCEnchantment mccEnchantment);
}
