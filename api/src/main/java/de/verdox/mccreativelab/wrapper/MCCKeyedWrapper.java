package de.verdox.mccreativelab.wrapper;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.vserializer.SerializableField;
import de.verdox.vserializer.generic.Serializer;
import de.verdox.vserializer.generic.SerializerBuilder;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;

/**
 * A wrapper that holds a native platform
 */
public interface MCCKeyedWrapper extends Keyed, MCCWrapped {
    Key getRegistryKey();

    static <T extends MCCKeyedWrapper> Serializer<T> createSerializer(String id, TypeToken<T> type) {
        return SerializerBuilder.create(id, type)
                .constructor(
                        new SerializableField<>("key", Serializer.Primitive.STRING, wrapped -> wrapped.key().asString()),
                        new SerializableField<>("registry", Serializer.Primitive.STRING, wrapped -> wrapped.key().asString()),
                        (keyString, registryKeyString) -> {
                            if (!Key.parseable(keyString)) {
                                throw new IllegalStateException("No valid minecraft key format for the value key: " + keyString);
                            }
                            if (!Key.parseable(registryKeyString)) {
                                throw new IllegalStateException("No valid minecraft key format for the registry key: " + registryKeyString);
                            }

                            Key key = Key.key(keyString);
                            Key registryKey = Key.key(registryKeyString);

                            MCCTypedKey<T> typedKey = MCCPlatform.getInstance().getTypedKeyFactory().getKey(key, registryKey, type);
                            MCCRegistry<T> registry = MCCPlatform.getInstance().getRegistryStorage().getMinecraftRegistry(registryKey);
                            if (registry == null) {
                                throw new IllegalStateException("No registry found with the key " + registryKey);
                            }
                            return registry.getOrThrow(typedKey);
                        }
                )
                .build();
    }
}
