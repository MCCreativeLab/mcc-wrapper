package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockFace;

import java.util.List;

/**
 *  Called when a piston retracts
 */

public class MCCBlockPistonRetractEvent extends MCCBlockPistonEvent  {

	private List<MCCBlock> blocks;

	public MCCBlockPistonRetractEvent(MCCBlock block, boolean cancelled, MCCBlockFace direction, List<MCCBlock> blocks){
		super(block, cancelled, direction);
		this.blocks = blocks;
	}

	/**
	 *      Get an immutable list of the blocks which will be moved by the
	 *      retracting.
	 *      *
	 *      @return Immutable list of the moved blocks.
	 */
	public List<MCCBlock> getBlocks(){
		return blocks;
	}

}
