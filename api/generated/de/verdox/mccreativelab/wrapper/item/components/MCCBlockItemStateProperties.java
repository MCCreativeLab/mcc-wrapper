package de.verdox.mccreativelab.wrapper.item.components;

import java.util.Map;

public interface MCCBlockItemStateProperties extends MCCItemComponent  {

	public Map<String, String> getProperties();

	public MCCBlockItemStateProperties withProperties(Map<String, String> properties);

}
