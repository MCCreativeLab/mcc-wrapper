package de.verdox.mccreativelab.wrapper.event.world;

import de.verdox.mccreativelab.wrapper.world.MCCWorld;

/**
 *  Called when a World is saved.
 */

public class MCCWorldSaveEvent extends MCCWorldEvent  {

	public MCCWorldSaveEvent(MCCWorld world){
		super(world);
	}

}
