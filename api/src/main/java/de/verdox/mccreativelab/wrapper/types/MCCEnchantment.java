package de.verdox.mccreativelab.wrapper.types;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlotGroup;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.registry.MCCReferenceSet;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Optional;

/**
 * A minecraft enchantment
 */
public interface MCCEnchantment extends MCCWrapped {
    /**
     * Returns the description of this enchantment
     *
     * @return the description
     */
    Component getDescription();

    /**
     * Returns the effects that are applied with this enchantment
     *
     * @return the effects
     */
    MCCDataComponentMap getEffects();

    /**
     * Returns the enchantments that are excluded when this enchantment is applied to an item
     *
     * @return the excluded enchantments
     */
    MCCReferenceSet<MCCEnchantment> getExclusiveSet();

    /**
     * Returns the enchantment definition
     *
     * @return the definition
     */
    Definition getDefinition();

    /**
     * Represents an enchantment definition
     */
    interface Definition {

        /**
         * Returns the supported items for this enchantment
         *
         * @return the supported items
         */
        MCCReferenceSet<MCCItemType> getSupportedItems();

        /**
         * Returns the primary items for this enchantment
         *
         * @return the primary items
         */
        Optional<MCCReferenceSet<MCCItemType>> getPrimaryItems();

        /**
         * Returns the weight of this enchantment
         *
         * @return the weight
         */
        int getWeight();

        /**
         * Returns the max level of this enchantment
         *
         * @return the max level
         */
        int getMaxLevel();

        /**
         * Returns the min cost for this enchantment
         *
         * @return the min cost
         */
        Cost getMinCost();

        /**
         * Returns the max cost for this enchantment
         *
         * @return the max cost
         */
        Cost getMaxCost();

        /**
         * Returns the anvil cost of this enchantment
         *
         * @return the anvil cost
         */
        int getAnvilCost();

        /**
         * Returns the list of slot groups this enchantment can be applied on
         *
         * @return the slot list
         */
        List<MCCEquipmentSlotGroup> getSlots();
    }

    interface Cost {
        /**
         * Returns the base cost for this enchantment
         *
         * @return the base cost
         */
        int getBase();

        /**
         * Returns the amount of level that are added on the base cost for this enchantment
         *
         * @return the amount
         */
        int getPerLevelAboveFirst();
    }
}
