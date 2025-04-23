package de.verdox.mccreativelab.wrapper.platform.serialization;

import de.verdox.vserializer.generic.SerializationContext;
import de.verdox.vserializer.generic.SerializationElement;
import de.verdox.vserializer.generic.Serializer;
import de.verdox.vserializer.json.JsonSerializerContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public class ComponentSerializer implements Serializer<Component> {
    final JsonSerializerContext jsonSerializerContext = new JsonSerializerContext();

    @Override
    public SerializationElement serialize(SerializationContext context, Component object) {
        GsonComponentSerializer gsonComponentSerializer = GsonComponentSerializer.builder().build();
        SerializationElement element = jsonSerializerContext.toElement(gsonComponentSerializer.serializeToTree(object));
        return context.convert(element, false);
    }

    @Override
    public Component deserialize(SerializationElement context) {
        var element = jsonSerializerContext.convert(context, false);
        GsonComponentSerializer gsonComponentSerializer = GsonComponentSerializer.builder().build();
        return gsonComponentSerializer.deserialize(jsonSerializerContext.toJsonString(element));
    }

    @Override
    public String id() {
        return "component";
    }

    @Override
    public Class<? extends Component> getType() {
        return Component.class;
    }
}
