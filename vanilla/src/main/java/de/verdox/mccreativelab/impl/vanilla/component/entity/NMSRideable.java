package de.verdox.mccreativelab.impl.vanilla.component.entity;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.component.AbstractComponent;
import de.verdox.mccreativelab.wrapper.component.entity.MCCRideable;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import net.minecraft.world.entity.Entity;

import java.util.List;

public class NMSRideable extends AbstractComponent<Entity, MCCEntity> implements MCCRideable {
    public NMSRideable(MCCEntity entity) {
        super(entity, new TypeToken<>() {});
    }

    @Override
    public List<MCCEntity> getPassengers() {
        return conversionService.wrap(getHandle().getPassengers(), new TypeToken<>() {});
    }
}
