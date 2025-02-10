package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockFace;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlot;

/**
 *  Triggered when an entity is created in the world by a player "placing" an item
 *  on a block.
 *  <br>
 *  Note that this event is currently only fired for four specific placements:
 *  armor stands, boats, minecarts, and end crystals.
 */

public class MCCEntityPlaceEvent extends MCCEntityEvent  {

	private boolean cancelled;

	private final MCCEntity player;

	private final MCCBlock block;

	private final MCCBlockFace blockFace;

	private final MCCEquipmentSlot hand;

	public MCCEntityPlaceEvent(MCCEntity entity, boolean cancelled, MCCEntity player, MCCBlock block, MCCBlockFace blockFace, MCCEquipmentSlot hand){
		super(entity);
		this.cancelled = cancelled;
		this.player = player;
		this.block = block;
		this.blockFace = blockFace;
		this.hand = hand;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Returns the player placing the entity
	 *      *
	 *      @return the player placing the entity
	 */
	public MCCEntity getPlayer(){
		return player;
	}

	/**
	 *      Returns the block that the entity was placed on
	 *      *
	 *      @return the block that the entity was placed on
	 */
	public MCCBlock getBlock(){
		return block;
	}

	/**
	 *      Returns the face of the block that the entity was placed on
	 *      *
	 *      @return the face of the block that the entity was placed on
	 */
	public MCCBlockFace getBlockFace(){
		return blockFace;
	}

	/**
	 *      Get the hand used to place the entity.
	 *      *
	 *      @return the hand
	 */
	public MCCEquipmentSlot getHand(){
		return hand;
	}

}
