package de.verdox.mccreativelab.wrapper.item.components;

/**
 * Defines whether the item can be enchanted in an enchanting table. This only applies if there are applicable enchantments available for that item.
 */
public interface MCCEnchantable extends MCCItemComponent {
	public int getValue();

	public MCCEnchantable withValue(int value);
}