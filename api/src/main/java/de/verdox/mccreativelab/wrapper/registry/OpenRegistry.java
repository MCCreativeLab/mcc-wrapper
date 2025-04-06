package de.verdox.mccreativelab.wrapper.registry;

import de.verdox.mccreativelab.wrapper.annotations.MCCLogic;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

/**
 * An open registry is a custom implementation of a minecraft registry that will never freeze and is therefore dynamic.
 *
 * @param <T>
 */
public class OpenRegistry<T> implements MCCCustomRegistry<T> {
    private final Map<Key, T> registry = new HashMap<>();
    private final Map<T, Key> dataToKeyMapping = new HashMap<>();
    private final Map<T, MCCTypedKey<T>> dataToTypedKeyMapping = new HashMap<>();
    private final Map<MCCTypedKey<T>, T> typedKeyToDataMapping = new HashMap<>();

    @Override
    public @Nullable Key getKey(T value) {
        return dataToKeyMapping.get(value);
    }

    @Override
    public Optional<MCCTypedKey<T>> getTypedKey(T value) {
        return Optional.ofNullable(dataToTypedKeyMapping.get(value));
    }

    @Override
    public @Nullable T get(@Nullable MCCTypedKey<T> key) {
        return typedKeyToDataMapping.get(key);
    }

    @Override
    public @Nullable T get(@Nullable Key key) {
        return registry.get(key);
    }

    @Override
    public Optional<MCCReference<T>> getAny() {
        return dataToTypedKeyMapping.values().stream().findAny().map(MCCTypedKey::getAsReference);
    }

    @Override
    public T getOrThrow(MCCTypedKey<T> key) {
        return Objects.requireNonNull(get(key), "Could not find element with key " + key + " in registry.");
    }

    @Override
    public Set<Key> keySet() {
        return registry.keySet();
    }

    @Override
    public Set<MCCTypedKey<T>> registryKeySet() {
        return typedKeyToDataMapping.keySet();
    }

    @Override
    public boolean containsKey(Key key) {
        return registry.containsKey(key);
    }

    @Override
    public boolean containsKey(MCCTypedKey<T> key) {
        return typedKeyToDataMapping.containsKey(key);
    }

    @Override
    public Optional<MCCReference<T>> getReference(Key key) {
        T value = get(key);
        if (value == null) {
            return Optional.empty();
        }

        return Optional.of(new MCCReference<T>() {
            @Override
            public Optional<MCCTypedKey<T>> unwrapKey() {
                return getTypedKey(value);
            }

            @Override
            public T get() {
                return value;
            }
        });
    }

    @Override
    public Optional<MCCReference<T>> getReference(MCCTypedKey<T> key) {
        return key.getAsOptionalReference();
    }

    @Override
    public MCCReference<T> wrapAsReference(T value) {
        return getTypedKey(value).orElseThrow(NoSuchElementException::new).getAsReference();
    }

    @Override
    @MCCLogic
    public Optional<MCCReferenceSet<T>> getTagValues(MCCTag<T> tag) {
        throw new UnsupportedOperationException("This is not possible in a custom open registry yet.");
    }

    @Override
    @MCCLogic
    public MCCReferenceSet<T> getOrCreateTag(MCCTag<T> tag) {
        throw new UnsupportedOperationException("This is not possible in a custom open registry yet.");
    }

    @Override
    @MCCLogic
    public Iterable<MCCReference<T>> iterate(MCCTag<T> tag) {
        throw new UnsupportedOperationException("This is not possible in a custom open registry yet.");
    }

    @Override
    @MCCLogic
    public Stream<MCCTag<T>> getTagNames() {
        throw new UnsupportedOperationException("This is not possible in a custom open registry yet.");
    }

    @Override
    public MCCReference<T> register(MCCTypedKey<T> key, T value) {
        registry.put(key.key(), value);
        dataToKeyMapping.put(value, key.key());
        dataToTypedKeyMapping.put(value, key);
        typedKeyToDataMapping.put(key, value);
        return getReference(value).orElseThrow(NoSuchElementException::new);
    }

    public void clear() {
        registry.clear();
        dataToKeyMapping.clear();
        dataToTypedKeyMapping.clear();
        typedKeyToDataMapping.clear();
    }

    public void remove(MCCTypedKey<T> key) {
        T value = get(key);
        Objects.requireNonNull(value, "Value is not saved in this registry");

        registry.remove(key.key());
        dataToKeyMapping.remove(value);
        dataToTypedKeyMapping.remove(value);
        typedKeyToDataMapping.remove(key);
    }

    public void remove(T value) {
        MCCTypedKey<T> typedKey = getTypedKey(value).orElseThrow(NoSuchElementException::new);
        remove(typedKey);
    }
}
