package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;

/**
 *  Called when an {@link Entity} enters a block and is stored in that block.
 *  <p>
 *  This event is called for bees entering a bee hive.
 *  <br>
 *  It is not called when a silverfish "enters" a stone block. For that listen to
 *  the {@link EntityChangeBlockEvent}.
 */

public class MCCEntityEnterBlockEvent extends MCCEntityEvent  {

	private final MCCBlock block;

	private boolean cancel;

	public MCCEntityEnterBlockEvent(MCCEntity entity, MCCBlock block, boolean cancel){
		super(entity);
		this.block = block;
		this.cancel = cancel;
	}

	/**
	 *      Get the block the entity will enter.
	 *      *
	 *      @return the block
	 */
	public MCCBlock getBlock(){
		return block;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
