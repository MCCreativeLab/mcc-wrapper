package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a Slime splits into smaller Slimes upon death
 */

public class MCCSlimeSplitEvent extends MCCEntityEvent  {

	private boolean cancel;

	private int count;

	public MCCSlimeSplitEvent(MCCEntity entity, boolean cancel, int count){
		super(entity);
		this.cancel = cancel;
		this.count = count;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

	/**
	 *      Gets the amount of smaller slimes to spawn
	 *      *
	 *      @return the amount of slimes to spawn
	 */
	public int getCount(){
		return count;
	}

	/**
	 *      Sets how many smaller slimes will spawn on the split
	 *      *
	 *      @param count the amount of slimes to spawn
	 */
	public void setCount(int count){
		this.count = count;
	}

}
