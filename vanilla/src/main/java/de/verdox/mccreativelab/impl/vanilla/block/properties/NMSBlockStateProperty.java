package de.verdox.mccreativelab.impl.vanilla.block.properties;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStateProperty;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.stream.Stream;

public class NMSBlockStateProperty<F extends Comparable<F>, T extends Comparable<T>> extends MCCHandle<Property<F>> implements MCCBlockStateProperty<T> {
    public static final MCCConverter<Property, NMSBlockStateProperty> CONVERTER = converter(NMSBlockStateProperty.class, Property.class, NMSBlockStateProperty::new, nmsBlockStateProperty -> (Property) nmsBlockStateProperty.getHandle());

    public NMSBlockStateProperty(Property<F> handle) {
        super(handle);
    }

    @Override
    public String getName() {
        return handle.getName();
    }

    @Override
    public MCCBlockStateProperty.Value<T> value(T value) {
        return conversionService.wrap(handle.value(conversionService.unwrap(value, handle.getValueClass())), new TypeToken<>() {});
    }

    @Override
    public Stream<T> getPossibleValues() {
        return handle.getAllValues().map(fValue -> conversionService.wrap(fValue));
    }

    @Override
    public String getName(T value) {
        return handle.getName(conversionService.unwrap(value));
    }

    @Override
    public int getInternalIndex(T value) {
        return handle.getInternalIndex(conversionService.unwrap(value));
    }

    public static class Value<F extends Comparable<F>, T extends Comparable<T>> extends MCCHandle<Property.Value<F>> implements MCCBlockStateProperty.Value<T> {
        public static final MCCConverter<Property.Value, NMSBlockStateProperty.Value> CONVERTER = converter(NMSBlockStateProperty.Value.class, Property.Value.class, Value::new, nmsBlockStateProperty -> (Property.Value) nmsBlockStateProperty.getHandle());

        public Value(Property.Value<F> handle) {
            super(handle);
        }

        @Override
        public MCCBlockStateProperty<T> property() {
            return conversionService.wrap(handle.property(), new TypeToken<>() {});
        }

        @Override
        public T value() {
            return conversionService.wrap(handle.value());
        }
    }
}
