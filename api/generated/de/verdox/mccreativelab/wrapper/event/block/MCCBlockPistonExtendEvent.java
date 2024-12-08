package de.verdox.mccreativelab.wrapper.event.block;

import java.util.List;
import de.verdox.mccreativelab.wrapper.block.MCCBlockFace;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;

/**
 *  Called when a piston extends
 */

public class MCCBlockPistonExtendEvent extends MCCBlockPistonEvent  {

	private final int length;

	private List<MCCBlock> blocks;

	public MCCBlockPistonExtendEvent(MCCBlock block, boolean cancelled, MCCBlockFace direction, int length, List<MCCBlock> blocks){
		super(block, cancelled, direction);
		this.length = length;
		this.blocks = blocks;
	}

	/**
	 *      Get the amount of blocks which will be moved while extending.
	 *      *
	 *      @return the amount of moving blocks
	 *      @deprecated slime blocks make the value of this method
	 *               inaccurate due to blocks being pushed at the side
	 */
	public int getLength(){
		return length;
	}

	/**
	 *      Get an immutable list of the blocks which will be moved by the
	 *      extending.
	 *      *
	 *      @return Immutable list of the moved blocks.
	 */
	public List<MCCBlock> getBlocks(){
		return blocks;
	}

}
