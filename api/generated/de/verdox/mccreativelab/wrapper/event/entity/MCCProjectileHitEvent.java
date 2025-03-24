package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockFace;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a projectile hits an object
 */

public class MCCProjectileHitEvent extends MCCEntityEvent  {

	private final MCCEntity hitEntity;

	private final MCCBlock hitBlock;

	private final MCCBlockFace hitFace;

	private boolean cancel;

	public MCCProjectileHitEvent(MCCEntity entity, MCCEntity hitEntity, MCCBlock hitBlock, MCCBlockFace hitFace, boolean cancel){
		super(entity);
		this.hitEntity = hitEntity;
		this.hitBlock = hitBlock;
		this.hitFace = hitFace;
		this.cancel = cancel;
	}

	/**
	 *      Gets the entity that was hit, if it was an entity that was hit.
	 *      *
	 *      @return hit entity or else null
	 */
	public MCCEntity getHitEntity(){
		return hitEntity;
	}

	/**
	 *      Gets the block that was hit, if it was a block that was hit.
	 *      *
	 *      @return hit block or else null
	 */
	public MCCBlock getHitBlock(){
		return hitBlock;
	}

	/**
	 *      Gets the block face that was hit, if it was a block that was hit and the
	 *      face was provided in the event.
	 *      *
	 *      @return hit face or else null
	 */
	public MCCBlockFace getHitBlockFace(){
		return hitFace;
	}

	public boolean isCancelled(){
		return cancel;
	}

	/**
	 *      Whether to cancel the action that occurs when the projectile hits.
	 *      *
	 *      In the case of an entity, it will not collide (unless it's a firework,
	 *      then use {@link FireworkExplodeEvent}).
	 *      <br>
	 *      In the case of a block, some blocks (eg target block, bell) will not
	 *      perform the action associated.
	 *      <br>
	 *      This does NOT prevent block collisions, and explosions will still occur
	 *      unless their respective events are cancelled.
	 *      *
	 *      @param cancel true if you wish to cancel this event
	 */
	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

}
