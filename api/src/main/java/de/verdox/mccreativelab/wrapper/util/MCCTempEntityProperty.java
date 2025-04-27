package de.verdox.mccreativelab.wrapper.util;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import org.jetbrains.annotations.Nullable;

public class MCCTempEntityProperty<DATA_TYPE, ENTITY extends MCCEntity> implements MCCEntityProperty<DATA_TYPE, ENTITY> {

    private final String key;
    private final Class<DATA_TYPE> type;
    private final ENTITY handle;
    private final DATA_TYPE defaultValue;

    public MCCTempEntityProperty(String key, Class<DATA_TYPE> type, ENTITY handle, DATA_TYPE defaultValue) {
        this.key = key;
        this.type = type;
        this.handle = handle;
        this.defaultValue = defaultValue;
    }

    @Override
    public @Nullable DATA_TYPE get() {
        if (handle.getTempData().containsData(key)) {
            return defaultValue;
        }
        return handle.getTempData().getData(type, key);
    }

    @Override
    public void set(@Nullable DATA_TYPE newValue) {
        handle.getTempData().storeData(key, newValue);
    }

    @Override
    public void sync() {
        handle.getTempData().removeData(key);
    }
}
