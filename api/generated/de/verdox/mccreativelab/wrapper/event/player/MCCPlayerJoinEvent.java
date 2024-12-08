package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import net.kyori.adventure.text.Component;

/**
 *  Called when a player joins a server
 */

public class MCCPlayerJoinEvent extends MCCPlayerEvent  {

	private Component joinMessage;

	public MCCPlayerJoinEvent(MCCEntity player, Component joinMessage){
		super(player);
		this.joinMessage = joinMessage;
	}

	/**
	 *      Gets the join message to send to all online players
	 *      *
	 *      @return string join message. Can be null
	 *      @deprecated in favour of {@link #joinMessage()}
	 */
	public Component getJoinMessage(){
		return joinMessage;
	}

	/**
	 *      Sets the join message to send to all online players
	 *      *
	 *      @param joinMessage join message. If null, no message will be sent
	 *      @deprecated in favour of {@link #joinMessage(net.kyori.adventure.text.Component)}
	 */
	public void setJoinMessage(Component joinMessage){
		this.joinMessage = joinMessage;
	}

}
