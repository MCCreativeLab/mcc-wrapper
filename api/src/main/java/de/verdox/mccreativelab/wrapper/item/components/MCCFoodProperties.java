package de.verdox.mccreativelab.wrapper.item.components;

public interface MCCFoodProperties extends MCCItemComponent  {
	public int getNutrition();

	public MCCFoodProperties withNutrition(int nutrition);

	public float getSaturation();

	public MCCFoodProperties withSaturation(float saturation);

	public boolean getCanAlwaysEat();

	public MCCFoodProperties withCanAlwaysEat(boolean canAlwaysEat);
}
