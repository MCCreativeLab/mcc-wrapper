package de.verdox.mccreativelab.wrapper.event.inventory;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.event.block.MCCBlockExpEvent;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;

/**
 *  This event is called when a player takes items out of a furnace-like block such as a
 *  {@link org.bukkit.block.Furnace}, {@link org.bukkit.block.Smoker}, or
 *  {@link org.bukkit.block.BlastFurnace}.
 */

public class MCCFurnaceExtractEvent extends MCCBlockExpEvent  {

	private final MCCEntity player;

	private final MCCItemType itemType;

	private final int itemAmount;

	public MCCFurnaceExtractEvent(MCCBlock block, int exp, MCCEntity player, MCCItemType itemType, int itemAmount){
		super(block, exp);
		this.player = player;
		this.itemType = itemType;
		this.itemAmount = itemAmount;
	}

	/**
	 *      Get the player that triggered the event
	 *      *
	 *      @return the relevant player
	 */
	public MCCEntity getPlayer(){
		return player;
	}

	/**
	 *      Get the Material of the item being retrieved
	 *      *
	 *      @return the material of the item
	 */
	public MCCItemType getItemType(){
		return itemType;
	}

	/**
	 *      Get the item count being retrieved
	 *      *
	 *      @return the amount of the item
	 */
	public int getItemAmount(){
		return itemAmount;
	}

}
