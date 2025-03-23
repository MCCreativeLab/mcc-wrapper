package de.verdox.mccreativelab.wrapper.types;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.entity.MCCEffect;

import java.util.List;

public interface MCCPotion extends MCCWrapped {
    /**
     * Returns the name of the potion
     *
     * @return the name
     */
    String getName();

    /**
     * Returns all effects of the potion
     *
     * @return the effects
     */
    List<MCCEffect> getEffects();

    /**
     * Returns if the potion has instant effects
     * @return true if it has instant effects
     */
    boolean hasInstantEffects();
}
