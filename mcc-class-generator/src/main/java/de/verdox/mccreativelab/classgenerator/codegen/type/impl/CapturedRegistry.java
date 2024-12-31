package de.verdox.mccreativelab.classgenerator.codegen.type.impl;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CapturedRegistry {

    private transient final Map<Type, CapturedType<?, ?>> capturedTypesCache = new HashMap<>();

    @Nullable
    public CapturedType<?, ?> resolve(Type type) {
        return capturedTypesCache.getOrDefault(type, null);
    }

    void link(Type type, CapturedType<?, ?> capturedType) {
        capturedTypesCache.put(type, capturedType);
    }
}
