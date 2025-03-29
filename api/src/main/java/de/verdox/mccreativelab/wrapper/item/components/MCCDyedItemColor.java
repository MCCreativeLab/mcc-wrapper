package de.verdox.mccreativelab.wrapper.item.components;

/**
 * Defines the color of this leather armor piece.
 */
public interface MCCDyedItemColor extends MCCItemComponent {
	public int getRgb();

	public MCCDyedItemColor withRgb(int rgb);

	public boolean getShowInTooltip();

	public MCCDyedItemColor withShowInTooltip(boolean showInTooltip);
}