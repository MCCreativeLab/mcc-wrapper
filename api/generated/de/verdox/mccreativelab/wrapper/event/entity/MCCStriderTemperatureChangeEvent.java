package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a {@link Strider}'s temperature has changed as a result of
 *  entering or exiting blocks it considers warm.
 */

public class MCCStriderTemperatureChangeEvent extends MCCEntityEvent  {

	private final boolean shivering;

	private boolean cancelled;

	public MCCStriderTemperatureChangeEvent(MCCEntity entity, boolean shivering, boolean cancelled){
		super(entity);
		this.shivering = shivering;
		this.cancelled = cancelled;
	}

	/**
	 *      Get the Strider's new shivering state.
	 *      *
	 *      @return the new shivering state
	 */
	public boolean isShivering(){
		return shivering;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

}
