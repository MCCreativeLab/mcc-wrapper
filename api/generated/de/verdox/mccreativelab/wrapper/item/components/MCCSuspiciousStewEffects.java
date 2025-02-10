package de.verdox.mccreativelab.wrapper.item.components;

import de.verdox.mccreativelab.wrapper.entity.MCCEffectType;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;

import java.util.List;

public interface MCCSuspiciousStewEffects extends MCCItemComponent  {

	public MCCSuspiciousStewEffects.Entry createEntry();

	public List<de.verdox.mccreativelab.wrapper.item.components.MCCSuspiciousStewEffects.Entry> getEffects();

	public MCCSuspiciousStewEffects withEffects(List<de.verdox.mccreativelab.wrapper.item.components.MCCSuspiciousStewEffects.Entry> effects);


	public static interface Entry  {
	
		public MCCReference<MCCEffectType> getEffect();
	
		public MCCSuspiciousStewEffects.Entry withEffect(MCCReference<MCCEffectType> effect);
	
		public int getDuration();
	
		public MCCSuspiciousStewEffects.Entry withDuration(int duration);
	
	}
}
