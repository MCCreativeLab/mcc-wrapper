package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlot;

/**
 *  Called when we try to place a block, to see if we can build it here or not.
 *  <p>
 *  Note:
 *  <ul>
 *  <li>The Block returned by getBlock() is the block we are trying to place
 *      on, not the block we are trying to place.
 *  <li>If you want to figure out what is being placed, use {@link
 *      #getMaterial()} instead.
 *  </ul>
 */

public class MCCBlockCanBuildEvent extends MCCBlockEvent  {

	private boolean buildable;

	private MCCBlockState blockData;

	private final MCCEntity player;

	private final MCCEquipmentSlot hand;

	public MCCBlockCanBuildEvent(MCCBlock block, boolean buildable, MCCBlockState blockData, MCCEntity player, MCCEquipmentSlot hand){
		super(block);
		this.buildable = buildable;
		this.blockData = blockData;
		this.player = player;
		this.hand = hand;
	}

	/**
	 *      Gets whether or not the block can be built here.
	 *      <p>
	 *      By default, returns Minecraft's answer on whether the block can be
	 *      built here or not.
	 *      *
	 *      @return boolean whether or not the block can be built
	 */
	public boolean isBuildable(){
		return buildable;
	}

	/**
	 *      Sets whether the block can be built here or not.
	 *      *
	 *      @param cancel true if you want to allow the block to be built here
	 *          despite Minecraft's default behaviour
	 */
	public void setBuildable(boolean buildable){
		this.buildable = buildable;
	}

	/**
	 *      Gets the Material that we are trying to place.
	 *      *
	 *      @return The Material that we are trying to place
	 */
	public MCCBlockState getMaterial(){
		return blockData;
	}

	/**
	 *      Gets the player who placed the block involved in this event.
	 *      <br>
	 *      May be null for legacy calls of the event.
	 *      *
	 *      @return The Player who placed the block involved in this event
	 */
	public MCCEntity getPlayer(){
		return player;
	}

	/**
	 *      Gets the hand the player will use to place the block
	 *      *
	 *      @return the EquipmentSlot representing the players hand.
	 */
	public MCCEquipmentSlot getHand(){
		return hand;
	}

}
