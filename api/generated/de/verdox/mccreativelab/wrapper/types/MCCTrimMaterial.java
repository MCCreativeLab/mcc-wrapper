package de.verdox.mccreativelab.wrapper.types;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import net.kyori.adventure.text.Component;

import java.util.Map;

public interface MCCTrimMaterial extends MCCWrapped  {

	public String getAssetName();

	public MCCReference<MCCItemType> getIngredient();

	public float getItemModelIndex();

	public Map<String, MCCReference<MCCArmorMaterial>> getOverrideArmorMaterials();

	public Component getDescription();

}
