package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Fired when a player changes their currently held item
 */

public class MCCPlayerItemHeldEvent extends MCCPlayerEvent  {

	private boolean cancel;

	private final int previous;

	private final int current;

	public MCCPlayerItemHeldEvent(MCCEntity player, boolean cancel, int previous, int current){
		super(player);
		this.cancel = cancel;
		this.previous = previous;
		this.current = current;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

	/**
	 *      Gets the previous held slot index
	 *      *
	 *      @return Previous slot index
	 */
	public int getPreviousSlot(){
		return previous;
	}

	/**
	 *      Gets the new held slot index
	 *      *
	 *      @return New slot index
	 */
	public int getNewSlot(){
		return current;
	}

}
