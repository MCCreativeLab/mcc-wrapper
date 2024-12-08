package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Thrown when an entity creates an item drop.
 */

public class MCCEntityDropItemEvent extends MCCEntityEvent  {

	private final MCCEntity drop;

	private boolean cancel;

	public MCCEntityDropItemEvent(MCCEntity entity, MCCEntity drop, boolean cancel){
		super(entity);
		this.drop = drop;
		this.cancel = cancel;
	}

	/**
	 *      Gets the Item created by the entity
	 *      *
	 *      @return Item created by the entity
	 */
	public MCCEntity getItemDrop(){
		return drop;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
