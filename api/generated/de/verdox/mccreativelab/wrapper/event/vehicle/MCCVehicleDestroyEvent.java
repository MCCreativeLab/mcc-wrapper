package de.verdox.mccreativelab.wrapper.event.vehicle;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Raised when a vehicle is destroyed, which could be caused by either a
 *  player or the environment. This is not raised if the boat is simply
 *  'removed' due to other means.
 */

public class MCCVehicleDestroyEvent extends MCCVehicleEvent  {

	private final MCCEntity attacker;

	private boolean cancelled;

	public MCCVehicleDestroyEvent(MCCEntity vehicle, MCCEntity attacker, boolean cancelled){
		super(vehicle);
		this.attacker = attacker;
		this.cancelled = cancelled;
	}

	/**
	 *      Gets the Entity that has destroyed the vehicle, potentially null
	 *      *
	 *      @return the Entity that has destroyed the vehicle, potentially null
	 */
	public MCCEntity getAttacker(){
		return attacker;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

}
