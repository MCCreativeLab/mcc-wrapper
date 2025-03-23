package de.verdox.mccreativelab.wrapper.item.components;
import java.lang.Integer;
import java.lang.Float;
import java.util.List;
import java.lang.Object;
import java.lang.String;
import java.lang.Boolean;
public interface MCCCustomModelData  {
	public List<Float> getFloats();

	public MCCCustomModelData withFloats(List<Float> floats);

	public List<Boolean> getFlags();

	public MCCCustomModelData withFlags(List<Boolean> flags);

	public List<String> getStrings();

	public MCCCustomModelData withStrings(List<String> strings);

	public List<Integer> getColors();

	public MCCCustomModelData withColors(List<Integer> colors);
}