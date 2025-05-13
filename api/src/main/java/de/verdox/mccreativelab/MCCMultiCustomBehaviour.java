package de.verdox.mccreativelab;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MCCMultiCustomBehaviour<K, T> {
    private final String key;
    private final Class<? extends T> type;
    private final T defaultImplementation;
    public final Map<K, MCCProxyInterface<T>> storedBehaviour = new HashMap<>();

    public MCCMultiCustomBehaviour(@NotNull Class<T> type, @NotNull T defaultImplementation, @NotNull String key) {
        this.key = key;
        this.type = type;
        this.defaultImplementation = defaultImplementation;
    }

    @NotNull
    public String getKey() {
        return key;
    }

    /**
     * Used to set a new behaviour.
     *
     * @param behaviour The new implementation
     */
    public void setBehaviour(@NotNull K key, @NotNull T behaviour) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(behaviour);
        storedBehaviour.computeIfAbsent(key, k -> new MCCProxyInterface<>(type, defaultImplementation)).addImplementation(behaviour);
    }

    @Nullable
    @ApiStatus.Internal
    public T getBehaviour(@NotNull K key) {
        Objects.requireNonNull(key);
        return storedBehaviour.computeIfAbsent(key, k -> new MCCProxyInterface<>(type, defaultImplementation)).getImplementation();
    }

    /**
     * Checks if a custom implementation exists
     * @return true if a custom implementation exists
     */
    public boolean isImplemented(@NotNull K key){
        Objects.requireNonNull(key);
        return storedBehaviour.containsKey(key);
    }
}