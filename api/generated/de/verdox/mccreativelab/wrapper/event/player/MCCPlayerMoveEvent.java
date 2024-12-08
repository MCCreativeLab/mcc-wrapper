package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Holds information for player movement events
 */

public class MCCPlayerMoveEvent extends MCCPlayerEvent  {

	private boolean cancel;

	private MCCLocation from;

	private MCCLocation to;

	public MCCPlayerMoveEvent(MCCEntity player, boolean cancel, MCCLocation from, MCCLocation to){
		super(player);
		this.cancel = cancel;
		this.from = from;
		this.to = to;
	}

	/**
	 *      Gets the cancellation state of this event. A cancelled event will not
	 *      be executed in the server, but will still pass to other plugins
	 *      <p>
	 *      If a move or teleport event is cancelled, the player will be moved or
	 *      teleported back to the Location as defined by getFrom(). This will not
	 *      fire an event
	 *      *
	 *      @return true if this event is cancelled
	 */
	public boolean isCancelled(){
		return cancel;
	}

	/**
	 *      Sets the cancellation state of this event. A cancelled event will not
	 *      be executed in the server, but will still pass to other plugins
	 *      <p>
	 *      If a move or teleport event is cancelled, the player will be moved or
	 *      teleported back to the Location as defined by getFrom(). This will not
	 *      fire an event
	 *      *
	 *      @param cancel true if you wish to cancel this event
	 */
	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

	/**
	 *      Gets the location this player moved from
	 *      *
	 *      @return Location the player moved from
	 */
	public MCCLocation getFrom(){
		return from;
	}

	/**
	 *      Sets the location to mark as where the player moved from
	 *      *
	 *      @param from New location to mark as the players previous location
	 */
	public void setFrom(MCCLocation from){
		this.from = from;
	}

	/**
	 *      Gets the location this player moved to
	 *      *
	 *      @return Location the player moved to
	 */
	public MCCLocation getTo(){
		return to;
	}

	/**
	 *      Sets the location that this player will move to
	 *      *
	 *      @param to New Location this player will move to
	 */
	public void setTo(MCCLocation to){
		this.to = to;
	}

}
