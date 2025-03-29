package de.verdox.mccreativelab.wrapper.item.components;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;

public interface MCCUseRemainder extends MCCItemComponent {
	public MCCItemStack getConvertInto();

	public MCCUseRemainder withConvertInto(MCCItemStack convertInto);
}