package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockFace;

/**
 *  Represents events with a source block and a destination block, currently
 *  only applies to liquid (lava and water) and teleporting dragon eggs.
 *  <p>
 *  If a Block From To event is cancelled, the block will not move (the liquid
 *  will not flow).
 */

public class MCCBlockFromToEvent extends MCCBlockEvent  {

	private MCCBlock to;

	private MCCBlockFace face;

	private boolean cancel;

	public MCCBlockFromToEvent(MCCBlock block, MCCBlock to, MCCBlockFace face, boolean cancel){
		super(block);
		this.to = to;
		this.face = face;
		this.cancel = cancel;
	}

	/**
	 *      Convenience method for getting the faced Block.
	 *      *
	 *      @return The faced Block
	 */
	public MCCBlock getToBlock(){
		return to;
	}

	/**
	 *      Gets the BlockFace that the block is moving to.
	 *      *
	 *      @return The BlockFace that the block is moving to
	 */
	public MCCBlockFace getFace(){
		return face;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
