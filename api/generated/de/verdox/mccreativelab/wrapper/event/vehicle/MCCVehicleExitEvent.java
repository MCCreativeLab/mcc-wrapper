package de.verdox.mccreativelab.wrapper.event.vehicle;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Raised when a living entity exits a vehicle.
 */

public class MCCVehicleExitEvent extends MCCVehicleEvent  {

	private boolean cancelled;

	private final MCCEntity exited;

	private final boolean isCancellable;

	public MCCVehicleExitEvent(MCCEntity vehicle, boolean cancelled, MCCEntity exited, boolean isCancellable){
		super(vehicle);
		this.cancelled = cancelled;
		this.exited = exited;
		this.isCancellable = isCancellable;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Get the living entity that exited the vehicle.
	 *      *
	 *      @return The entity.
	 */
	public MCCEntity getExited(){
		return exited;
	}

	public boolean isCancellable(){
		return isCancellable;
	}

}
