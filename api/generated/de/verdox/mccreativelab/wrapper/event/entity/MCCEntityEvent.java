package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.event.MCCEvent;

/**
 *  Represents an Entity-related event
 */

public class MCCEntityEvent implements MCCEvent  {

	private MCCEntity entity;

	public MCCEntityEvent(MCCEntity entity){
		this.entity = entity;
	}

	/**
	 *      Returns the Entity involved in this event
	 *      *
	 *      @return Entity who is involved in this event
	 */
	public MCCEntity getEntity(){
		return entity;
	}

}
