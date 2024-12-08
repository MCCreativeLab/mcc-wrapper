package de.verdox.mccreativelab.wrapper.event.weather;

import de.verdox.mccreativelab.wrapper.event.MCCEvent;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;

/**
 *  Represents a Weather-related event
 */

public class MCCWeatherEvent implements MCCEvent  {

	private MCCWorld world;

	public MCCWeatherEvent(MCCWorld world){
		this.world = world;
	}

	/**
	 *      Returns the World where this event is occurring
	 *      *
	 *      @return World this event is occurring in
	 */
	public MCCWorld getWorld(){
		return world;
	}

}
