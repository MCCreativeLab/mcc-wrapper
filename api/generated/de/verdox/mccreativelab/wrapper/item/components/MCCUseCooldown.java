package de.verdox.mccreativelab.wrapper.item.components;
import java.util.Optional;

import net.kyori.adventure.key.Key;

public interface MCCUseCooldown extends MCCItemComponent {
	public float getSeconds();

	public MCCUseCooldown withSeconds(float seconds);

	public Optional<Key> getCooldownGroup();

	public MCCUseCooldown withCooldownGroup(Optional<Key> cooldownGroup);
}