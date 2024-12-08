package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Sent when an entity's swimming status is toggled.
 */

public class MCCEntityToggleSwimEvent extends MCCEntityEvent  {

	private boolean cancel;

	private final boolean isSwimming;

	public MCCEntityToggleSwimEvent(MCCEntity entity, boolean cancel, boolean isSwimming){
		super(entity);
		this.cancel = cancel;
		this.isSwimming = isSwimming;
	}

	public boolean isCancelled(){
		return cancel;
	}

	/**
	 *      @deprecated This does nothing, the server and the client doesn't work
	 *      correctly when the server try to bypass this. A current workaround
	 *      exists. If you want to cancel the switch from the ground state to the
	 *      swimming state you need to disable the sprinting flag for the player after
	 *      the cancel action.
	 */
	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

	/**
	 *      Returns true if the entity is now swims or
	 *      false if the entity stops swimming.
	 *      *
	 *      @return new swimming state
	 */
	public boolean isSwimming(){
		return isSwimming;
	}

}
