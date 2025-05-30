package de.verdox.mccreativelab.wrapper.platform.serialization;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCBlockPos;
import de.verdox.vserializer.SerializableField;
import de.verdox.vserializer.generic.*;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used to serialize or deserialize certain elements where the serialization depends on the platform native types
 */
@ApiStatus.Experimental
public interface MCCSerializers {
    Logger LOGGER = Logger.getLogger(MCCSerializers.class.getSimpleName());

    // Platform independent serializers

    /**
     * A serializer for {@link Key}
     */
    Serializer<Key> KEY = SerializerBuilder.createObjectToPrimitiveSerializer("key", Key.class, Serializer.Primitive.STRING, Key::asString, Key::key);

    /**
     * A serializer for {@link Component}
     */
    Serializer<Component> COMPONENT = new ComponentSerializer();

    Serializer<MCBlockPos> BLOCK_POS = SerializerBuilder.create(MCBlockPos.class)
            .constructor(
                    MCBlockPos::new,
                    serializer -> new SerializableField<>("x", Serializer.Primitive.INTEGER, MCBlockPos::x),
                    serializer -> new SerializableField<>("y", Serializer.Primitive.INTEGER, MCBlockPos::y),
                    serializer -> new SerializableField<>("z", Serializer.Primitive.INTEGER, MCBlockPos::z)
            )
            .build();

    /**
     * A serializer for {@link MCCItemStack}
     */
    Serializer<MCCItemStack> ITEM_STACK = new ItemStackSerializer();

    /**
     * A serializer for {@link MCCDataComponentMap}
     */
    Serializer<MCCDataComponentMap> DATA_COMPONENT_MAP = new DataComponentMapSerializer();

    Serializer<MCCLocation> LOCATION = SerializerBuilder.create("location", MCCLocation.class)
            .constructor(
                    new SerializableField<>("world", KEY, location -> location.world().key()),
                    new SerializableField<>("x", Serializer.Primitive.DOUBLE, MCCLocation::x),
                    new SerializableField<>("globalY", Serializer.Primitive.DOUBLE, MCCLocation::y),
                    new SerializableField<>("z", Serializer.Primitive.DOUBLE, MCCLocation::z),
                    new SerializableField<>("yaw", Serializer.Primitive.FLOAT, MCCLocation::yaw),
                    new SerializableField<>("pitch", Serializer.Primitive.FLOAT, MCCLocation::pitch),
                    (key, x, y, z, yaw, pitch) -> {
                        MCCWorld mccWorld = MCCPlatform.getInstance().getWorlds().stream().filter(world -> world.key().equals(key)).findAny().orElse(null);
                        if (mccWorld == null) {
                            LOGGER.log(Level.SEVERE, "No world found with key " + key);
                            return null;
                        }
                        return new MCCLocation(mccWorld, x, y, z, Optional.of(yaw).orElse(0f), Optional.of(pitch).orElse(0f));
                    }
            )

            .build();

    static <T extends MCCKeyedWrapper> Serializer<T> KEYED_WRAPPER(TypeToken<T> type) {
        return KEYED_WRAPPER("keyed", type);
    }

    static <T extends MCCKeyedWrapper> Serializer<T> KEYED_WRAPPER(String name, TypeToken<T> type) {
        return SerializerBuilder.create(name, type)
                .constructor(
                        new SerializableField<>("key", KEY, t -> Objects.requireNonNull(t.key(), "Key of type " + type.getRawType().getCanonicalName() + " cannot be null")),
                        new SerializableField<>("registry", KEY, t -> Objects.requireNonNull(t.getRegistryKey(), "Registry key of type " + type.getRawType().getCanonicalName() + " cannot be null")),
                        (valueKey, registryKey) -> (T) MCCPlatform.getInstance().getTypedKeyFactory().getKey(valueKey, registryKey).get()
                )
                .build();
    }

    // Platform dependent serializers

    /**
     * A serializer for {@link MCCBlockState}
     */
    Serializer<MCCBlockState> BLOCK_STATE();
}
