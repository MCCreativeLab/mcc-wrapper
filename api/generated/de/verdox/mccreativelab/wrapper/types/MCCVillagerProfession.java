package de.verdox.mccreativelab.wrapper.types;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import net.kyori.adventure.sound.Sound;

import java.util.Set;

public interface MCCVillagerProfession extends MCCWrapped  {

	public String getName();

	public Set<MCCItemType> getRequestedItems();

	public Set<MCCBlockType> getSecondaryPoi();

	public Sound getWorkSound();

}
