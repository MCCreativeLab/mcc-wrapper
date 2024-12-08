package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

public class MCCItemMergeEvent extends MCCEntityEvent  {

	private boolean cancelled;

	private final MCCEntity target;

	public MCCItemMergeEvent(MCCEntity entity, boolean cancelled, MCCEntity target){
		super(entity);
		this.cancelled = cancelled;
		this.target = target;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Gets the Item entity the main Item is being merged into.
	 *      *
	 *      @return The Item being merged with
	 */
	public MCCEntity getTarget(){
		return target;
	}

}
