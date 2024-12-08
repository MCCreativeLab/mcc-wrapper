package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a player toggles their sprinting state
 */

public class MCCPlayerToggleSprintEvent extends MCCPlayerEvent  {

	private final boolean isSprinting;

	private boolean cancel;

	public MCCPlayerToggleSprintEvent(MCCEntity player, boolean isSprinting, boolean cancel){
		super(player);
		this.isSprinting = isSprinting;
		this.cancel = cancel;
	}

	/**
	 *      Gets whether the player is now sprinting or not.
	 *      *
	 *      @return sprinting state
	 */
	public boolean isSprinting(){
		return isSprinting;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
