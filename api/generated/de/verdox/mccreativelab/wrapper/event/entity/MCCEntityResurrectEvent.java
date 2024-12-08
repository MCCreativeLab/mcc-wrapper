package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlot;

/**
 *  Called when an entity dies and may have the opportunity to be resurrected.
 *  Will be called in a cancelled state if the entity does not have a totem
 *  equipped.
 */

public class MCCEntityResurrectEvent extends MCCEntityEvent  {

	private boolean cancelled;

	private final MCCEquipmentSlot hand;

	public MCCEntityResurrectEvent(MCCEntity entity, boolean cancelled, MCCEquipmentSlot hand){
		super(entity);
		this.cancelled = cancelled;
		this.hand = hand;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Get the hand in which the totem of undying was found, or null if the
	 *      entity did not have a totem of undying.
	 *      *
	 *      @return the hand, or null
	 */
	public MCCEquipmentSlot getHand(){
		return hand;
	}

}
