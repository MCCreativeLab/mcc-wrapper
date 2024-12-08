package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import java.lang.String;

/**
 *  This event is called after a player registers or unregisters a new plugin
 *  channel.
 */

public class MCCPlayerChannelEvent extends MCCPlayerEvent  {

	private final String channel;

	public MCCPlayerChannelEvent(MCCEntity player, String channel){
		super(player);
		this.channel = channel;
	}

	public String getChannel(){
		return channel;
	}

}
