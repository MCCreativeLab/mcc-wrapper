package de.verdox.mccreativelab.wrapper.item.components;
import java.lang.Object;
import java.lang.String;
import java.util.Map;
public interface MCCBlockItemStateProperties  {
	public Map<String, String> getProperties();

	public MCCBlockItemStateProperties withProperties(Map<String, String> properties);
}