package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a player toggles their flying state
 */

public class MCCPlayerToggleFlightEvent extends MCCPlayerEvent  {

	private final boolean isFlying;

	private boolean cancel;

	public MCCPlayerToggleFlightEvent(MCCEntity player, boolean isFlying, boolean cancel){
		super(player);
		this.isFlying = isFlying;
		this.cancel = cancel;
	}

	/**
	 *      Returns whether the player is trying to start or stop flying.
	 *      *
	 *      @return flying state
	 */
	public boolean isFlying(){
		return isFlying;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
