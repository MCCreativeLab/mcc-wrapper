package de.verdox.mccreativelab.wrapper.types;
import java.util.Set;

import de.verdox.mccreativelab.wrapper.block.MCCBlockState;

public interface MCCPoiType  {
	public Set<MCCBlockState> getMatchingStates();

	public int getMaxTickets();

	public int getValidRange();
}