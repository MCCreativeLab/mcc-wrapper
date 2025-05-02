package de.verdox.mccreativelab.wrapper.entity.types;

import de.verdox.mccreativelab.wrapper.entity.*;
import de.verdox.mccreativelab.wrapper.component.entity.MCCEffectTarget;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.util.MCCEntityMultiProperty;
import de.verdox.mccreativelab.wrapper.util.MCCEntityProperty;
import de.verdox.mccreativelab.wrapper.types.MCCDamageType;

public interface MCCLivingEntity extends MCCEntity {

    /**
     * Represents the immunity properties of an entity
     *
     * @return the damage immunity property
     */
    MCCEntityMultiProperty<MCCDamageType, MCCLivingEntity> getDamageImmunityProperty();

    /**
     * Represents the targetability of an entity by other entity types
     *
     * @return the targetable property
     */
    MCCEntityMultiProperty<MCCEntityType<?>, MCCLivingEntity> getUntargetableProperty();

    /**
     * Represents the ability of a living entity to pickup items
     *
     * @return the pickup item property
     */
    MCCEntityProperty<Boolean, MCCLivingEntity> getPickupItemProperty();

    /**
     * Returns true if the entity is dead
     *
     * @return true if it is dead
     */
    boolean isDead();

    /**
     * Returns the attribute map of this living entity
     *
     * @return the attribute map
     */
    MCCAttributeMap getAttributes();

    /**
     * Returns the {@link MCCEffectTarget} component
     */
    default MCCEffectTarget asEffectTarget() {
        return MCCPlatform.getInstance().getGameComponentRegistry().create(this, MCCEffectTarget.class);
    }
}
