package de.verdox.mccreativelab.wrapper.types;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlotGroup;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.registry.MCCReferenceSet;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Optional;

public interface MCCEnchantment extends MCCWrapped  {

	public Component getDescription();

	public MCCEnchantment.EnchantmentDefinition getDefinition();

	public MCCReferenceSet<MCCEnchantment> getExclusiveSet();


	public static interface EnchantmentDefinition  {
	
		public MCCReferenceSet<MCCItemType> getSupportedItems();
	
		public Optional<MCCReferenceSet<MCCItemType>> getPrimaryItems();
	
		public int getWeight();
	
		public int getMaxLevel();
	
		public MCCEnchantment.Cost getMinCost();
	
		public MCCEnchantment.Cost getMaxCost();
	
		public int getAnvilCost();
	
		public List<MCCEquipmentSlotGroup> getSlots();
	
	}

	public static interface Cost  {
	
		public int getBase();
	
		public int getPerLevelAboveFirst();
	
	}

	public static interface Builder  {
	
		public MCCEnchantment.EnchantmentDefinition getProperties();
	
	}
}
