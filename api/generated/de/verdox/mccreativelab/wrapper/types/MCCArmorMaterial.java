package de.verdox.mccreativelab.wrapper.types;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

import java.util.List;

public interface MCCArmorMaterial extends MCCWrapped  {

	public int getEnchantmentValue();

	public MCCReference<Sound> getEquipSound();

	public List<MCCArmorMaterial.Layer> getLayers();

	public float getToughness();

	public float getKnockbackResistance();


	public static interface Layer  {
	
		public Key getId();
	
		public String getSuffix();
	
		public boolean getDyeable();
	
	}
}
