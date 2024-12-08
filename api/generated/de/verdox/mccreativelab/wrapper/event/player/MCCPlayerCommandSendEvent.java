package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import java.util.Collection;
import java.lang.String;

/**
 *  This event is called when the list of available server commands is sent to
 *  the player.
 *  <br>
 *  Commands may be removed from display using this event, but implementations
 *  are not required to securely remove all traces of the command. If secure
 *  removal of commands is required, then the command should be assigned a
 *  permission which is not granted to the player.
 */

public class MCCPlayerCommandSendEvent extends MCCPlayerEvent  {

	private final Collection<String> commands;

	public MCCPlayerCommandSendEvent(MCCEntity player, Collection<String> commands){
		super(player);
		this.commands = commands;
	}

	/**
	 *      Returns a mutable collection of all top level commands to be sent.
	 *      <br>
	 *      It is not legal to add entries to this collection, only remove them.
	 *      Behaviour of adding entries is undefined.
	 *      *
	 *      @return collection of all commands
	 */
	public Collection<String> getCommands(){
		return commands;
	}

}
