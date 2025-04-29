package de.verdox.mccreativelab.wrapper.entity;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.annotations.MCCBuiltIn;
import de.verdox.mccreativelab.wrapper.annotations.MCCInstantiationSource;
import de.verdox.mccreativelab.wrapper.component.entity.MCCEffectTarget;
import de.verdox.mccreativelab.wrapper.entity.types.MCCLivingEntity;
import de.verdox.mccreativelab.wrapper.platform.serialization.MCCSerializers;
import de.verdox.mccreativelab.wrapper.typed.MCCRegistries;
import de.verdox.vserializer.generic.Serializer;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;

@MCCInstantiationSource(sourceClasses = {MCCLivingEntity.class})
@MCCBuiltIn(syncState = MCCBuiltIn.SyncState.SYNCED, clientEntersErrorStateOnDesync = true)
public interface MCCEffectType extends MCCKeyedWrapper {
    Serializer<MCCEffectType> SERIALIZER = MCCSerializers.KEYED_WRAPPER("effect", new TypeToken<>() {});

    MCCEffect create(int duration, int amplifier, boolean ambient, boolean particles, boolean icon, @Nullable MCCEffect hiddenEffect);

    /**
     * Tries to add the effect to the entity
     * @param mccEntity the entity
     * @param mccEffect the effect
     */
    boolean addEffect(MCCLivingEntity mccEntity, MCCEffect mccEffect);

    /**
     * Tries to remove this effect type from the entity
     * @param mccEntity the entity
     */
    boolean removeEffect(MCCLivingEntity mccEntity);

    /**
     * Checks if the entity can be affected by a particular effect
     * @param mccEntity the entity
     */
    boolean canBeAffected(MCCLivingEntity mccEntity);

    /**
     * Gets the active effect for a provided effect type or null if no active effect was found
     *
     * @return the active effect or null
     */
    @Nullable
    MCCEffect getActiveEffect(MCCLivingEntity livingEntity);

    /**
     * Checks if this entity has an active effect of the provided effect type
     *
     * @return true if there is an active effect
     */
    default boolean hasActiveEffect(MCCLivingEntity livingEntity){
        return getActiveEffect(livingEntity) != null;
    }

    @Override
    default Key getRegistryKey(){
        return MCCRegistries.EFFECT_TYPE_REGISTRY.key();
    }
}
