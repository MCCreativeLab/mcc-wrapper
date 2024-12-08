package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;

/**
 *  Called when leaves are decaying naturally.
 *  <p>
 *  If a Leaves Decay event is cancelled, the leaves will not decay.
 */

public class MCCLeavesDecayEvent extends MCCBlockEvent  {

	private boolean cancel;

	public MCCLeavesDecayEvent(MCCBlock block, boolean cancel){
		super(block);
		this.cancel = cancel;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
