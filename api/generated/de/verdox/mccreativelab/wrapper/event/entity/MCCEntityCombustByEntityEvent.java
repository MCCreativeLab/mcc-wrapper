package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when an entity causes another entity to combust.
 */

public class MCCEntityCombustByEntityEvent extends MCCEntityCombustEvent  {

	private final MCCEntity combuster;

	public MCCEntityCombustByEntityEvent(MCCEntity entity, float duration, boolean cancel, MCCEntity combuster){
		super(entity, duration, cancel);
		this.combuster = combuster;
	}

	/**
	 *      Get the entity that caused the combustion event.
	 *      *
	 *      @return the Entity that set the combustee alight.
	 */
	public MCCEntity getCombuster(){
		return combuster;
	}

}
