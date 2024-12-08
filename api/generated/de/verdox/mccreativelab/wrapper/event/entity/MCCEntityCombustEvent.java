package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when an entity combusts.
 *  <p>
 *  If an Entity Combust event is cancelled, the entity will not combust.
 */

public class MCCEntityCombustEvent extends MCCEntityEvent  {

	private float duration;

	private boolean cancel;

	public MCCEntityCombustEvent(MCCEntity entity, float duration, boolean cancel){
		super(entity);
		this.duration = duration;
		this.cancel = cancel;
	}

	/**
	 *      @return the amount of time (in seconds) the combustee should be alight
	 *          for
	 */
	public float getDuration(){
		return duration;
	}

	/**
	 *      The number of seconds the combustee should be alight for.
	 *      <p>
	 *      This value will only ever increase the combustion time, not decrease
	 *      existing combustion times.
	 *      *
	 *      @param duration the time in seconds to be alight for.
	 *      @see #setDuration(float)
	 *      @deprecated duration is now a float
	 */
	public void setDuration(float duration){
		this.duration = duration;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
