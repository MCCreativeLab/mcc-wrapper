package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;

/**
 *  Called when the fluid level of a block changes due to changes in adjacent
 *  blocks.
 */

public class MCCFluidLevelChangeEvent extends MCCBlockEvent  {

	private boolean cancelled;

	private MCCBlockState newData;

	public MCCFluidLevelChangeEvent(MCCBlock block, boolean cancelled, MCCBlockState newData){
		super(block);
		this.cancelled = cancelled;
		this.newData = newData;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Gets the new data of the changed block.
	 *      *
	 *      @return new data
	 */
	public MCCBlockState getNewData(){
		return newData;
	}

	/**
	 *      Sets the new data of the changed block. Must be of the same Material as
	 *      the old one.
	 *      *
	 *      @param newData the new data
	 */
	public void setNewData(MCCBlockState newData){
		this.newData = newData;
	}

}
