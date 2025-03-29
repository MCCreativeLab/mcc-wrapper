package de.verdox.mccreativelab.wrapper.item.components;

import java.util.List;

/**
 * If present, this item acts like a totem of undying, by reviving the holder of this item.
 */
public interface MCCDeathProtection extends MCCItemComponent {
    public List<MCCConsumeEffect> getDeathEffects();

    public MCCDeathProtection withDeathEffects(List<MCCConsumeEffect> deathEffects);
}