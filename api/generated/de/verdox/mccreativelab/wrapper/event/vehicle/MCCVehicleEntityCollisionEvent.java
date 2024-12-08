package de.verdox.mccreativelab.wrapper.event.vehicle;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Raised when a vehicle collides with an entity.
 */

public class MCCVehicleEntityCollisionEvent extends MCCVehicleCollisionEvent  {

	private final MCCEntity entity;

	private boolean cancelled;

	private boolean cancelledPickup;

	private boolean cancelledCollision;

	public MCCVehicleEntityCollisionEvent(MCCEntity vehicle, MCCEntity entity, boolean cancelled, boolean cancelledPickup, boolean cancelledCollision){
		super(vehicle);
		this.entity = entity;
		this.cancelled = cancelled;
		this.cancelledPickup = cancelledPickup;
		this.cancelledCollision = cancelledCollision;
	}

	public MCCEntity getEntity(){
		return entity;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	public boolean isPickupCancelled(){
		return cancelledPickup;
	}

	public void setPickupCancelled(boolean cancelledPickup){
		this.cancelledPickup = cancelledPickup;
	}

	public boolean isCollisionCancelled(){
		return cancelledCollision;
	}

	public void setCollisionCancelled(boolean cancelledCollision){
		this.cancelledCollision = cancelledCollision;
	}

}
