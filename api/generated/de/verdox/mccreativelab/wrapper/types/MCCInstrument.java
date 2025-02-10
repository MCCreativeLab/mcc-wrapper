package de.verdox.mccreativelab.wrapper.types;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import net.kyori.adventure.sound.Sound;

public interface MCCInstrument extends MCCWrapped  {

	public MCCReference<Sound> getSoundEvent();

	public int getUseDuration();

	public float getRange();

}
