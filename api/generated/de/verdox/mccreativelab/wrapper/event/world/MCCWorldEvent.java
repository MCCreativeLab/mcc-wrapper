package de.verdox.mccreativelab.wrapper.event.world;

import de.verdox.mccreativelab.wrapper.event.MCCEvent;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;

/**
 *  Represents events within a world
 */

public class MCCWorldEvent implements MCCEvent  {

	private final MCCWorld world;

	public MCCWorldEvent(MCCWorld world){
		this.world = world;
	}

	/**
	 *      Gets the world primarily involved with this event
	 *      *
	 *      @return World which caused this event
	 */
	public MCCWorld getWorld(){
		return world;
	}

}
