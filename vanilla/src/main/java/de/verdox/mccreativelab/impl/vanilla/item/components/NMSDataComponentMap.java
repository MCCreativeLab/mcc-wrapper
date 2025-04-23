package de.verdox.mccreativelab.impl.vanilla.item.components;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentEditor;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.item.components.MCCTypedDataComponentType;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class NMSDataComponentMap extends MCCHandle<DataComponentMap> implements MCCDataComponentMap {
    public static final MCCConverter<DataComponentMap, NMSDataComponentMap> CONVERTER = converter(NMSDataComponentMap.class, DataComponentMap.class, NMSDataComponentMap::new, MCCHandle::getHandle);

    public NMSDataComponentMap(DataComponentMap handle) {
        super(handle);
    }

    @Override
    public <R, T extends MCCDataComponentType<R>> MCCDataComponentMap edit(T dataComponentType, Consumer<MCCDataComponentEditor<R, T>> editor) {
        NMSDataComponentEditor<R, T> nmsItemComponentEditor = new NMSDataComponentEditor<>(handle, dataComponentType);
        editor.accept(nmsItemComponentEditor);
        return this;
    }

    @Override
    public <R, T extends MCCDataComponentType<R>> R editAndGet(T dataComponentType, Function<MCCDataComponentEditor<R, T>, R> editor) {
        NMSDataComponentEditor<R, T> nmsItemComponentEditor = new NMSDataComponentEditor<>(handle, dataComponentType);
        return editor.apply(nmsItemComponentEditor);
    }

    @Override
    public <R, T extends MCCDataComponentType<R>> @Nullable R get(T dataComponentType) {
        NMSDataComponentEditor<R, T> nmsItemComponentEditor = new NMSDataComponentEditor<>(handle, dataComponentType);
        return nmsItemComponentEditor.get();
    }

    @Override
    public <R, T extends MCCDataComponentType<R>> void set(T dataComponentType, @Nullable R value) {
        NMSDataComponentEditor<R, T> nmsItemComponentEditor = new NMSDataComponentEditor<>(handle, dataComponentType);
        nmsItemComponentEditor.set(value);
    }

    @Override
    public boolean has(MCCDataComponentType<?> dataComponentType) {
        return handle.has(conversionService.unwrap(dataComponentType, new TypeToken<>() {}));
    }

    @Override
    public int size() {
        return handle.size();
    }

    @Override
    public boolean isEmpty() {
        return handle.isEmpty();
    }

    @Override
    public Set<MCCDataComponentType<?>> keySet() {
        return MCCPlatform.getInstance().getConversionService().wrap(handle.keySet());
    }

    @Override
    public Set<MCCTypedDataComponentType<?>> pairSet() {
        Set<MCCTypedDataComponentType<?>> set = new HashSet<>();
        for (DataComponentType<?> dataComponentType : handle.keySet()) {
            var pair = handle.getTyped(dataComponentType);
            if (pair == null) {
                continue;
            }
            set.add(new MCCTypedDataComponentType<>(conversionService.wrap(pair.type()), conversionService.wrap(pair.value())));
        }
        return set;
    }

    @Override
    public @NotNull Iterator<MCCDataComponentType<?>> iterator() {
        Iterator<TypedDataComponent<?>> iterator = handle.iterator();
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public MCCDataComponentType<?> next() {
                return conversionService.wrap(iterator.next().type());
            }
        };
    }
}
