package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Thrown when a player drops an item from their inventory
 */

public class MCCPlayerDropItemEvent extends MCCPlayerEvent  {

	private final MCCEntity drop;

	private boolean cancel;

	public MCCPlayerDropItemEvent(MCCEntity player, MCCEntity drop, boolean cancel){
		super(player);
		this.drop = drop;
		this.cancel = cancel;
	}

	/**
	 *      Gets the ItemDrop created by the player
	 *      *
	 *      @return ItemDrop created by the player
	 */
	public MCCEntity getItemDrop(){
		return drop;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
