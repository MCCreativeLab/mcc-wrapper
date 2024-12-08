package de.verdox.mccreativelab.wrapper.event.world;

import de.verdox.mccreativelab.wrapper.world.MCCWorld;

/**
 *  Called when a World is loaded
 */

public class MCCWorldLoadEvent extends MCCWorldEvent  {

	public MCCWorldLoadEvent(MCCWorld world){
		super(world);
	}

}
