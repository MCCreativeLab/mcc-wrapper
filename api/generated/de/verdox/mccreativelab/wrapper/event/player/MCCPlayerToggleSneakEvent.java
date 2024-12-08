package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a player toggles their sneaking state
 */

public class MCCPlayerToggleSneakEvent extends MCCPlayerEvent  {

	private final boolean isSneaking;

	private boolean cancel;

	public MCCPlayerToggleSneakEvent(MCCEntity player, boolean isSneaking, boolean cancel){
		super(player);
		this.isSneaking = isSneaking;
		this.cancel = cancel;
	}

	/**
	 *      Returns whether the player is now sneaking or not.
	 *      *
	 *      @return sneaking state
	 */
	public boolean isSneaking(){
		return isSneaking;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
