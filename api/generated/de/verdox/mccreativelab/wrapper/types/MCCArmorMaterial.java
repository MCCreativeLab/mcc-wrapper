package de.verdox.mccreativelab.wrapper.types;

import java.util.List;
import net.kyori.adventure.sound.Sound;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.types.MCCArmorMaterial;
import java.lang.String;
import net.kyori.adventure.key.Key;

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
