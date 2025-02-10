package de.verdox.mccreativelab.wrapper.item.components;

import de.verdox.mccreativelab.wrapper.entity.MCCEffect;

import java.util.List;
import java.util.Optional;

public interface MCCPotionContents extends MCCItemComponent  {

	public Optional<Integer> getCustomColor();

	public MCCPotionContents withCustomColor(Optional<Integer> customColor);

	public List<MCCEffect> getCustomEffects();

	public MCCPotionContents withCustomEffects(List<MCCEffect> customEffects);

}
