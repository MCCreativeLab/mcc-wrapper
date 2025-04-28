package de.verdox.mccreativelab.impl.vanilla.util.field;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.util.BuilderField;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DataComponentBuilderField<API_FIELD_TYPE, NMS_FIELD_TYPE, OWNER, HANDLE> extends BuilderField<API_FIELD_TYPE, OWNER> {
    private final Function<HANDLE, NMS_FIELD_TYPE> getter;
    private final TypeToken<NMS_FIELD_TYPE> nmsFieldType;
    private final HANDLE handle;
    private final BiFunction<SafeAccess<HANDLE>, NMS_FIELD_TYPE, HANDLE> constructor;
    private final TypeToken<API_FIELD_TYPE> apiFieldType;

    public DataComponentBuilderField(OWNER owner, HANDLE handle, Function<HANDLE, NMS_FIELD_TYPE> getter, BiFunction<SafeAccess<HANDLE>, NMS_FIELD_TYPE, HANDLE> constructor, TypeToken<NMS_FIELD_TYPE> nmsFieldType, TypeToken<API_FIELD_TYPE> apiFieldType) {
        super(owner);
        this.getter = getter;
        this.nmsFieldType = nmsFieldType;
        this.handle = handle;
        this.constructor = constructor;
        this.apiFieldType = apiFieldType;
    }

    @Override
    public OWNER with(API_FIELD_TYPE value) {
        ConversionService conversionService = MCCPlatform.getInstance().getConversionService();
        return conversionService.wrap(constructor.apply(new SafeAccess<>(handle), conversionService.unwrap(value, nmsFieldType)));
    }

    @Override
    public API_FIELD_TYPE get() {
        ConversionService conversionService = MCCPlatform.getInstance().getConversionService();
        return conversionService.wrap(getter.apply(handle), apiFieldType);
    }

    public static class SafeAccess<HANDLE> {
        private final HANDLE handle;

        public SafeAccess(HANDLE handle) {
            this.handle = handle;
        }

        public <NMS_FIELD_TYPE> NMS_FIELD_TYPE get(Function<HANDLE, NMS_FIELD_TYPE> getter, NMS_FIELD_TYPE standard) {
            if (handle == null) {
                return standard;
            }
            return getter.apply(handle);
        }

        public <NMS_FIELD_TYPE> NMS_FIELD_TYPE nullable(Function<HANDLE, NMS_FIELD_TYPE> getter) {
            return get(getter, null);
        }

        public <NMS_FIELD_TYPE> Optional<NMS_FIELD_TYPE> optional(Function<HANDLE, Optional<NMS_FIELD_TYPE>> getter) {
            return get(getter, Optional.empty());
        }
    }
}
