package de.verdox.mccreativelab.wrapper.event.vehicle;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.event.MCCEvent;

/**
 *  Represents a vehicle-related event.
 */

public class MCCVehicleEvent implements MCCEvent  {

	private MCCEntity vehicle;

	public MCCVehicleEvent(MCCEntity vehicle){
		this.vehicle = vehicle;
	}

	/**
	 *      Get the vehicle.
	 *      *
	 *      @return the vehicle
	 */
	public MCCEntity getVehicle(){
		return vehicle;
	}

}
