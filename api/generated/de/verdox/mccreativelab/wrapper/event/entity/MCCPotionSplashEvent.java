package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.block.MCCBlockFace;
import java.lang.Double;
import java.util.Map;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;

/**
 *  Called when a splash potion hits an area
 */

public class MCCPotionSplashEvent extends MCCProjectileHitEvent  {

	private boolean cancelled;

	private final Map<MCCEntity, Double> affectedEntities;

	public MCCPotionSplashEvent(MCCEntity entity, MCCEntity hitEntity, MCCBlock hitBlock, MCCBlockFace hitFace, boolean cancel, boolean cancelled, Map<MCCEntity, Double> affectedEntities){
		super(entity, hitEntity, hitBlock, hitFace, cancel);
		this.cancelled = cancelled;
		this.affectedEntities = affectedEntities;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	/**
	 *      Retrieves a list of all effected entities
	 *      *
	 *      @return A fresh copy of the affected entity list
	 */
	public Map<MCCEntity, Double> getAffectedEntities(){
		return affectedEntities;
	}

}
