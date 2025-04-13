package de.verdox.mccreativelab.wrapper.entity.types;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a projectile entity
 */
public interface MCCProjectileEntity extends MCCEntity {
    /**
     * Sets the new owner of the projectile
     * @param owner the owner
     */
    void setOwner(@Nullable MCCEntity owner);

    /**
     * Returns the owner of the projectile
     * @return the owner
     */
    @Nullable
    MCCEntity getOwner();

    /**
     * Returns the source of the projectile
     * @return the source
     */
    @Nullable
    MCCEntity getEffectSource();
}
