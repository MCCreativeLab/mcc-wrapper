package de.verdox.mccreativelab.wrapper.event.world;

import de.verdox.mccreativelab.wrapper.world.MCCWorld;

/**
 *  Called when a World is initializing.
 *  <p>
 *  To get every world it is recommended to add following to the plugin.yml.
 *  <pre>load: STARTUP</pre>
 */

public class MCCWorldInitEvent extends MCCWorldEvent  {

	public MCCWorldInitEvent(MCCWorld world){
		super(world);
	}

}
