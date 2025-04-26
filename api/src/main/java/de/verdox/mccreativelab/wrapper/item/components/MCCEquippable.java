package de.verdox.mccreativelab.wrapper.item.components;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlot;
import de.verdox.mccreativelab.wrapper.item.equipment.MCCEquipmentAsset;
import de.verdox.mccreativelab.wrapper.registry.MCCReferenceSet;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.util.BuilderField;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

import java.util.Optional;

/**
 * If present, this item can be equipped in the specified slot.
 */
public interface MCCEquippable extends MCCItemComponent {

    /**
     * The slot to put the item on
     */
    BuilderField<MCCEquipmentSlot, MCCEquippable> slot();

    /**
     * One sound event to play when the item is equipped.
     * Defaults to {@code item.armor.equip_generic}
     */
    BuilderField<Sound, MCCEquippable> equipSound();

    /**
     * The resource location of the equipment model to use when equipped. If not specified, falls back to rendering as the item itself when in the head slot (if not applicable, the item does not render).
     * The directory this refers to is {@code assets/<namespace>/equipment/<id>.json}.
     */
    BuilderField<Optional<MCCTypedKey<MCCEquipmentAsset>>, MCCEquippable> assetId();

    /**
     * The resource location of the overlay texture to use when equipped.
     * The directory this refers to is {@code assets/<namespace>/textures/<id>}
     */
    BuilderField<Optional<Key>, MCCEquippable> cameraOverlay();

    /**
     * Entity ID, Entity Tag, or list of Entity IDs to limit which entities can equip this item.
     * Defaults to all entities.
     */
    BuilderField<Optional<MCCReferenceSet<MCCEntityType<?>>>, MCCEquippable> allowedEntities();

    /**
     * Whether the item can be dispensed by using a dispenser.
     * Defaults to {@code true}
     */
    BuilderField<Boolean, MCCEquippable> dispensable();

    /**
     * Whether the item can be equipped into the relevant slot by right-clicking.
     * Defaults to {@code true}
     */
    BuilderField<Boolean, MCCEquippable> swappable();

    /**
     * Whether this item is damaged when the wearing entity is damaged.
     * Defaults to {@code true}
     */
    BuilderField<Boolean, MCCEquippable> damageOnHurt();
}
