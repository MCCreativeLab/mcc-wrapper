package de.verdox.mccreativelab.wrapper.item.components;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
public interface MCCUseRemainder  {
	public MCCUseRemainder.OnExtraCreatedRemainder createOnExtraCreatedRemainder();

	public MCCItemStack getConvertInto();

	public MCCUseRemainder withConvertInto(MCCItemStack convertInto);

	public static interface OnExtraCreatedRemainder  {
		}}