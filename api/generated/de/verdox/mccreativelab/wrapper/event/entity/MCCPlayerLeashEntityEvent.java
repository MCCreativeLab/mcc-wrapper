package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.event.MCCEvent;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlot;

/**
 *  Called immediately prior to a creature being leashed by a player.
 */

public class MCCPlayerLeashEntityEvent implements MCCEvent  {

	private final MCCEntity leashHolder;

	private final MCCEntity entity;

	private boolean cancelled;

	private final MCCEntity player;

	private final MCCEquipmentSlot hand;

	public MCCPlayerLeashEntityEvent(MCCEntity leashHolder, MCCEntity entity, boolean cancelled, MCCEntity player, MCCEquipmentSlot hand){
		this.leashHolder = leashHolder;
		this.entity = entity;
		this.cancelled = cancelled;
		this.player = player;
		this.hand = hand;
	}

	/**
	 *      Returns the entity that is holding the leash.
	 *      *
	 *      @return The leash holder
	 */
	public MCCEntity getLeashHolder(){
		return leashHolder;
	}

	/**
	 *      Returns the entity being leashed.
	 *      *
	 *      @return The entity
	 */
	public MCCEntity getEntity(){
		return entity;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Returns the player involved in this event
	 *      *
	 *      @return Player who is involved in this event
	 */
	public MCCEntity getPlayer(){
		return player;
	}

	/**
	 *      Returns the hand used by the player to leash the entity.
	 *      *
	 *      @return the hand
	 */
	public MCCEquipmentSlot getHand(){
		return hand;
	}

}
