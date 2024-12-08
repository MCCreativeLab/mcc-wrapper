package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a firework explodes.
 */

public class MCCFireworkExplodeEvent extends MCCEntityEvent  {

	private boolean cancel;

	public MCCFireworkExplodeEvent(MCCEntity entity, boolean cancel){
		super(entity);
		this.cancel = cancel;
	}

	public boolean isCancelled(){
		return cancel;
	}

	/**
	 *      Set the cancelled state of this event. If the firework explosion is
	 *      cancelled, the firework will still be removed, but no particles will be
	 *      displayed.
	 *      *
	 *      @param cancel whether to cancel or not.
	 */
	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
