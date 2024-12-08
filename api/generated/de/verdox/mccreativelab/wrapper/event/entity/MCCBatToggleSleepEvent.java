package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a bat attempts to sleep or wake up from its slumber.
 *  <p>
 *  If a Bat Toggle Sleep event is cancelled, the Bat will not toggle its sleep
 *  state.
 */

public class MCCBatToggleSleepEvent extends MCCEntityEvent  {

	private boolean cancel;

	private final boolean awake;

	public MCCBatToggleSleepEvent(MCCEntity entity, boolean cancel, boolean awake){
		super(entity);
		this.cancel = cancel;
		this.awake = awake;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

	/**
	 *      Get whether or not the bat is attempting to awaken.
	 *      *
	 *      @return true if trying to awaken, false otherwise
	 */
	public boolean isAwake(){
		return awake;
	}

}
