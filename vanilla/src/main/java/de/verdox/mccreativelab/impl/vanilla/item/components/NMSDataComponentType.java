package de.verdox.mccreativelab.impl.vanilla.item.components;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.vserializer.generic.Serializer;
import net.minecraft.core.component.DataComponentType;

import java.util.function.Supplier;

public class NMSDataComponentType<N, T, I extends T> extends MCCHandle<DataComponentType<N>> implements MCCDataComponentType<T> {
    private final TypeToken<N> nativeDataClass;
    private final TypeToken<T> apiDataClass;
    private final Supplier<I> defaultCreator;
    private final Serializer<T> apiSerializer;

    public NMSDataComponentType(DataComponentType<N> handle, TypeToken<N> nativeDataClass, TypeToken<T> apiDataClass, Serializer<T> apiSerializer, Supplier<I> defaultCreator) {
        super(handle);
        this.nativeDataClass = nativeDataClass;
        this.apiDataClass = apiDataClass;
        this.defaultCreator = defaultCreator;
        this.apiSerializer = apiSerializer;
    }

    public NMSDataComponentType(DataComponentType<N> handle, TypeToken<N> nativeDataClass, TypeToken<T> apiDataClass, Serializer<T> apiSerializer) {
        this(handle, nativeDataClass, apiDataClass, apiSerializer, null);
    }

    @Override
    public TypeToken<T> dataType() {
        return apiDataClass;
    }

    @Override
    public TypeToken<?> nativeType() {
        return nativeDataClass;
    }

    @Override
    public T createEmpty() {
        if (this.defaultCreator == null)
            throw new IllegalStateException("No default creator available for type " + apiDataClass);
        return defaultCreator.get();
    }

    @Override
    public Serializer<T> getValueSerializer() {
        return apiSerializer;
    }
}
