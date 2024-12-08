package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.event.MCCEvent;

/**
 *  Represents a player related event
 */

public class MCCPlayerEvent implements MCCEvent  {

	private MCCEntity player;

	public MCCPlayerEvent(MCCEntity player){
		this.player = player;
	}

	/**
	 *      Returns the player involved in this event
	 *      *
	 *      @return Player who is involved in this event
	 */
	public MCCEntity getPlayer(){
		return player;
	}

}
