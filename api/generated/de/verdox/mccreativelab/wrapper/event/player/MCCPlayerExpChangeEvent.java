package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a players experience changes naturally
 */

public class MCCPlayerExpChangeEvent extends MCCPlayerEvent  {

	private final MCCEntity source;

	private int exp;

	public MCCPlayerExpChangeEvent(MCCEntity player, MCCEntity source, int exp){
		super(player);
		this.source = source;
		this.exp = exp;
	}

	/**
	 *      Get the source that provided the experience.
	 *      *
	 *      @return The source of the experience
	 */
	public MCCEntity getSource(){
		return source;
	}

	/**
	 *      Get the amount of experience the player will receive
	 *      *
	 *      @return The amount of experience
	 */
	public int getAmount(){
		return exp;
	}

	/**
	 *      Set the amount of experience the player will receive
	 *      *
	 *      @param amount The amount of experience to set
	 */
	public void setAmount(int exp){
		this.exp = exp;
	}

}
