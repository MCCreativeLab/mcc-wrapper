package de.verdox.mccreativelab.wrapper.event.vehicle;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Raised when a vehicle is created.
 */

public class MCCVehicleCreateEvent extends MCCVehicleEvent  {

	private boolean cancelled;

	public MCCVehicleCreateEvent(MCCEntity vehicle, boolean cancelled){
		super(vehicle);
		this.cancelled = cancelled;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

}
