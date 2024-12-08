package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;

/**
 *  Called when any Entity changes a block and a more specific event is not available.
 */

public class MCCEntityChangeBlockEvent extends MCCEntityEvent  {

	private final MCCBlock block;

	private boolean cancel;

	private final MCCBlockState to;

	public MCCEntityChangeBlockEvent(MCCEntity entity, MCCBlock block, boolean cancel, MCCBlockState to){
		super(entity);
		this.block = block;
		this.cancel = cancel;
		this.to = to;
	}

	/**
	 *      Gets the block the entity is changing
	 *      *
	 *      @return the block that is changing
	 */
	public MCCBlock getBlock(){
		return block;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

	/**
	 *      Gets the Material that the block is changing into
	 *      *
	 *      @return the material that the block is changing into
	 */
	public MCCBlockState getTo(){
		return to;
	}

}
