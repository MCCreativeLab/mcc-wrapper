package de.verdox.mccreativelab.wrapper.event.vehicle;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Raised when a vehicle moves.
 */

public class MCCVehicleMoveEvent extends MCCVehicleEvent  {

	private final MCCLocation from;

	private final MCCLocation to;

	public MCCVehicleMoveEvent(MCCEntity vehicle, MCCLocation from, MCCLocation to){
		super(vehicle);
		this.from = from;
		this.to = to;
	}

	/**
	 *      Get the previous position.
	 *      *
	 *      @return Old position.
	 */
	public MCCLocation getFrom(){
		return from;
	}

	/**
	 *      Get the next position.
	 *      *
	 *      @return New position.
	 */
	public MCCLocation getTo(){
		return to;
	}

}
