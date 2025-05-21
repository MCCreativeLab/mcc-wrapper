package de.verdox.mccreativelab.wrapper.platform.serialization;

import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.item.components.MCCTypedDataComponentType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.typed.MCCRegistries;
import de.verdox.vserializer.exception.SerializationException;
import de.verdox.vserializer.generic.*;
import net.kyori.adventure.key.Key;

import java.util.logging.Level;

public class DataComponentMapSerializer implements Serializer<MCCDataComponentMap> {
    @Override
    public SerializationElement serialize(SerializationContext context, MCCDataComponentMap object) throws SerializationException {
        SerializationContainer container = context.createContainer();

        for (MCCTypedDataComponentType<?> mccTypedDataComponentType : object.pairSet()) {
            if (mccTypedDataComponentType.value() == null) {
                continue;
            }
            Key key = MCCRegistries.DATA_COMPONENT_REGISTRY.get().getKey(mccTypedDataComponentType.key());
            container.set(MCCSerializers.KEY.serialize(context, key).getAsString(), mccTypedDataComponentType.serializeElement(context));
        }
        return container;
    }

    @Override
    public MCCDataComponentMap deserialize(SerializationElement serializedElement) throws SerializationException {
        MCCDataComponentMap map = MCCPlatform.getInstance().getElementFactory().createEmptyDataComponentMap();
        if (!serializedElement.isContainer()) {
            return map;
        }
        for (String dataComponentKey : serializedElement.getAsContainer().getChildKeys()) {
            if (!Key.parseable(dataComponentKey)) {
                MCCSerializers.LOGGER.log(Level.WARNING, "The provided data component type key " + dataComponentKey + " has not the valid key format");
                continue;
            }
            Key key = Key.key(dataComponentKey);

            MCCDataComponentType<?> dataComponentType = MCCRegistries.DATA_COMPONENT_REGISTRY.get().get(key);
            if (dataComponentType == null) {
                MCCSerializers.LOGGER.log(Level.WARNING, "Data component type not found with key " + key);
                continue;
            }
            deserializeValueAndSaveToMap(key, dataComponentType, serializedElement.getAsContainer().get(dataComponentKey), map);
        }
        return map;
    }

    private <T> void deserializeValueAndSaveToMap(Key key, MCCDataComponentType<T> type, SerializationElement serializationElement, MCCDataComponentMap map) throws SerializationException {
        if (type.getValueSerializer() == null) {
            MCCSerializers.LOGGER.log(Level.WARNING, "No value serializer was defined by the platform for data component type " + key);
            return;
        }
        T deserializedDataComponentValue = type.getValueSerializer().deserialize(serializationElement);
        map.set(type, deserializedDataComponentValue);
    }

    @Override
    public String id() {
        return "data_component_map";
    }

    @Override
    public Class<? extends MCCDataComponentMap> getType() {
        return MCCDataComponentMap.class;
    }
}
