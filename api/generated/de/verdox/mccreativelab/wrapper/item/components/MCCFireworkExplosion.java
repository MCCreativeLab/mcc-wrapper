package de.verdox.mccreativelab.wrapper.item.components;

import java.util.List;

public interface MCCFireworkExplosion extends MCCItemComponent  {

	public MCCFireworkExplosion.Shape getShape();

	public MCCFireworkExplosion withShape(MCCFireworkExplosion.Shape shape);

	public List getColors();

	public MCCFireworkExplosion withColors(List colors);

	public List getFadeColors();

	public MCCFireworkExplosion withFadeColors(List fadeColors);

	public boolean getHasTrail();

	public MCCFireworkExplosion withHasTrail(boolean hasTrail);

	public boolean getHasTwinkle();

	public MCCFireworkExplosion withHasTwinkle(boolean hasTwinkle);


	public static enum Shape  {
	
		SMALL_BALL,
		LARGE_BALL,
		STAR,
		CREEPER,
		BURST,
	;
	}
}
