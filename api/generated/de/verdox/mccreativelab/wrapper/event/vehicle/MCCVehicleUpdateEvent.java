package de.verdox.mccreativelab.wrapper.event.vehicle;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a vehicle updates
 */

public class MCCVehicleUpdateEvent extends MCCVehicleEvent  {

	public MCCVehicleUpdateEvent(MCCEntity vehicle){
		super(vehicle);
	}

}
