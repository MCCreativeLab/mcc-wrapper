package de.verdox.mccreativelab.wrapper.item.components;

import java.util.List;

public interface MCCDeathProtection {
    public List<MCCConsumeEffect> getDeathEffects();

    public MCCDeathProtection withDeathEffects(List<MCCConsumeEffect> deathEffects);
}