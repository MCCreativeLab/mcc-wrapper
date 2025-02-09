package de.verdox.mccreativelab.wrapper.types;
import com.google.common.collect.ImmutableSet;
import net.kyori.adventure.sound.Sound;
import java.lang.String;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
public interface MCCVillagerProfession  {
	public String getName();

	public ImmutableSet<MCCItemType> getRequestedItems();

	public ImmutableSet<MCCBlockType> getSecondaryPoi();

	public Sound getWorkSound();
}