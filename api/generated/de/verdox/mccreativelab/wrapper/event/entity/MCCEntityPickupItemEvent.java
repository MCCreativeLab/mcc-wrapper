package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Thrown when an entity picks an item up from the ground
 */

public class MCCEntityPickupItemEvent extends MCCEntityEvent  {

	private final MCCEntity item;

	private boolean cancel;

	private final int remaining;

	public MCCEntityPickupItemEvent(MCCEntity entity, MCCEntity item, boolean cancel, int remaining){
		super(entity);
		this.item = item;
		this.cancel = cancel;
		this.remaining = remaining;
	}

	/**
	 *      Gets the Item picked up by the entity.
	 *      *
	 *      @return Item
	 */
	public MCCEntity getItem(){
		return item;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

	/**
	 *      Gets the amount remaining on the ground, if any
	 *      *
	 *      @return amount remaining on the ground
	 */
	public int getRemaining(){
		return remaining;
	}

}
