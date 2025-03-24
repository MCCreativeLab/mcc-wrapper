package de.verdox.mccreativelab.wrapper.item.components;

import java.util.List;

public interface MCCDeathProtection extends MCCItemComponent {
    public List<MCCConsumeEffect> getDeathEffects();

    public MCCDeathProtection withDeathEffects(List<MCCConsumeEffect> deathEffects);
}