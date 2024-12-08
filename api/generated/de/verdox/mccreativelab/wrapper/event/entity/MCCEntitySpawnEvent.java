package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when an entity is spawned into a world.
 *  <p>
 *  If an Entity Spawn event is cancelled, the entity will not spawn.
 */

public class MCCEntitySpawnEvent extends MCCEntityEvent  {

	private boolean canceled;

	public MCCEntitySpawnEvent(MCCEntity entity, boolean canceled){
		super(entity);
		this.canceled = canceled;
	}

	public boolean isCancelled(){
		return canceled;
	}

	public void setCancelled(boolean canceled){
		this.canceled = canceled;
	}

}
