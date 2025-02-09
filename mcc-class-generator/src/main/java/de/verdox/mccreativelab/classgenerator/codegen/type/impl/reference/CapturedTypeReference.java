package de.verdox.mccreativelab.classgenerator.codegen.type.impl.reference;

import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class CapturedTypeReference<T extends CapturedType<?, ?>> {

    private final UUID typeUUID;

    public static <T extends CapturedType<?, ?>> CapturedTypeReference<T> create(T type) {
        return new CapturedTypeReference<>(CapturedType.CAPTURED_REGISTRY.getUUID(type));
    }

    public static <T extends CapturedType<?, ?>> CapturedTypeReference<T> create(UUID referenceUUID) {
        return new CapturedTypeReference<>(referenceUUID);
    }

    private CapturedTypeReference(UUID typeUUID) {
        this.typeUUID = typeUUID;
    }

    @Nullable
    public T resolve() {
        return (T) CapturedType.CAPTURED_REGISTRY.resolve(typeUUID);
    }

    public T resolveOrThrow() {
        return (T) Objects.requireNonNull(CapturedType.CAPTURED_REGISTRY.resolve(typeUUID), "No captured type found with uuid " + typeUUID);
    }
}
