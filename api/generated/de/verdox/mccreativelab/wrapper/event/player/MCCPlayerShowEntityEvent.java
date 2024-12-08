package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a hidden entity is shown to a player.
 *  <br>
 *  This event is only called when the entity's visibility status is actually
 *  changed.
 *  <br>
 *  This event is called regardless of whether the entity was within tracking
 *  range.
 *  *
 *  @see Player#showEntity(org.bukkit.plugin.Plugin, org.bukkit.entity.Entity)
 */

public class MCCPlayerShowEntityEvent extends MCCPlayerEvent  {

	private final MCCEntity entity;

	public MCCPlayerShowEntityEvent(MCCEntity player, MCCEntity entity){
		super(player);
		this.entity = entity;
	}

	/**
	 *      Gets the entity which has been shown to the player.
	 *      *
	 *      @return the shown entity
	 */
	public MCCEntity getEntity(){
		return entity;
	}

}
