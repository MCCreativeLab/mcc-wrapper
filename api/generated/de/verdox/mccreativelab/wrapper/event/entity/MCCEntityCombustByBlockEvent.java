package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a block causes an entity to combust.
 */

public class MCCEntityCombustByBlockEvent extends MCCEntityCombustEvent  {

	private final MCCBlock combuster;

	public MCCEntityCombustByBlockEvent(MCCEntity entity, float duration, boolean cancel, MCCBlock combuster){
		super(entity, duration, cancel);
		this.combuster = combuster;
	}

	/**
	 *      The combuster can be lava or a block that is on fire.
	 *      <p>
	 *      WARNING: block may be null.
	 *      *
	 *      @return the Block that set the combustee alight.
	 */
	public MCCBlock getCombuster(){
		return combuster;
	}

}
