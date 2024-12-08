package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlot;

/**
 *  Represents an event that is called when a player right clicks an entity.
 */

public class MCCPlayerInteractEntityEvent extends MCCPlayerEvent  {

	private MCCEntity clickedEntity;

	private boolean cancelled;

	private MCCEquipmentSlot hand;

	public MCCPlayerInteractEntityEvent(MCCEntity player, MCCEntity clickedEntity, boolean cancelled, MCCEquipmentSlot hand){
		super(player);
		this.clickedEntity = clickedEntity;
		this.cancelled = cancelled;
		this.hand = hand;
	}

	/**
	 *      Gets the entity that was right-clicked by the player.
	 *      *
	 *      @return entity right clicked by player
	 */
	public MCCEntity getRightClicked(){
		return clickedEntity;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      The hand used to perform this interaction.
	 *      *
	 *      @return the hand used to interact
	 */
	public MCCEquipmentSlot getHand(){
		return hand;
	}

}
