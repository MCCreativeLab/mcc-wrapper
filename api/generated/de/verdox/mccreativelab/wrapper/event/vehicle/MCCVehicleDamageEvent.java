package de.verdox.mccreativelab.wrapper.event.vehicle;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Raised when a vehicle receives damage.
 */

public class MCCVehicleDamageEvent extends MCCVehicleEvent  {

	private final MCCEntity attacker;

	private double damage;

	private boolean cancelled;

	public MCCVehicleDamageEvent(MCCEntity vehicle, MCCEntity attacker, double damage, boolean cancelled){
		super(vehicle);
		this.attacker = attacker;
		this.damage = damage;
		this.cancelled = cancelled;
	}

	/**
	 *      Gets the Entity that is attacking the vehicle
	 *      *
	 *      @return the Entity that is attacking the vehicle
	 */
	public MCCEntity getAttacker(){
		return attacker;
	}

	/**
	 *      Gets the damage done to the vehicle
	 *      *
	 *      @return the damage done to the vehicle
	 */
	public double getDamage(){
		return damage;
	}

	/**
	 *      Sets the damage done to the vehicle
	 *      *
	 *      @param damage The damage
	 */
	public void setDamage(double damage){
		this.damage = damage;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

}
