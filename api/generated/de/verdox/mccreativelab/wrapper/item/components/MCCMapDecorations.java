package de.verdox.mccreativelab.wrapper.item.components;

import java.util.Map;
import java.lang.String;
import de.verdox.mccreativelab.wrapper.item.components.MCCMapDecorations;

public interface MCCMapDecorations extends MCCItemComponent  {

	public MCCMapDecorations.Entry createEntry();

	public Map<String, de.verdox.mccreativelab.wrapper.item.components.MCCMapDecorations.Entry> getDecorations();

	public MCCMapDecorations withDecorations(Map<String, de.verdox.mccreativelab.wrapper.item.components.MCCMapDecorations.Entry> decorations);


	public static interface Entry  {
	
		public double getX();
	
		public MCCMapDecorations.Entry withX(double x);
	
		public double getZ();
	
		public MCCMapDecorations.Entry withZ(double z);
	
		public float getRotation();
	
		public MCCMapDecorations.Entry withRotation(float rotation);
	
	}
}
