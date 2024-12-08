package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a Pig Zombie is angered by another entity.
 *  <p>
 *  If the event is cancelled, the pig zombie will not be angered.
 */

public class MCCPigZombieAngerEvent extends MCCEntityEvent  {

	private boolean canceled;

	private final MCCEntity target;

	private int newAnger;

	public MCCPigZombieAngerEvent(MCCEntity entity, boolean canceled, MCCEntity target, int newAnger){
		super(entity);
		this.canceled = canceled;
		this.target = target;
		this.newAnger = newAnger;
	}

	public boolean isCancelled(){
		return canceled;
	}

	public void setCancelled(boolean canceled){
		this.canceled = canceled;
	}

	/**
	 *      Gets the entity (if any) which triggered this anger update.
	 *      *
	 *      @return triggering entity, or null
	 */
	public MCCEntity getTarget(){
		return target;
	}

	/**
	 *      Gets the new anger resulting from this event.
	 *      *
	 *      @return new anger
	 *      @see PigZombie#getAnger()
	 */
	public int getNewAnger(){
		return newAnger;
	}

	/**
	 *      Sets the new anger resulting from this event.
	 *      *
	 *      @param newAnger the new anger
	 *      @see PigZombie#setAnger(int)
	 */
	public void setNewAnger(int newAnger){
		this.newAnger = newAnger;
	}

}
