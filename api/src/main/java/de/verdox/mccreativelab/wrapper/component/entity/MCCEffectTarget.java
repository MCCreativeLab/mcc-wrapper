package de.verdox.mccreativelab.wrapper.component.entity;

import de.verdox.mccreativelab.wrapper.component.GameComponent;
import de.verdox.mccreativelab.wrapper.entity.MCCEffect;
import de.verdox.mccreativelab.wrapper.entity.MCCEffectType;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCLivingEntity;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Represents an entity that can have effects
 */
public interface MCCEffectTarget extends GameComponent<MCCLivingEntity> {

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
    default boolean hasEffect(MCCReference<MCCEffectType> effectType) {
        return effectType.get().hasActiveEffect(getOwner());
    }

    /**
     * Tries to add an effect of a specified type
     * @param effect the effect
     * @param cause the cause
     */
    default boolean addEffect(MCCEffect effect, @Nullable MCCEntity cause) {
        return effect.apply(getOwner());
    }

    @Nullable
    default MCCEffect getEffect(MCCReference<MCCEffectType> effectType) {
        return effectType.get().getActiveEffect(getOwner());
    }

    /**
     * Tries to add an effect of a specified type
     * @param effectType the effect
     * @param effectBuilder the effect builder
     * @param cause the cause
     */
    default boolean addEffect(MCCReference<MCCEffectType> effectType, Consumer<MCCEffect.Builder> effectBuilder, @Nullable MCCEntity cause) {
        MCCEffectType type = effectType.get();
        MCCEffect.Builder builder = MCCEffect.create().withType(type);
        effectBuilder.accept(builder);
        return type.addEffect(getOwner(), builder.build());
    }

    /**
     * Tries to add an effect of a specified type
     * @param effectType the effect
     * @param effectBuilder the effect builder
     * @param cause the cause
     */
    default boolean addEffect(MCCReference<MCCEffectType> effectType, Consumer<MCCEffect.Builder> effectBuilder) {
        return addEffect(effectType, effectBuilder, null);
    }

    /**
     * Tries to add an effect of a specified type
     * @param effect the effect
     */
    default boolean addEffect(MCCEffect effect) {
        return addEffect(effect, null);
    }

    /**
     * Tries to remove an existing effect of a specified type
     * @param effectType the effect type
     */
    default boolean removeEffect(MCCReference<MCCEffectType> effectType) {
        return effectType.get().removeEffect(getOwner());
    }

    /**
     * Checks if the entity can be affected by a particular effect
     * @param effect the effect
     */
    default boolean canBeAffected(MCCEffect effect) {
        return effect.getType().canBeAffected(getOwner());
    }
}
