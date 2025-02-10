package de.verdox.mccreativelab.wrapper.event.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;

import java.util.List;

/**
 *  Called when a bell resonated after being rung and highlights nearby raiders.
 *  A bell will only resonate if raiders are in the vicinity of the bell.
 */

public class MCCBellResonateEvent extends MCCBlockEvent  {

	private final List<MCCEntity> resonatedEntities;

	public MCCBellResonateEvent(MCCBlock block, List<MCCEntity> resonatedEntities){
		super(block);
		this.resonatedEntities = resonatedEntities;
	}

	/**
	 *      Get a mutable list of all {@link LivingEntity LivingEntities} to be
	 *      highlighted by the bell's resonating. This list can be added to or
	 *      removed from to change which entities are highlighted, and may be empty
	 *      if no entities were resonated as a result of this event.
	 *      <p>
	 *      While the highlighted entities will change, the particles that display
	 *      over a resonated entity and their colors will not. This is handled by the
	 *      client and cannot be controlled by the server.
	 *      *
	 *      @return a list of resonated entities
	 */
	public List<MCCEntity> getResonatedEntities(){
		return resonatedEntities;
	}

}
