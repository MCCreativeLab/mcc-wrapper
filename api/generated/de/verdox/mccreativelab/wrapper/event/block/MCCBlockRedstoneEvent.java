package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;

/**
 *  Called when a redstone current changes
 */

public class MCCBlockRedstoneEvent extends MCCBlockEvent  {

	private final int oldCurrent;

	private int newCurrent;

	public MCCBlockRedstoneEvent(MCCBlock block, int oldCurrent, int newCurrent){
		super(block);
		this.oldCurrent = oldCurrent;
		this.newCurrent = newCurrent;
	}

	/**
	 *      Gets the old current of this block
	 *      *
	 *      @return The previous current
	 */
	public int getOldCurrent(){
		return oldCurrent;
	}

	/**
	 *      Gets the new current of this block
	 *      *
	 *      @return The new current
	 */
	public int getNewCurrent(){
		return newCurrent;
	}

	/**
	 *      Sets the new current of this block
	 *      *
	 *      @param newCurrent The new current to set
	 */
	public void setNewCurrent(int newCurrent){
		this.newCurrent = newCurrent;
	}

}
