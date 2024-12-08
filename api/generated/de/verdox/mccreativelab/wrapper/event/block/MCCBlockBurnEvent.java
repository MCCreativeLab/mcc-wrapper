package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;

/**
 *  Called when a block is destroyed as a result of being burnt by fire.
 *  <p>
 *  If a Block Burn event is cancelled, the block will not be destroyed as a
 *  result of being burnt by fire.
 */

public class MCCBlockBurnEvent extends MCCBlockEvent  {

	private boolean cancelled;

	private final MCCBlock ignitingBlock;

	public MCCBlockBurnEvent(MCCBlock block, boolean cancelled, MCCBlock ignitingBlock){
		super(block);
		this.cancelled = cancelled;
		this.ignitingBlock = ignitingBlock;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Gets the block which ignited this block.
	 *      *
	 *      @return The Block that ignited and burned this block, or null if no
	 *      source block exists
	 */
	public MCCBlock getIgnitingBlock(){
		return ignitingBlock;
	}

}
