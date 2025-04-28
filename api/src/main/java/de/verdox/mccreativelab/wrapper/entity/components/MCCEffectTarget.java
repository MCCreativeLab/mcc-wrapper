package de.verdox.mccreativelab.wrapper.entity.components;

import de.verdox.mccreativelab.wrapper.entity.MCCEffect;
import de.verdox.mccreativelab.wrapper.entity.MCCEffectType;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Represents an entity that can have effects
 */
public interface MCCEffectTarget {

    /**
     * Returns all active effects
     */
    Collection<MCCEffect> getActiveEffects();

    /**
     * Removes all effects
     */
    boolean removeAllEffects();

    /**
     * Returns whether an effect of the specified type is active on the entity
     * @param effectType the type
     */
    boolean hasEffect(MCCReference<MCCEffectType> effectType);

    /**
     * Tries to add an effect of a specified type
     * @param effect the effect
     * @param cause the cause
     */
    boolean addEffect(MCCEffect effect, @Nullable MCCEntity cause);

    /**
     * Tries to add an effect of a specified type
     * @param effect the effect
     */
    default boolean addEffect(MCCEffect effect) {
        return addEffect(effect, null);
    }

    /**
     * Checks if the entity can be affected by a particular effect
     * @param effect the effect
     */
    boolean canBeAffected(MCCEffect effect);

    /**
     * Tries to remove an existing effect of a specified type
     * @param effectType the effect type
     */
    boolean removeEffect(MCCReference<MCCEffectType> effectType);
}
