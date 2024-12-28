package de.verdox.mccreativelab.impl.vanilla.item.components;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentEditor;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public record NMSMCCDataComponentEditor<R, T extends MCCDataComponentType<R>>(DataComponentMap dataComponents,
                                                                              T type) implements MCCDataComponentEditor<R, T> {
    @Override
    public @Nullable R get() {
        // The native data that is stored inside a minecraft DataComponentType
        DataComponentType<?> nativeDataComponentType = MCCPlatform.getInstance().getConversionService().unwrap(type, DataComponentType.class);
        if (!dataComponents.has(nativeDataComponentType))
            return null;

        Object nativeComponentData = dataComponents.get(nativeDataComponentType);

        return MCCPlatform.getInstance().getConversionService().wrap(nativeComponentData, type().dataType());
    }

    @Override
    @MCCReflective
    public void set(@Nullable R data) {
        DataComponentType nativeDataComponentType = MCCPlatform.getInstance().getConversionService().unwrap(type, new TypeToken<>() {});
        if (dataComponents instanceof PatchedDataComponentMap patchedDataComponentMap) {
            //TODO Needs tests
            if (data == null) {
                patchedDataComponentMap.set(nativeDataComponentType, null);
                return;
            }

            patchedDataComponentMap.set(nativeDataComponentType, MCCPlatform.getInstance().getConversionService().unwrap(data, type.nativeType()));
        } else if (dataComponents.getClass().isRecord() && dataComponents.getClass().getRecordComponents().length >= 1) {
            //TODO Needs tests
            try {
                Reference2ObjectMap<DataComponentType<?>, Object> map = (Reference2ObjectMap<DataComponentType<?>, Object>) dataComponents.getClass().getRecordComponents()[0].getAccessor().invoke(dataComponents);
                if (data == null) {
                    map.remove(nativeDataComponentType);
                    return;
                }
                map.put(nativeDataComponentType, MCCPlatform.getInstance().getConversionService().unwrap(data, type.nativeType()));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        else throw new IllegalStateException("Trying to manipulate an immutable data component map");


    }

    @Override
    public @NotNull R create() {
        return type.createEmpty();
    }
}
