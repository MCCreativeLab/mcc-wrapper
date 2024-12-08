package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when an arrow enters or exists an entity's body.
 */

public class MCCArrowBodyCountChangeEvent extends MCCEntityEvent  {

	private boolean cancelled;

	private final boolean isReset;

	private final int oldAmount;

	private int newAmount;

	public MCCArrowBodyCountChangeEvent(MCCEntity entity, boolean cancelled, boolean isReset, int oldAmount, int newAmount){
		super(entity);
		this.cancelled = cancelled;
		this.isReset = isReset;
		this.oldAmount = oldAmount;
		this.newAmount = newAmount;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Whether the event was called because the entity was reset.
	 *      *
	 *      @return was reset
	 */
	public boolean isReset(){
		return isReset;
	}

	/**
	 *      Gets the old amount of arrows in the entity's body.
	 *      *
	 *      @return amount of arrows
	 */
	public int getOldAmount(){
		return oldAmount;
	}

	/**
	 *      Get the new amount of arrows in the entity's body.
	 *      *
	 *      @return amount of arrows
	 */
	public int getNewAmount(){
		return newAmount;
	}

	/**
	 *      Sets the final amount of arrows in the entity's body.
	 *      *
	 *      @param newAmount amount of arrows
	 */
	public void setNewAmount(int newAmount){
		this.newAmount = newAmount;
	}

}
