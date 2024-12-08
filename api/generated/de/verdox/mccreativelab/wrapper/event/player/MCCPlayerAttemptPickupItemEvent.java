package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Thrown when a player attempts to pick an item up from the ground
 */

public class MCCPlayerAttemptPickupItemEvent extends MCCPlayerEvent  {

	private final MCCEntity item;

	private final int remaining;

	private boolean flyAtPlayer;

	private boolean cancelled;

	public MCCPlayerAttemptPickupItemEvent(MCCEntity player, MCCEntity item, int remaining, boolean flyAtPlayer, boolean cancelled){
		super(player);
		this.item = item;
		this.remaining = remaining;
		this.flyAtPlayer = flyAtPlayer;
		this.cancelled = cancelled;
	}

	/**
	 *      Gets the Item attempted by the player.
	 *      *
	 *      @return Item
	 */
	public MCCEntity getItem(){
		return item;
	}

	/**
	 *      Gets the amount that will remain on the ground, if any
	 *      *
	 *      @return amount that will remain on the ground
	 */
	public int getRemaining(){
		return remaining;
	}

	/**
	 *      Gets if the item will fly at the player
	 *      *
	 *      @return {@code true} if the item will fly at the player
	 */
	public boolean getFlyAtPlayer(){
		return flyAtPlayer;
	}

	/**
	 *      Set if the item will fly at the player
	 *      <p>Cancelling the event will set this value to false.</p>
	 *      *
	 *      @param flyAtPlayer {@code true} for item to fly at player
	 */
	public void setFlyAtPlayer(boolean flyAtPlayer){
		this.flyAtPlayer = flyAtPlayer;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

}
