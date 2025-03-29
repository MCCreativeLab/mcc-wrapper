package de.verdox.mccreativelab.wrapper.item.components;

/**
 * The id of the map to show. Maps are stored in \<world_name>\data and have file names such as map_1.dat
 */
public interface MCCMapId extends MCCItemComponent {
	public int getId();

	public MCCMapId withId(int id);
}