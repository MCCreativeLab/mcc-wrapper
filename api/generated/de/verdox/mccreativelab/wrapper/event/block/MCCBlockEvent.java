package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.event.MCCEvent;

/**
 *  Represents a block related event.
 */

public class MCCBlockEvent implements MCCEvent  {

	private MCCBlock block;

	public MCCBlockEvent(MCCBlock block){
		this.block = block;
	}

	/**
	 *      Gets the block involved in this event.
	 *      *
	 *      @return The Block which block is involved in this event
	 */
	public MCCBlock getBlock(){
		return block;
	}

}
