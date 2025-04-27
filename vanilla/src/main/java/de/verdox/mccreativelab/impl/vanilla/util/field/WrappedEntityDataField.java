package de.verdox.mccreativelab.impl.vanilla.util.field;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.util.VanillaField;
import net.minecraft.world.entity.Entity;

public class WrappedEntityDataField<HANDLE extends Entity, API_TYPE, FIELD_TYPE> extends VanillaField<API_TYPE> {
    private final TypeToken<API_TYPE> apiTypeTypeToken;
    private final EntityDataField<HANDLE, FIELD_TYPE> dataField;

    public WrappedEntityDataField(TypeToken<API_TYPE> apiTypeTypeToken, EntityDataField<HANDLE, FIELD_TYPE> dataField) {
        this.apiTypeTypeToken = apiTypeTypeToken;
        this.dataField = dataField;
    }

    @Override
    public void set(API_TYPE newValue) {
        FIELD_TYPE vanilla = MCCPlatform.getInstance().getConversionService().unwrap(newValue);
        dataField.set(vanilla);
    }

    @Override
    public API_TYPE get() {
        return MCCPlatform.getInstance().getConversionService().wrap(dataField.get(), apiTypeTypeToken);
    }
}
