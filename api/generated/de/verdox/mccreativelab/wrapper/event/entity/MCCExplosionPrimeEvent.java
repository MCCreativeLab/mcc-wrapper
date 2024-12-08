package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when an entity has made a decision to explode.
 */

public class MCCExplosionPrimeEvent extends MCCEntityEvent  {

	private boolean cancel;

	private float radius;

	private boolean fire;

	public MCCExplosionPrimeEvent(MCCEntity entity, boolean cancel, float radius, boolean fire){
		super(entity);
		this.cancel = cancel;
		this.radius = radius;
		this.fire = fire;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

	/**
	 *      Gets the radius of the explosion
	 *      *
	 *      @return returns the radius of the explosion
	 */
	public float getRadius(){
		return radius;
	}

	/**
	 *      Sets the radius of the explosion
	 *      *
	 *      @param radius the radius of the explosion
	 */
	public void setRadius(float radius){
		this.radius = radius;
	}

	/**
	 *      Gets whether this explosion will create fire or not
	 *      *
	 *      @return true if this explosion will create fire
	 */
	public boolean getFire(){
		return fire;
	}

	/**
	 *      Sets whether this explosion will create fire or not
	 *      *
	 *      @param fire true if you want this explosion to create fire
	 */
	public void setFire(boolean fire){
		this.fire = fire;
	}

}
