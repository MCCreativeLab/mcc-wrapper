package de.verdox.mccreativelab.wrapper.item.components;
import java.util.Optional;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import java.lang.Object;
public interface MCCLodestoneTracker  {
	public Optional<MCCLocation> getTarget();

	public MCCLodestoneTracker withTarget(Optional<MCCLocation> target);

	public boolean getTracked();

	public MCCLodestoneTracker withTracked(boolean tracked);
}