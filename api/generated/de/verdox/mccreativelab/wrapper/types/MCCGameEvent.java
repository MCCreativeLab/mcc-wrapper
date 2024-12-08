package de.verdox.mccreativelab.wrapper.types;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.types.MCCGameEvent;

public interface MCCGameEvent extends MCCWrapped  {

	public int getNotificationRadius();


	public static interface ListenerInfo  {
	
		public MCCReference<MCCGameEvent> getArg0();
	
		public MCCGameEvent.Context getArg2();
	
	}

	public static interface Context  {
	
		public MCCEntity getSourceEntity();
	
		public MCCBlockState getAffectedState();
	
	}
}
