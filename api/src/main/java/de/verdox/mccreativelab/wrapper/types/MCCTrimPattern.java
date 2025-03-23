package de.verdox.mccreativelab.wrapper.types;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.key.Key;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
public interface MCCTrimPattern  {
	public Key getAssetId();

	public MCCReference<MCCItemType> getTemplateItem();

	public Component getDescription();

	public boolean getDecal();
}