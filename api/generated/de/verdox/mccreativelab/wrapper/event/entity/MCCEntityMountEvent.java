package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when an entity attempts to ride another entity.
 */

public class MCCEntityMountEvent extends MCCEntityEvent  {

	private boolean cancelled;

	private final MCCEntity mount;

	public MCCEntityMountEvent(MCCEntity entity, boolean cancelled, MCCEntity mount){
		super(entity);
		this.cancelled = cancelled;
		this.mount = mount;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Gets the entity which will be ridden.
	 *      *
	 *      @return mounted entity
	 */
	public MCCEntity getMount(){
		return mount;
	}

}
