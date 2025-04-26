package de.verdox.mccreativelab.wrapper.item.components;


import de.verdox.vserializer.generic.SerializationContext;
import de.verdox.vserializer.generic.SerializationElement;
import org.jetbrains.annotations.Nullable;

/**
 * An immutable pair consisting of a data component type and an associated value
 */
public record MCCTypedDataComponentType<T>(MCCDataComponentType<T> key, @Nullable T value) {
    public SerializationElement serializeElement(SerializationContext context) {
        return key.getValueSerializer().serialize(context, value);
    }
}
