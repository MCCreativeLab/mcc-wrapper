package de.verdox.mccreativelab.impl.vanilla.entity.types;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.entity.NMSAttributeInstance;
import de.verdox.mccreativelab.impl.vanilla.entity.NMSEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCDisplayEntity;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.util.VanillaField;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class NMSDisplayEntity<T extends Display> extends NMSEntity<T> implements MCCDisplayEntity {
    public static final MCCConverter<AttributeInstance, NMSAttributeInstance> CONVERTER = converter(NMSAttributeInstance.class, AttributeInstance.class, NMSAttributeInstance::new, MCCHandle::getHandle);

    public NMSDisplayEntity(T handle) {
        super(handle);
    }

    @Override
    public VanillaField<Integer> interpolationDurationInTicks() {
        return null;
    }

    @Override
    public VanillaField<Integer> teleportDurationInTicks() {
        return null;
    }

    @Override
    public VanillaField<Integer> interpolationDelayInTicks() {
        return null;
    }

    @Override
    public VanillaField<Vector3f> translation() {
        return null;
    }

    @Override
    public VanillaField<Quaternionf> leftRotation() {
        return null;
    }

    @Override
    public VanillaField<Quaternionf> rightRotation() {
        return null;
    }

    @Override
    public VanillaField<Vector3f> scale() {
        return null;
    }

    @Override
    public VanillaField<Billboard> billboard() {
        return null;
    }

    @Override
    public VanillaField<Integer> blockBrightness() {
        return null;
    }

    @Override
    public VanillaField<Integer> skyBrightness() {
        return null;
    }

    @Override
    public VanillaField<Float> viewRange() {
        return null;
    }

    @Override
    public VanillaField<Float> shadowStrength() {
        return null;
    }

    @Override
    public VanillaField<Float> shadowRadius() {
        return null;
    }

    @Override
    public VanillaField<Float> width() {
        return null;
    }

    @Override
    public VanillaField<Float> height() {
        return null;
    }

    @Override
    public VanillaField<Integer> glowColorOverride() {
        return null;
    }
}
