package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;

/**
 *  Called when a player switches to another world.
 */

public class MCCPlayerChangedWorldEvent extends MCCPlayerEvent  {

	private final MCCWorld from;

	public MCCPlayerChangedWorldEvent(MCCEntity player, MCCWorld from){
		super(player);
		this.from = from;
	}

	/**
	 *      Gets the world the player is switching from.
	 *      *
	 *      @return  player's previous world
	 */
	public MCCWorld getFrom(){
		return from;
	}

}
