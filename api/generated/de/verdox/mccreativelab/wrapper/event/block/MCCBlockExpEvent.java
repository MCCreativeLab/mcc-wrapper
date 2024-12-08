package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;

/**
 *  An event that's called when a block yields experience.
 */

public class MCCBlockExpEvent extends MCCBlockEvent  {

	private int exp;

	public MCCBlockExpEvent(MCCBlock block, int exp){
		super(block);
		this.exp = exp;
	}

	/**
	 *      Get the experience dropped by the block after the event has processed
	 *      *
	 *      @return The experience to drop
	 */
	public int getExpToDrop(){
		return exp;
	}

	/**
	 *      Set the amount of experience dropped by the block after the event has
	 *      processed
	 *      *
	 *      @param exp 1 or higher to drop experience, else nothing will drop
	 */
	public void setExpToDrop(int exp){
		this.exp = exp;
	}

}
