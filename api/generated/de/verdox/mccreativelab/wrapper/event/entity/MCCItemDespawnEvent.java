package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  This event is called when a {@link org.bukkit.entity.Item} is removed from
 *  the world because it has existed for 5 minutes.
 *  <p>
 *  Cancelling the event results in the item being allowed to exist for 5 more
 *  minutes. This behavior is not guaranteed and may change in future versions.
 */

public class MCCItemDespawnEvent extends MCCEntityEvent  {

	private boolean canceled;

	private final MCCLocation location;

	public MCCItemDespawnEvent(MCCEntity entity, boolean canceled, MCCLocation location){
		super(entity);
		this.canceled = canceled;
		this.location = location;
	}

	public boolean isCancelled(){
		return canceled;
	}

	public void setCancelled(boolean canceled){
		this.canceled = canceled;
	}

	/**
	 *      Gets the location at which the item is despawning.
	 *      *
	 *      @return The location at which the item is despawning
	 */
	public MCCLocation getLocation(){
		return location;
	}

}
