package de.verdox.mccreativelab.wrapper.types;

import net.kyori.adventure.sound.Sound;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import java.util.Set;
import de.verdox.mccreativelab.wrapper.MCCWrapped;
import java.lang.String;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;

public interface MCCVillagerProfession extends MCCWrapped  {

	public String getName();

	public Set<MCCItemType> getRequestedItems();

	public Set<MCCBlockType> getSecondaryPoi();

	public Sound getWorkSound();

}
