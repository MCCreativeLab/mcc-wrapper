package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a players level changes
 */

public class MCCPlayerLevelChangeEvent extends MCCPlayerEvent  {

	private final int oldLevel;

	private final int newLevel;

	public MCCPlayerLevelChangeEvent(MCCEntity player, int oldLevel, int newLevel){
		super(player);
		this.oldLevel = oldLevel;
		this.newLevel = newLevel;
	}

	/**
	 *      Gets the old level of the player
	 *      *
	 *      @return The old level of the player
	 */
	public int getOldLevel(){
		return oldLevel;
	}

	/**
	 *      Gets the new level of the player
	 *      *
	 *      @return The new (current) level of the player
	 */
	public int getNewLevel(){
		return newLevel;
	}

}
