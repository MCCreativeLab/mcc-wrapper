package de.verdox.mccreativelab.wrapper.event.world;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;

/**
 *  An event that is called when a world's spawn changes. The world's previous
 *  spawn location is included.
 */

public class MCCSpawnChangeEvent extends MCCWorldEvent  {

	private final MCCLocation previousLocation;

	public MCCSpawnChangeEvent(MCCWorld world, MCCLocation previousLocation){
		super(world);
		this.previousLocation = previousLocation;
	}

	/**
	 *      Gets the previous spawn location
	 *      *
	 *      @return Location that used to be spawn
	 */
	public MCCLocation getPreviousLocation(){
		return previousLocation;
	}

}
