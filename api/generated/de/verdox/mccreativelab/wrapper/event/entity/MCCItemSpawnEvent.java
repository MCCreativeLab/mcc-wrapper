package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when an item is spawned into a world
 */

public class MCCItemSpawnEvent extends MCCEntitySpawnEvent  {

	public MCCItemSpawnEvent(MCCEntity entity, boolean canceled){
		super(entity, canceled);
	}

}
