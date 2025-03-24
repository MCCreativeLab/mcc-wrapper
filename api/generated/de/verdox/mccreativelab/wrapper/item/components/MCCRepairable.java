package de.verdox.mccreativelab.wrapper.item.components;
import de.verdox.mccreativelab.wrapper.registry.MCCReferenceSet;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
public interface MCCRepairable extends MCCItemComponent {
	public MCCReferenceSet<MCCItemType> getItems();

	public MCCRepairable withItems(MCCReferenceSet<MCCItemType> items);
}