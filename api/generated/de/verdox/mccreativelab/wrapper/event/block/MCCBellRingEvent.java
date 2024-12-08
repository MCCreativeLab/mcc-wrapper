package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlockFace;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;

/**
 *  Called when a bell is being rung.
 */

public class MCCBellRingEvent extends MCCBlockEvent  {

	private final MCCBlockFace direction;

	private final MCCEntity entity;

	private boolean cancelled;

	public MCCBellRingEvent(MCCBlock block, MCCBlockFace direction, MCCEntity entity, boolean cancelled){
		super(block);
		this.direction = direction;
		this.entity = entity;
		this.cancelled = cancelled;
	}

	/**
	 *      Get the direction in which the bell was rung.
	 *      *
	 *      @return the direction
	 */
	public MCCBlockFace getDirection(){
		return direction;
	}

	/**
	 *      Get the {@link Entity} that rang the bell (if there was one).
	 *      *
	 *      @return the entity
	 */
	public MCCEntity getEntity(){
		return entity;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

}
