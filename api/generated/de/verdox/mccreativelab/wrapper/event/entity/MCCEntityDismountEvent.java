package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when an entity stops riding another entity.
 */

public class MCCEntityDismountEvent extends MCCEntityEvent  {

	private boolean cancelled;

	private final MCCEntity dismounted;

	private final boolean isCancellable;

	public MCCEntityDismountEvent(MCCEntity entity, boolean cancelled, MCCEntity dismounted, boolean isCancellable){
		super(entity);
		this.cancelled = cancelled;
		this.dismounted = dismounted;
		this.isCancellable = isCancellable;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Gets the entity which will no longer be ridden.
	 *      *
	 *      @return dismounted entity
	 */
	public MCCEntity getDismounted(){
		return dismounted;
	}

	public boolean isCancellable(){
		return isCancellable;
	}

}
