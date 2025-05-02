package de.verdox.mccreativelab.impl.vanilla.item.components;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.util.field.DataComponentBuilderField;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlot;
import de.verdox.mccreativelab.wrapper.item.components.MCCEquippable;
import de.verdox.mccreativelab.wrapper.item.equipment.MCCEquipmentAsset;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.registry.MCCReferenceSet;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.util.BuilderField;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.Equippable;

import java.util.Optional;

public class NMSEquippable extends MCCHandle<Equippable> implements MCCEquippable {
    public static final MCCConverter<Equippable, NMSEquippable> CONVERTER = converter(NMSEquippable.class, Equippable.class, NMSEquippable::new, MCCHandle::getHandle);

    public NMSEquippable(Equippable handle) {
        super(handle);
    }

    @Override
    public BuilderField<MCCEquipmentSlot, MCCEquippable> slot() {
        return new DataComponentBuilderField<>(this, handle, Equippable::slot, (equippable, value) ->
                new Equippable(
                        value,
                        equippable.get(Equippable::equipSound, SoundEvents.ARMOR_EQUIP_GENERIC),
                        equippable.optional(Equippable::assetId),
                        equippable.optional(Equippable::cameraOverlay),
                        equippable.optional(Equippable::allowedEntities),
                        equippable.get(Equippable::dispensable, true),
                        equippable.get(Equippable::swappable, true),
                        equippable.get(Equippable::damageOnHurt, true)
                )
                , new TypeToken<>() {}, new TypeToken<>() {});
    }

    @Override
    public BuilderField<Sound, MCCEquippable> equipSound() {
        return new DataComponentBuilderField<>(this, handle, Equippable::equipSound, (equippable, value) ->
                new Equippable(
                        equippable.nullable(Equippable::slot),
                        value,
                        equippable.optional(Equippable::assetId),
                        equippable.optional(Equippable::cameraOverlay),
                        equippable.optional(Equippable::allowedEntities),
                        equippable.get(Equippable::dispensable, true),
                        equippable.get(Equippable::swappable, true),
                        equippable.get(Equippable::damageOnHurt, true)
                )
                , new TypeToken<>() {}, new TypeToken<>() {});
    }

    @Override
    public BuilderField<Optional<MCCTypedKey<MCCEquipmentAsset>>, MCCEquippable> assetId() {
        return new DataComponentBuilderField<>(this, handle, Equippable::assetId, (equippable, value) ->
                new Equippable(
                        equippable.nullable(Equippable::slot),
                        equippable.get(Equippable::equipSound, SoundEvents.ARMOR_EQUIP_GENERIC),
                        value,
                        equippable.optional(Equippable::cameraOverlay),
                        equippable.optional(Equippable::allowedEntities),
                        equippable.get(Equippable::dispensable, true),
                        equippable.get(Equippable::swappable, true),
                        equippable.get(Equippable::damageOnHurt, true)
                )
                , new TypeToken<>() {}, new TypeToken<>() {});
    }

    @Override
    public BuilderField<Optional<Key>, MCCEquippable> cameraOverlay() {
        return new DataComponentBuilderField<>(this, handle, Equippable::cameraOverlay, (equippable, value) ->
                new Equippable(
                        equippable.nullable(Equippable::slot),
                        equippable.get(Equippable::equipSound, SoundEvents.ARMOR_EQUIP_GENERIC),
                        equippable.optional(Equippable::assetId),
                        value,
                        equippable.optional(Equippable::allowedEntities),
                        equippable.get(Equippable::dispensable, true),
                        equippable.get(Equippable::swappable, true),
                        equippable.get(Equippable::damageOnHurt, true)
                )
                , new TypeToken<>() {}, new TypeToken<>() {});
    }

    @Override
    public BuilderField<Optional<MCCReferenceSet<MCCEntityType<?>>>, MCCEquippable> allowedEntities() {
        return new DataComponentBuilderField<>(this, handle, Equippable::allowedEntities, (equippable, value) ->
                new Equippable(
                        equippable.nullable(Equippable::slot),
                        equippable.get(Equippable::equipSound, SoundEvents.ARMOR_EQUIP_GENERIC),
                        equippable.optional(Equippable::assetId),
                        equippable.optional(Equippable::cameraOverlay),
                        value,
                        equippable.get(Equippable::dispensable, true),
                        equippable.get(Equippable::swappable, true),
                        equippable.get(Equippable::damageOnHurt, true)
                )
                , new TypeToken<>() {}, new TypeToken<>() {});
    }

    @Override
    public BuilderField<Boolean, MCCEquippable> dispensable() {
        return new DataComponentBuilderField<>(this, handle, Equippable::dispensable, (equippable, value) ->
                new Equippable(
                        equippable.nullable(Equippable::slot),
                        equippable.get(Equippable::equipSound, SoundEvents.ARMOR_EQUIP_GENERIC),
                        equippable.optional(Equippable::assetId),
                        equippable.optional(Equippable::cameraOverlay),
                        equippable.optional(Equippable::allowedEntities),
                        value,
                        equippable.get(Equippable::swappable, true),
                        equippable.get(Equippable::damageOnHurt, true)
                )
                , new TypeToken<>() {}, new TypeToken<>() {});
    }

    @Override
    public BuilderField<Boolean, MCCEquippable> swappable() {
        return new DataComponentBuilderField<>(this, handle, Equippable::swappable, (equippable, value) ->
                new Equippable(
                        equippable.nullable(Equippable::slot),
                        equippable.get(Equippable::equipSound, SoundEvents.ARMOR_EQUIP_GENERIC),
                        equippable.optional(Equippable::assetId),
                        equippable.optional(Equippable::cameraOverlay),
                        equippable.optional(Equippable::allowedEntities),
                        equippable.get(Equippable::dispensable, true),
                        value,
                        equippable.get(Equippable::damageOnHurt, true)
                )
                , new TypeToken<>() {}, new TypeToken<>() {});
    }

    @Override
    public BuilderField<Boolean, MCCEquippable> damageOnHurt() {
        return new DataComponentBuilderField<>(this, handle, Equippable::damageOnHurt, (equippable, value) ->
                new Equippable(
                        equippable.nullable(Equippable::slot),
                        equippable.get(Equippable::equipSound, SoundEvents.ARMOR_EQUIP_GENERIC),
                        equippable.optional(Equippable::assetId),
                        equippable.optional(Equippable::cameraOverlay),
                        equippable.optional(Equippable::allowedEntities),
                        equippable.get(Equippable::dispensable, true),
                        equippable.get(Equippable::swappable, true),
                        value
                )
                , new TypeToken<>() {}, new TypeToken<>() {});
    }
}
