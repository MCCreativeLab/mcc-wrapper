package de.verdox.mccreativelab.wrapper.types;
import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;

public interface MCCInstrument extends MCCWrapped  {

	public MCCReference<Sound> getSoundEvent();

	public float getUseDuration();

	public float getRange();

	public Component getDescription();
}