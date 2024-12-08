package de.verdox.mccreativelab.wrapper.event.world;

import de.verdox.mccreativelab.wrapper.world.MCCWorld;

/**
 *  Called when a World is unloaded
 */

public class MCCWorldUnloadEvent extends MCCWorldEvent  {

	private boolean isCancelled;

	public MCCWorldUnloadEvent(MCCWorld world, boolean isCancelled){
		super(world);
		this.isCancelled = isCancelled;
	}

	public boolean isCancelled(){
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled){
		this.isCancelled = isCancelled;
	}

}
