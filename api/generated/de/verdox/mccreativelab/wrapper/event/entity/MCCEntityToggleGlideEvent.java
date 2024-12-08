package de.verdox.mccreativelab.wrapper.event.entity;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Sent when an entity's gliding status is toggled with an Elytra.
 *  Examples of when this event would be called:
 *  <ul>
 *      <li>Player presses the jump key while in midair and using an Elytra</li>
 *      <li>Player lands on ground while they are gliding (with an Elytra)</li>
 *  </ul>
 *  This can be visually estimated by the animation in which a player turns horizontal.
 */

public class MCCEntityToggleGlideEvent extends MCCEntityEvent  {

	private boolean cancel;

	private final boolean isGliding;

	public MCCEntityToggleGlideEvent(MCCEntity entity, boolean cancel, boolean isGliding){
		super(entity);
		this.cancel = cancel;
		this.isGliding = isGliding;
	}

	public boolean isCancelled(){
		return cancel;
	}

	public void setCancelled(boolean cancel){
		this.cancel = cancel;
	}

	/**
	 *      Returns true if the entity is now gliding or
	 *      false if the entity stops gliding.
	 *      *
	 *      @return new gliding state
	 */
	public boolean isGliding(){
		return isGliding;
	}

}
