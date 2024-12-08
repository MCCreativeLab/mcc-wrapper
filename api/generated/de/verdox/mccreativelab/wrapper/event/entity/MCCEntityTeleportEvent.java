package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Thrown when a non-player entity is teleported from one location to another.
 *  <br>
 *  This may be as a result of natural causes (Enderman, Shulker), pathfinding
 *  (Wolf), or commands (/teleport).
 */

public class MCCEntityTeleportEvent extends MCCEntityEvent  {

	private boolean cancel;

	private MCCLocation from;

	private MCCLocation to;

	public MCCEntityTeleportEvent(MCCEntity entity, boolean cancel, MCCLocation from, MCCLocation to){
		super(entity);
		this.cancel = cancel;
		this.from = from;
		this.to = to;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

	/**
	 *      Gets the location that this entity moved from
	 *      *
	 *      @return Location this entity moved from
	 */
	public MCCLocation getFrom(){
		return from;
	}

	/**
	 *      Sets the location that this entity moved from
	 *      *
	 *      @param from New location this entity moved from
	 */
	public void setFrom(MCCLocation from){
		this.from = from;
	}

	/**
	 *      Gets the location that this entity moved to
	 *      *
	 *      @return Location the entity moved to
	 */
	public MCCLocation getTo(){
		return to;
	}

	/**
	 *      Sets the location that this entity moved to
	 *      *
	 *      @param to New Location this entity moved to
	 */
	public void setTo(MCCLocation to){
		this.to = to;
	}

}
