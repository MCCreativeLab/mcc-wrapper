package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import java.lang.String;

/**
 *  This is called immediately after a player registers for a plugin channel.
 */

public class MCCPlayerRegisterChannelEvent extends MCCPlayerChannelEvent  {

	public MCCPlayerRegisterChannelEvent(MCCEntity player, String channel){
		super(player, channel);
	}

}
