package de.verdox.mccreativelab.impl.vanilla.entity.types;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.entity.NMSEntity;
import de.verdox.mccreativelab.wrapper.component.entity.MCCEffectTarget;
import de.verdox.mccreativelab.wrapper.entity.*;
import de.verdox.mccreativelab.wrapper.entity.types.MCCLivingEntity;
import de.verdox.mccreativelab.wrapper.exceptions.OperationNotPossibleOnNMS;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.util.MCCEntityMultiProperty;
import de.verdox.mccreativelab.wrapper.util.MCCEntityProperty;
import de.verdox.mccreativelab.wrapper.types.MCCDamageType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class NMSLivingEntity<T extends LivingEntity> extends NMSEntity<T> implements MCCLivingEntity {
    public static final MCCConverter<LivingEntity, NMSLivingEntity> CONVERTER = MCCHandle.converter(NMSLivingEntity.class, LivingEntity.class, NMSLivingEntity::new, nmsItemEntity -> (LivingEntity) nmsItemEntity.handle);

    public NMSLivingEntity(T handle) {
        super(handle);
    }

    @Override
    public MCCEntityMultiProperty<MCCDamageType, MCCLivingEntity> getDamageImmunityProperty() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public MCCEntityMultiProperty<MCCEntityType<?>, MCCLivingEntity> getUntargetableProperty() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public MCCEntityProperty<Boolean, MCCLivingEntity> getPickupItemProperty() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public boolean isDead() {
        return handle.isDeadOrDying();
    }

    @Override
    public MCCAttributeMap getAttributes() {
        return conversionService.wrap(handle.getAttributes(), new TypeToken<>() {});
    }
}
