package de.verdox.mccreativelab.wrapper.event.vehicle;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Raised when an entity enters a vehicle.
 */

public class MCCVehicleEnterEvent extends MCCVehicleEvent  {

	private boolean cancelled;

	private final MCCEntity entered;

	public MCCVehicleEnterEvent(MCCEntity vehicle, boolean cancelled, MCCEntity entered){
		super(vehicle);
		this.cancelled = cancelled;
		this.entered = entered;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Gets the Entity that entered the vehicle.
	 *      *
	 *      @return the Entity that entered the vehicle
	 */
	public MCCEntity getEntered(){
		return entered;
	}

}
