package de.verdox.mccreativelab.wrapper.item.components;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import java.util.List;

/**
 * Defines the items and projectiles loaded into this crossbow. This component needs to be present for this crossbow to be charged.
 */
public interface MCCChargedProjectiles extends MCCItemComponent {
	public List<MCCItemStack> getProjectiles();

	public MCCChargedProjectiles withProjectiles(List<MCCItemStack> arg0);
}