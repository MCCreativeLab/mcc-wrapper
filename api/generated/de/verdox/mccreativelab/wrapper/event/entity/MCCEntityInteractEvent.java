package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;

/**
 *  Called when an entity interacts with an object
 */

public class MCCEntityInteractEvent extends MCCEntityEvent  {

	private MCCBlock block;

	private boolean cancelled;

	public MCCEntityInteractEvent(MCCEntity entity, MCCBlock block, boolean cancelled){
		super(entity);
		this.block = block;
		this.cancelled = cancelled;
	}

	/**
	 *      Returns the involved block
	 *      *
	 *      @return the block clicked with this item.
	 */
	public MCCBlock getBlock(){
		return block;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

}
