package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

import java.util.Set;

/**
 *  This event is called whenever a player runs a command (by placing a slash
 *  at the start of their message). It is called early in the command handling
 *  process, and modifications in this event (via {@link #setMessage(String)})
 *  will be shown in the behavior.
 *  <p>
 *  Many plugins will have <b>no use for this event</b>, and you should
 *  attempt to avoid using it if it is not necessary.
 *  <p>
 *  Some examples of valid uses for this event are:
 *  <ul>
 *  <li>Logging executed commands to a separate file
 *  <li>Variable substitution. For example, replacing
 *      <code>${nearbyPlayer}</code> with the name of the nearest other
 *      player, or simulating the <code>@a</code> and <code>@p</code>
 *      decorators used by Command Blocks in plugins that do not handle it.
 *  <li>Conditionally blocking commands belonging to other plugins. For
 *      example, blocking the use of the <code>/home</code> command in a
 *      combat arena.
 *  <li>Per-sender command aliases. For example, after a player runs the
 *      command <code>/calias cr gamemode creative</code>, the next time they
 *      run <code>/cr</code>, it gets replaced into
 *      <code>/gamemode creative</code>. (Global command aliases should be
 *      done by registering the alias.)
 *  </ul>
 *  <p>
 *  Examples of incorrect uses are:
 *  <ul>
 *  <li>Using this event to run command logic
 *  </ul>
 *  <p>
 *  If the event is cancelled, processing of the command will halt.
 *  <p>
 *  The state of whether or not there is a slash (<code>/</code>) at the
 *  beginning of the message should be preserved. If a slash is added or
 *  removed, unexpected behavior may result.
 */

public class MCCPlayerCommandPreprocessEvent extends MCCPlayerEvent  {

	private boolean cancel;

	private String message;

	private final Set<MCCEntity> recipients;

	public MCCPlayerCommandPreprocessEvent(MCCEntity player, boolean cancel, String message, Set<MCCEntity> recipients){
		super(player);
		this.cancel = cancel;
		this.message = message;
		this.recipients = recipients;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

	/**
	 *      Gets the command that the player is attempting to send.
	 *      <p>
	 *      All commands begin with a special character; implementations do not
	 *      consider the first character when executing the content.
	 *      *
	 *      @return Message the player is attempting to send
	 */
	public String getMessage(){
		return message;
	}

	/**
	 *      Sets the command that the player will send.
	 *      <p>
	 *      All commands begin with a special character; implementations do not
	 *      consider the first character when executing the content.
	 *      *
	 *      @param command New message that the player will send
	 *      @throws IllegalArgumentException if command is null or empty
	 */
	public void setMessage(String message){
		this.message = message;
	}

	/**
	 *      Gets a set of recipients that this chat message will be displayed to.
	 *      <p>
	 *      The set returned is not guaranteed to be mutable and may auto-populate
	 *      on access. Any listener accessing the returned set should be aware that
	 *      it may reduce performance for a lazy set implementation. Listeners
	 *      should be aware that modifying the list may throw {@link
	 *      UnsupportedOperationException} if the event caller provides an
	 *      unmodifiable set.
	 *      *
	 *      @return All Players who will see this chat message
	 *      @deprecated This is simply the online players. Modifications have no effect
	 */
	public Set<MCCEntity> getRecipients(){
		return recipients;
	}

}
