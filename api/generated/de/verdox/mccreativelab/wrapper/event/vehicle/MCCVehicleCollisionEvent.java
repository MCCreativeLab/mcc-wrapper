package de.verdox.mccreativelab.wrapper.event.vehicle;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Raised when a vehicle collides.
 */

public class MCCVehicleCollisionEvent extends MCCVehicleEvent  {

	public MCCVehicleCollisionEvent(MCCEntity vehicle){
		super(vehicle);
	}

}
