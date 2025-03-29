package de.verdox.mccreativelab.wrapper.item.components;

/**
 * Contains the icons that are displayed on this filled map (e.g. the structures on an explorer map).
 */
public interface MCCMapItemColor extends MCCItemComponent {
	public int getRgb();

	public MCCMapItemColor withRgb(int rgb);
}