package de.verdox.mccreativelab.impl.vanilla.util.field;

import de.verdox.mccreativelab.wrapper.util.VanillaField;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class EntityDataField<HANDLE extends Entity, FIELD_TYPE> extends VanillaField<FIELD_TYPE> {
    private final Supplier<HANDLE> getHandle;
    private final EntityDataAccessor<FIELD_TYPE> dataAccessor;

    public EntityDataField(Supplier<HANDLE> getHandle, EntityDataAccessor<FIELD_TYPE> dataAccessor) {
        this.getHandle = getHandle;
        this.dataAccessor = dataAccessor;
    }

    @Override
    public void set(FIELD_TYPE newValue) {
        getHandle.get().getEntityData().set(dataAccessor, newValue);
    }

    @Override
    public FIELD_TYPE get() {
        return getHandle.get().getEntityData().get(dataAccessor);
    }
}
