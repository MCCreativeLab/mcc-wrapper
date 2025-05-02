package de.verdox.mccreativelab.wrapper.platform.serialization;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.typed.MCCItems;
import de.verdox.vserializer.generic.SerializationContainer;
import de.verdox.vserializer.generic.SerializationContext;
import de.verdox.vserializer.generic.SerializationElement;
import de.verdox.vserializer.generic.Serializer;

import java.util.logging.Level;

public class ItemStackSerializer implements Serializer<MCCItemStack> {
    @Override
    public SerializationElement serialize(SerializationContext context, MCCItemStack object) {
        Serializer<MCCItemType> itemTypeSerializer = MCCSerializers.KEYED_WRAPPER(new TypeToken<>() {});
        int amount = object.getAmount();

        SerializationContainer container = context.createContainer();
        container.set("item", itemTypeSerializer.serialize(context, object.getType()));
        container.set("amount", amount);
        container.set("components", MCCSerializers.DATA_COMPONENT_MAP.serialize(context, object.components()));
        return container;
    }

    @Override
    public MCCItemStack deserialize(SerializationElement serializedElement) {
        if (!serializedElement.isContainer()) {
            return MCCItems.STONE.get().createItem();
        }
        Serializer<MCCItemType> itemTypeSerializer = MCCSerializers.KEYED_WRAPPER(new TypeToken<>() {});

        SerializationContainer container = serializedElement.getAsContainer();
        int amount = 1;
        MCCDataComponentMap map = null;
        MCCItemType mccItemType = itemTypeSerializer.deserialize(container.get("item"));
        if(mccItemType == null) {
            mccItemType = MCCItems.STONE.get();
        }

        if (container.contains("amount")) {
            amount = container.get("amount").getAsInt();
            if (amount <= 0) {
                MCCSerializers.LOGGER.log(Level.WARNING, "Amount of item stacks can only be positive");
                amount = 1;
            }
        }
        if (container.contains("components") || !container.get("components").isContainer()) {
            SerializationContainer components = container.get("components").getAsContainer();
            map = MCCSerializers.DATA_COMPONENT_MAP.deserialize(components);
        }

        MCCItemStack mccItemStack = mccItemType.createItem().withAmount(amount);
        if (map != null) {
            mccItemStack.components().putAll(map);
        }
        return mccItemStack;
    }

    @Override
    public String id() {
        return "item_stack";
    }

    @Override
    public Class<? extends MCCItemStack> getType() {
        return MCCItemStack.class;
    }
}
