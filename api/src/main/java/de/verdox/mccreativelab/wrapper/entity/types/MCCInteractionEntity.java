package de.verdox.mccreativelab.wrapper.entity.types;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.util.VanillaField;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an interaction entity
 */
public interface MCCInteractionEntity extends MCCEntity {
    /**
     * The width of the interaction bounding box
     */
    VanillaField<Float> width();

    /**
     * The height of the interaction bounding box
     */
    VanillaField<Float> height();

    /**
     * If true effects are played when interaction with the entity (arm swing, sounds, etc...)
     */
    VanillaField<Boolean> response();

    /**
     * Returns the last {@link MCCPlayer} that attacked the entity (left click)
     */
    @Nullable
    MCCLivingEntity getLastAttacker();


    /**
     * Returns the last {@link MCCPlayer} that interacted with the entity (right click)
     */
    @Nullable
    MCCLivingEntity getLastInteraction();
}
