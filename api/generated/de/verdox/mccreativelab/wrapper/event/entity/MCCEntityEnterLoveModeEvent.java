package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when an entity enters love mode.
 *  <br>
 *  This can be cancelled but the item will still be consumed that was used to
 *  make the entity enter into love mode.
 */

public class MCCEntityEnterLoveModeEvent extends MCCEntityEvent  {

	private boolean cancel;

	private final MCCEntity humanEntity;

	private int ticksInLove;

	public MCCEntityEnterLoveModeEvent(MCCEntity entity, boolean cancel, MCCEntity humanEntity, int ticksInLove){
		super(entity);
		this.cancel = cancel;
		this.humanEntity = humanEntity;
		this.ticksInLove = ticksInLove;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

	/**
	 *      Gets the Human Entity that caused the animal to enter love mode.
	 *      *
	 *      @return The Human entity that caused the animal to enter love mode, or
	 *      null if there wasn't one.
	 */
	public MCCEntity getHumanEntity(){
		return humanEntity;
	}

	/**
	 *      Gets the amount of ticks that the animal will fall in love for.
	 *      *
	 *      @return The amount of ticks that the animal will fall in love for
	 */
	public int getTicksInLove(){
		return ticksInLove;
	}

	/**
	 *      Sets the amount of ticks that the animal will fall in love for.
	 *      *
	 *      @param ticksInLove The amount of ticks that the animal will fall in love
	 *      for
	 */
	public void setTicksInLove(int ticksInLove){
		this.ticksInLove = ticksInLove;
	}

}
