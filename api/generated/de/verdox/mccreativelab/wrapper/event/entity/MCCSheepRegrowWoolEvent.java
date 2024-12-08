package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a sheep regrows its wool
 */

public class MCCSheepRegrowWoolEvent extends MCCEntityEvent  {

	private boolean cancel;

	public MCCSheepRegrowWoolEvent(MCCEntity entity, boolean cancel){
		super(entity);
		this.cancel = cancel;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
