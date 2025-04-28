package de.verdox.mccreativelab.impl.paper.component.entity;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.component.AbstractComponent;
import de.verdox.mccreativelab.wrapper.component.entity.MCCPersistent;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import net.minecraft.world.entity.Entity;

public class PaperPersistent extends AbstractComponent<Entity, MCCEntity> implements MCCPersistent {
    public PaperPersistent(MCCEntity entity) {
        super(entity, new TypeToken<>() {});
    }

    @Override
    public boolean isPersistent() {
        return handle.persist;
    }

    @Override
    public void setPersistent() {
        handle.persist = true;
    }
}
