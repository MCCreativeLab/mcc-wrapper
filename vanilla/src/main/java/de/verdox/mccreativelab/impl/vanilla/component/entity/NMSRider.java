package de.verdox.mccreativelab.impl.vanilla.component.entity;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.component.AbstractComponent;
import de.verdox.mccreativelab.wrapper.component.entity.MCCRideable;
import de.verdox.mccreativelab.wrapper.component.entity.MCCRider;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class NMSRider extends AbstractComponent<Entity, MCCEntity> implements MCCRider {
    public NMSRider(MCCEntity livingEntity) {
        super(livingEntity, new TypeToken<>() {});
    }

    @Override
    public boolean startRiding(MCCRideable vehicle, boolean force) {
        NMSRideable rideable = (NMSRideable) vehicle;
        return getHandle().startRiding(conversionService.unwrap(rideable.getHandle()), force);
    }

    @Override
    public boolean stopRiding() {
        getHandle().stopRiding();
        return true;
    }

    @Override
    public @Nullable MCCRideable getCurrentlyRiddenEntity() {
        MCCEntity mccEntity = conversionService.wrap(getHandle().getVehicle(), MCCEntity.class);
        if(mccEntity == null) {
            return null;
        }
        return mccEntity.asRideable();
    }

    @Override
    public MCCEntity getOwner() {
        return super.getOwner();
    }
}
