package de.verdox.mccreativelab.classgenerator.codegen.type.impl;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CapturedRegistry {

    private transient final Map<Type, CapturedType<?, ?>> capturedTypesCache = new HashMap<>();
    private transient final Map<CapturedType<?, ?>, UUID> capturedTypesToUUID = new HashMap<>();
    private transient boolean isInitialized = false;
    private final Map<UUID, CapturedType<?, ?>> uuidToCapturedTypes = new HashMap<>();

    @Nullable
    public CapturedType<?, ?> resolve(Type type) {
        initCache();
        return capturedTypesCache.getOrDefault(type, null);
    }

    @Nullable
    public CapturedType<?, ?> resolve(UUID uuid) {
        return uuidToCapturedTypes.getOrDefault(uuid, null);
    }

    public UUID getUUID(CapturedType<?, ?> type) {
        initCache();
        if (!capturedTypesToUUID.containsKey(type)) {
            registerType(type);
        }
        return capturedTypesToUUID.get(type);
    }

    void linkToRealType(Type type, CapturedType<?, ?> capturedType) {
        initCache();
        capturedTypesCache.put(type, capturedType);
        // Register type
        registerType(capturedType);
    }

    private void registerType(CapturedType<?, ?> type) {
        UUID newUUID = UUID.randomUUID();
        capturedTypesToUUID.put(type, newUUID);
        uuidToCapturedTypes.put(newUUID, type);
    }

    private void initCache() {
        if (!isInitialized) {
            capturedTypesToUUID.forEach((capturedType, uuid) -> uuidToCapturedTypes.put(uuid, capturedType));
            isInitialized = true;
        }
    }
}
