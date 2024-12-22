package de.verdox.mccreativelab.wrapper.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;

public interface MCCRegistryStorage {
    /**
     * Finds a registry in the native minecraft main registry of registries
     * @param registryKey the registry key
     * @return the found registry or null
     * @param <T> the generic type
     */
    <T> MCCRegistry<T> getMinecraftRegistry(Key registryKey);

    /**
     * Creates a new minecraft registry with a specified key
     * @return the new registry
     * @param <T> the generic type
     */
    <T> MCCRegistry<T> createMinecraftRegistry(Key key);

    /**
     * Custom registries are not frozen when minecraft tries to freeze them.
     * Instead, we freeze them before the worlds are loaded
     */
    void freezeCustomRegistries();
}