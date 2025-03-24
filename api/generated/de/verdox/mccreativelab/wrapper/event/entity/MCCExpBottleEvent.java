package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockFace;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a ThrownExpBottle hits and releases experience.
 */

public class MCCExpBottleEvent extends MCCProjectileHitEvent  {

	private int exp;

	private boolean showEffect;

	public MCCExpBottleEvent(MCCEntity entity, MCCEntity hitEntity, MCCBlock hitBlock, MCCBlockFace hitFace, boolean cancel, int exp, boolean showEffect){
		super(entity, hitEntity, hitBlock, hitFace, cancel);
		this.exp = exp;
		this.showEffect = showEffect;
	}

	/**
	 *      This method retrieves the amount of experience to be created.
	 *      <p>
	 *      The number indicates a total amount to be divided into orbs.
	 *      *
	 *      @return the total amount of experience to be created
	 */
	public int getExperience(){
		return exp;
	}

	/**
	 *      This method sets the amount of experience to be created.
	 *      <p>
	 *      The number indicates a total amount to be divided into orbs.
	 *      *
	 *      @param exp the total amount of experience to be created
	 */
	public void setExperience(int exp){
		this.exp = exp;
	}

	/**
	 *      This method indicates if the particle effect should be shown.
	 *      *
	 *      @return true if the effect will be shown, false otherwise
	 */
	public boolean getShowEffect(){
		return showEffect;
	}

	/**
	 *      This method sets if the particle effect will be shown.
	 *      <p>
	 *      This does not change the experience created.
	 *      *
	 *      @param showEffect true indicates the effect will be shown, false
	 *          indicates no effect will be shown
	 */
	public void setShowEffect(boolean showEffect){
		this.showEffect = showEffect;
	}

}
