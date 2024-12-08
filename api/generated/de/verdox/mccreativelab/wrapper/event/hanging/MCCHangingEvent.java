package de.verdox.mccreativelab.wrapper.event.hanging;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.event.MCCEvent;

/**
 *  Represents a hanging entity-related event.
 */

public class MCCHangingEvent implements MCCEvent  {

	private MCCEntity hanging;

	public MCCHangingEvent(MCCEntity hanging){
		this.hanging = hanging;
	}

	/**
	 *      Gets the hanging entity involved in this event.
	 *      *
	 *      @return the hanging entity
	 */
	public MCCEntity getEntity(){
		return hanging;
	}

}
