package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  This is called immediately after a player unregisters for a plugin channel.
 */

public class MCCPlayerUnregisterChannelEvent extends MCCPlayerChannelEvent  {

	public MCCPlayerUnregisterChannelEvent(MCCEntity player, String channel){
		super(player, channel);
	}

}
