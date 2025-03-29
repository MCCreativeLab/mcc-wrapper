package de.verdox.mccreativelab.wrapper.item.components;
import de.verdox.mccreativelab.wrapper.registry.MCCReferenceSet;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;

/**
 * Allows the item to be repaired, if damageable, in an anvil using the specified ingredient.
 */
public interface MCCRepairable extends MCCItemComponent {
	public MCCReferenceSet<MCCItemType> getItems();

	public MCCRepairable withItems(MCCReferenceSet<MCCItemType> items);
}