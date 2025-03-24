package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  This event is fired when the player is leaving a bed.
 */

public class MCCPlayerBedLeaveEvent extends MCCPlayerEvent  {

	private final MCCBlock bed;

	private boolean setBedSpawn;

	private boolean cancelled;

	public MCCPlayerBedLeaveEvent(MCCEntity player, MCCBlock bed, boolean setBedSpawn, boolean cancelled){
		super(player);
		this.bed = bed;
		this.setBedSpawn = setBedSpawn;
		this.cancelled = cancelled;
	}

	/**
	 *      Returns the bed block involved in this event.
	 *      *
	 *      @return the bed block involved in this event
	 */
	public MCCBlock getBed(){
		return bed;
	}

	/**
	 *      Get if this event should set the new spawn location for the
	 *      {@link Player}.
	 *      <br>
	 *      This does not remove any existing spawn location, only prevent it from
	 *      being changed (if true).
	 *      <br>
	 *      To change a {@link Player}'s spawn location, use
	 *      {@link Player#setBedSpawnLocation(Location)}.
	 *      *
	 *      @return true if the spawn location will be changed
	 *      @deprecated the respawn point is now set when the player enter the bed and
	 *      this option doesn't work since MC 1.15.
	 */
	public boolean shouldSetSpawnLocation(){
		return setBedSpawn;
	}

	/**
	 *      Set if this event should set the new spawn location for the
	 *      {@link Player}.
	 *      <br>
	 *      This will not remove any existing spawn location, only prevent it from
	 *      being changed (if true).
	 *      <br>
	 *      To change a {@link Player}'s spawn location, use
	 *      {@link Player#setBedSpawnLocation(Location)}.
	 *      *
	 *      @param setBedSpawn true to change the new spawn location
	 *      @deprecated the respawn point is now set when the player enter the bed and
	 *      this option doesn't work since MC 1.15.
	 */
	public void setSpawnLocation(boolean setBedSpawn){
		this.setBedSpawn = setBedSpawn;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

}
