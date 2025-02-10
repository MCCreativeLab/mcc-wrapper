package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockFace;

/**
 *  Called when a piston block is triggered
 */

public class MCCBlockPistonEvent extends MCCBlockEvent  {

	private boolean cancelled;

	private final MCCBlockFace direction;

	public MCCBlockPistonEvent(MCCBlock block, boolean cancelled, MCCBlockFace direction){
		super(block);
		this.cancelled = cancelled;
		this.direction = direction;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Return the direction in which the piston will operate.
	 *      *
	 *      @return direction of the piston
	 */
	public MCCBlockFace getDirection(){
		return direction;
	}

}
