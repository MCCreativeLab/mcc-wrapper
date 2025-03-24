package de.verdox.mccreativelab.wrapper.registry;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Represents a minecraft registry
 *
 * @param <T> the type
 */
public interface MCCRegistry<T> extends MCCWrapped {
    /**
     * Gets the key of a provided value
     *
     * @param value the value
     * @return the key
     */
    @Nullable
    Key getKey(T value);

    /**
     * Gets the typed key of a provided value if the value is known to the registry. Else an empty optional is returned
     *
     * @param value the value
     * @return the typed key of the value as optional
     */
    Optional<MCCTypedKey<T>> getTypedKey(T value);

    /**
     * Gets the value associated with the provided key or null if the key is unknown to the registry
     *
     * @param key the provided key
     * @return the found value or null
     */
    @Nullable
    T get(@Nullable MCCTypedKey<T> key);

    /**
     * Gets the value associated with the provided key or null if the key is unknown to the registry
     *
     * @param key the provided key
     * @return the found value or null
     */
    @Nullable
    T get(@Nullable Key key);

    /**
     * Gets the value associated with the provided key or null if the key is unknown to the registry
     *
     * @param key the provided key
     * @return the found value as optional
     */
    default Optional<T> getOptional(@Nullable Key key) {
        return Optional.ofNullable(get(key));
    }

    /**
     * Gets the value associated with the provided key or null if the key is unknown to the registry
     *
     * @param key the provided key
     * @return the found value as optional
     */
    default Optional<T> getOptional(@Nullable MCCTypedKey<T> key) {
        return Optional.ofNullable(get(key));
    }

    /**
     * Gets any value from the registry
     *
     * @return any value as optional
     */
    Optional<MCCReference<T>> getAny();

    /**
     * Gets the value associated with the provided key or throws an Exception when the value is unknown
     *
     * @param key the provided key
     * @return the found value
     */
    T getOrThrow(MCCTypedKey<T> key);

    /**
     * Returns the key set of the registry
     *
     * @return the key set
     */
    Set<Key> keySet();

    /**
     * Returns the typed key set of the registry
     *
     * @return the typed key set
     */
    Set<MCCTypedKey<T>> registryKeySet();

    /**
     * Checks if the key is known to the registry
     *
     * @param key the key
     * @return true if the key is known to the registry
     */
    boolean containsKey(Key key);

    /**
     * Checks if the key is known to the registry
     *
     * @param key the key
     * @return true if the key is known to the registry
     */
    boolean containsKey(MCCTypedKey<T> key);

    /**
     * Gets the reference associated with the provided id
     *
     * @param value the id
     * @return the reference as optional
     */
    default Optional<MCCReference<T>> getReference(T value) {
        MCCTypedKey<T> key = getTypedKey(value).get();
        return getReference(key);
    }

    /**
     * Gets the reference associated with the provided key
     *
     * @param key the key
     * @return the reference as optional
     */
    Optional<MCCReference<T>> getReference(Key key);

    /**
     * Gets the reference associated with the provided key
     *
     * @param key the key
     * @return the reference as optional
     */
    Optional<MCCReference<T>> getReference(MCCTypedKey<T> key);

    /**
     * Wraps a value that belongs to this registry as a reference
     *
     * @param value the value
     * @return the value wrapped as reference
     */
    MCCReference<T> wrapAsReference(T value);

    /**
     * Returns a reference set that belongs to a tag
     *
     * @param tag the tag
     * @return the reference set as an optional
     */
    Optional<MCCReferenceSet<T>> getTagValues(MCCTag<T> tag);

    /**
     * Gets or registers a tag to this registry
     *
     * @param tag the tag key
     * @return the references set
     */
    MCCReferenceSet<T> getOrCreateTag(MCCTag<T> tag);

    /**
     * Returns an iterable that iterates through all values of the tag
     *
     * @param tag the tag
     * @return the iterable
     */
    Iterable<MCCReference<T>> iterate(MCCTag<T> tag);

    /**
     * Returns all tag names known to this registry
     *
     * @return a stream of all tags
     */
    Stream<MCCTag<T>> getTagNames();

    /**
     * Used to register a new value to this registry. Works only when the registry is not frozen yet.
     * @param key the key for this value
     * @param value the value
     * @return the reference
     */
    MCCReference<T> register(MCCTypedKey<T> key, T value);
}
