package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a visible entity is hidden from a player.
 *  <br>
 *  This event is only called when the entity's visibility status is actually
 *  changed.
 *  <br>
 *  This event is called regardless of if the entity was within tracking range.
 *  *
 *  @see Player#hideEntity(org.bukkit.plugin.Plugin, org.bukkit.entity.Entity)
 */

public class MCCPlayerHideEntityEvent extends MCCPlayerEvent  {

	private final MCCEntity entity;

	public MCCPlayerHideEntityEvent(MCCEntity player, MCCEntity entity){
		super(player);
		this.entity = entity;
	}

	/**
	 *      Gets the entity which has been hidden from the player.
	 *      *
	 *      @return the hidden entity
	 */
	public MCCEntity getEntity(){
		return entity;
	}

}
