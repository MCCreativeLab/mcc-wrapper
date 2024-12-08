package de.verdox.mccreativelab.wrapper.event.entity;

import java.util.List;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

/**
 *  Called when a lingering potion applies its effects. Happens
 *  once every 5 ticks
 */

public class MCCAreaEffectCloudApplyEvent extends MCCEntityEvent  {

	private final List<MCCEntity> affectedEntities;

	private boolean cancelled;

	public MCCAreaEffectCloudApplyEvent(MCCEntity entity, List<MCCEntity> affectedEntities, boolean cancelled){
		super(entity);
		this.affectedEntities = affectedEntities;
		this.cancelled = cancelled;
	}

	/**
	 *      Retrieves a mutable list of the effected entities
	 *      <p>
	 *      It is important to note that not every entity in this list
	 *      is guaranteed to be effected.  The cloud may die during the
	 *      application of its effects due to the depletion of {@link AreaEffectCloud#getDurationOnUse()}
	 *      or {@link AreaEffectCloud#getRadiusOnUse()}
	 *      *
	 *      @return the affected entity list
	 */
	public List<MCCEntity> getAffectedEntities(){
		return affectedEntities;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

}
