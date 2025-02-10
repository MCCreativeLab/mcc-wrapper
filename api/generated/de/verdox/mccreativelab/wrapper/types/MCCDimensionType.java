package de.verdox.mccreativelab.wrapper.types;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.registry.MCCTag;
import net.kyori.adventure.key.Key;

import java.util.OptionalLong;

public interface MCCDimensionType extends MCCWrapped  {

	public OptionalLong getFixedTime();

	public boolean getHasSkyLight();

	public boolean getHasCeiling();

	public boolean getUltraWarm();

	public boolean getNatural();

	public double getCoordinateScale();

	public boolean getBedWorks();

	public boolean getRespawnAnchorWorks();

	public int getMinY();

	public int getHeight();

	public int getLogicalHeight();

	public MCCTag<MCCBlockType> getInfiniburn();

	public Key getEffectsLocation();

	public float getAmbientLight();

	public MCCDimensionType.MonsterSettings getMonsterSettings();


	public static interface MonsterSettings  {
	
		public boolean getPiglinSafe();
	
		public boolean getHasRaids();
	
		public int getMonsterSpawnBlockLightLimit();
	
	}
}
