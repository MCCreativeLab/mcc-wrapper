package de.verdox.mccreativelab.wrapper.item.components;

public interface MCCFoodProperties  {
	public MCCFoodProperties.Builder createBuilder();

	public int getNutrition();

	public MCCFoodProperties withNutrition(int nutrition);

	public float getSaturation();

	public MCCFoodProperties withSaturation(float saturation);

	public boolean getCanAlwaysEat();

	public MCCFoodProperties withCanAlwaysEat(boolean canAlwaysEat);

	public static interface Builder  {
		}}