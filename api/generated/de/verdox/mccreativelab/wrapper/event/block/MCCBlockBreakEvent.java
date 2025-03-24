package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a block is broken by a player.
 *  <p>
 *  If you wish to have the block drop experience, you must set the experience
 *  value above 0. By default, experience will be set in the event if:
 *  <ol>
 *  <li>The player is not in creative or adventure mode
 *  <li>The player can loot the block (ie: does not destroy it completely, by
 *      using the correct tool)
 *  <li>The player does not have silk touch
 *  <li>The block drops experience in vanilla Minecraft
 *  </ol>
 *  <p>
 *  Note:
 *  Plugins wanting to simulate a traditional block drop should set the block
 *  to air and utilize their own methods for determining what the default drop
 *  for the block being broken is and what to do about it, if anything.
 *  <p>
 *  If a Block Break event is cancelled, the block will not break and
 *  experience will not drop.
 */

public class MCCBlockBreakEvent extends MCCBlockExpEvent  {

	private final MCCEntity player;

	private boolean dropItems;

	private boolean cancel;

	public MCCBlockBreakEvent(MCCBlock block, int exp, MCCEntity player, boolean dropItems, boolean cancel){
		super(block, exp);
		this.player = player;
		this.dropItems = dropItems;
		this.cancel = cancel;
	}

	/**
	 *      Gets the Player that is breaking the block involved in this event.
	 *      *
	 *      @return The Player that is breaking the block involved in this event
	 */
	public MCCEntity getPlayer(){
		return player;
	}

	/**
	 *      Gets whether or not the block will attempt to drop items.
	 *      *
	 *      If and only if this is false then {@link BlockDropItemEvent} will not be
	 *      called after this event.
	 *      *
	 *      @return Whether or not the block will attempt to drop items
	 */
	public boolean isDropItems(){
		return dropItems;
	}

	/**
	 *      Sets whether or not the block will attempt to drop items as it normally
	 *      would.
	 *      *
	 *      If and only if this is false then {@link BlockDropItemEvent} will not be
	 *      called after this event.
	 *      *
	 *      @param dropItems Whether or not the block will attempt to drop items
	 */
	public void setDropItems(boolean dropItems){
		this.dropItems = dropItems;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
