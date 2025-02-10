package de.verdox.mccreativelab.wrapper.types;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

public interface MCCTrimPattern extends MCCWrapped  {

	public Key getAssetId();

	public MCCReference<MCCItemType> getTemplateItem();

	public Component getDescription();

	public boolean getDecal();

}
