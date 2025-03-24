package de.verdox.mccreativelab.wrapper.item.components;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import java.util.List;

public interface MCCChargedProjectiles extends MCCItemComponent {
	public List<MCCItemStack> getArg0();

	public MCCChargedProjectiles withArg0(List<MCCItemStack> arg0);
}