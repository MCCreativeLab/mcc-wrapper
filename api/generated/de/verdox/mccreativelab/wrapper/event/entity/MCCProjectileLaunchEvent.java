package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a projectile is launched.
 */

public class MCCProjectileLaunchEvent extends MCCEntitySpawnEvent  {

	private boolean cancelled;

	public MCCProjectileLaunchEvent(MCCEntity entity, boolean canceled, boolean cancelled){
		super(entity, canceled);
		this.cancelled = cancelled;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

}
