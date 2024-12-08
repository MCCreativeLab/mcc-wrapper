package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;

/**
 *  Called when a player throws an egg and it might hatch
 */

public class MCCPlayerEggThrowEvent extends MCCPlayerEvent  {

	private final MCCEntity egg;

	private boolean hatching;

	private MCCEntityType hatchType;

	private byte numHatches;

	public MCCPlayerEggThrowEvent(MCCEntity player, MCCEntity egg, boolean hatching, MCCEntityType hatchType, byte numHatches){
		super(player);
		this.egg = egg;
		this.hatching = hatching;
		this.hatchType = hatchType;
		this.numHatches = numHatches;
	}

	/**
	 *      Gets the egg involved in this event.
	 *      *
	 *      @return the egg involved in this event
	 */
	public MCCEntity getEgg(){
		return egg;
	}

	/**
	 *      Gets whether the egg is hatching or not. Will be what the server
	 *      would've done without interaction.
	 *      *
	 *      @return boolean Whether the egg is going to hatch or not
	 */
	public boolean isHatching(){
		return hatching;
	}

	/**
	 *      Sets whether the egg will hatch or not.
	 *      *
	 *      @param hatching true if you want the egg to hatch, false if you want it
	 *          not to
	 */
	public void setHatching(boolean hatching){
		this.hatching = hatching;
	}

	/**
	 *      Get the type of the mob being hatched (EntityType.CHICKEN by default)
	 *      *
	 *      @return The type of the mob being hatched by the egg
	 */
	public MCCEntityType getHatchingType(){
		return hatchType;
	}

	/**
	 *      Change the type of mob being hatched by the egg
	 *      *
	 *      @param hatchType The type of the mob being hatched by the egg
	 */
	public void setHatchingType(MCCEntityType hatchType){
		this.hatchType = hatchType;
	}

	/**
	 *      Get the number of mob hatches from the egg. By default the number will
	 *      be the number the server would've done
	 *      <ul>
	 *      <li>7/8 chance of being 0
	 *      <li>31/256 ~= 1/8 chance to be 1
	 *      <li>1/256 chance to be 4
	 *      </ul>
	 *      *
	 *      @return The number of mobs going to be hatched by the egg
	 */
	public byte getNumHatches(){
		return numHatches;
	}

	/**
	 *      Change the number of mobs coming out of the hatched egg
	 *      <p>
	 *      The boolean hatching will override this number. Ie. If hatching =
	 *      false, this number will not matter
	 *      *
	 *      @param numHatches The number of mobs coming out of the egg
	 */
	public void setNumHatches(byte numHatches){
		this.numHatches = numHatches;
	}

}
