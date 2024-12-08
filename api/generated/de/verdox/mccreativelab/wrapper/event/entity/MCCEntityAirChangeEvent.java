package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when the amount of air an entity has remaining changes.
 */

public class MCCEntityAirChangeEvent extends MCCEntityEvent  {

	private int amount;

	private boolean cancelled;

	public MCCEntityAirChangeEvent(MCCEntity entity, int amount, boolean cancelled){
		super(entity);
		this.amount = amount;
		this.cancelled = cancelled;
	}

	/**
	 *      Gets the amount of air the entity has left (measured in ticks).
	 *      *
	 *      @return amount of air remaining
	 */
	public int getAmount(){
		return amount;
	}

	/**
	 *      Sets the amount of air remaining for the entity (measured in ticks.
	 *      *
	 *      @param amount amount of air remaining
	 */
	public void setAmount(int amount){
		this.amount = amount;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

}
