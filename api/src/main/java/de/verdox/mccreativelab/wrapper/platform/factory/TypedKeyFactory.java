package de.verdox.mccreativelab.wrapper.platform.factory;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.registry.MCCReferenceSet;
import de.verdox.mccreativelab.wrapper.registry.MCCTag;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Used to create typed keys
 */
public interface TypedKeyFactory {
    @NotNull Key REGISTRY_OF_REGISTRIES = Key.key("minecraft", "root");

    default <T> MCCTypedKey<T> getRegistryKey(String registryKey, TypeToken<T> type){
        return getKey(Key.key("minecraft", registryKey), REGISTRY_OF_REGISTRIES, type);
    }

    default <T> MCCTypedKey<T> getRegistryKey(String registryKey){
        return getKey(Key.key("minecraft", registryKey), REGISTRY_OF_REGISTRIES);
    }

    /**
     * Creates a typed key for a specific type
     *
     * @param key         the key of the typed key
     * @param registryKey the key of registry
     * @param type        the type
     * @param <T>         the generic wrapped type
     * @return the typed key
     */
    <T> MCCTypedKey<T> getKey(Key key, Key registryKey, TypeToken<T> type);

    /**
     * Creates a typed key
     *
     * @param key         the key of the typed key
     * @param registryKey the key of registry
     * @param <T>         the generic wrapped type
     * @return the typed key
     */
    <T> MCCTypedKey<T> getKey(Key key, Key registryKey);

    /**
     * Creates a tag for a specific type
     *
     * @param key         the key of the typed key
     * @param registryKey the key of registry
     * @param type        the type
     * @param <T>         the generic wrapped type
     * @return the tag
     */
    <T> MCCTag<T> createTag(Key key, Key registryKey, TypeToken<T> type);

    /**
     * Creates a tag
     *
     * @param key         the key of the typed key
     * @param registryKey the key of registry
     * @param <T>         the generic wrapped type
     * @return the tag
     */
    <T> MCCTag<T> createTag(Key key, Key registryKey);

    /**
     * Creates an immutable set without a key
     *
     * @param <T>         the generic wrapped type
     * @return the immutable set without key
     */
    <T> MCCReferenceSet<T> createImmutableSetWithoutKey(List<MCCReference<T>> references);

    /**
     * Creates an unbound tag
     * @param key the key of the tag
     * @param values the values of the tag
     * @return the tag
     * @param <T> the generic wrapped type
     */
    <T> MCCTag<T> createUnboundTag(Key key, Collection<T> values);

    /**
     * Creates an unbound tag
     * @param key the key of the tag
     * @param values the values of the tag
     * @return the tag
     * @param <T> the generic wrapped type
     */
    default <T> MCCTag<T> createUnboundTag(Key key, T... values) {
        return createUnboundTag(key, Set.of(values));
    }

    class UnboundTagBuilder<T> {
        private final Set<T> values = new HashSet<>();
        private final Key key;
        private final TypeToken<T> tagType;

        public UnboundTagBuilder(Key key, TypeToken<T> tagType) {
            this.key = key;
            this.tagType = tagType;
        }

        public UnboundTagBuilder<T> with(T value) {
            this.values.add(value);
            return this;
        }

        public UnboundTagBuilder<T> with(MCCReference<T> value) {
            return with(value.get());
        }

        public UnboundTagBuilder<T> with(T... values) {
            this.values.addAll(Arrays.stream(values).toList());
            return this;
        }

        public UnboundTagBuilder<T> with(Collection<T> values) {
            this.values.addAll(values);
            return this;
        }

        public UnboundTagBuilder<T> with(MCCTag<T> tag) {
            return with(tag.getValues().stream().map(MCCReference::get).collect(Collectors.toSet()));
        }

        public MCCTag<T> build() {
            return MCCPlatform.getInstance().getTypedKeyFactory().createUnboundTag(key, values);
        }
    }
}
