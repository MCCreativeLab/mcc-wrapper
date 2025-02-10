package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when an {@link Entity} breaks a door
 *  <p>
 *  Cancelling the event will cause the event to be delayed
 */

public class MCCEntityBreakDoorEvent extends MCCEntityChangeBlockEvent  {

	public MCCEntityBreakDoorEvent(MCCEntity entity, MCCBlock block, boolean cancel, MCCBlockState to){
		super(entity, block, cancel, to);
	}

}
