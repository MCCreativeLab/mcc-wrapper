package de.verdox.mccreativelab.wrapper.platform.factory;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.*;
import net.kyori.adventure.key.Key;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Used to create typed keys
 */
public interface TypedKeyFactory {
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
    <T> MCCTypedKey<T> getKey(Key key, Key registryKey);

    <T> MCCTag<T> createTag(Key key, Key registryKey, TypeToken<T> type);
    <T> MCCTag<T> createTag(Key key, Key registryKey);

    <T> MCCReferenceSet<T> createImmutableSetWithoutKey(List<MCCReference<T>> references);

    <T> MCCTag<T> createUnboundTag(Key key, Collection<T> values);

    default <T> MCCTag<T> createUnboundTag(Key key, T... values) {
        return createUnboundTag(key, Set.of(values));
    }

    class UnboundTagBuilder<T> {
        private final Set<T> values = new HashSet<>();
        private final Key key;
        private final TypeToken<T> tagType;

        public UnboundTagBuilder(Key key, TypeToken<T> tagType){
            this.key = key;
            this.tagType = tagType;
        }

        public UnboundTagBuilder<T> with(T value){
            this.values.add(value);
            return this;
        }

        public UnboundTagBuilder<T> with(MCCReference<T> value){
            return with(value.get());
        }

        public UnboundTagBuilder<T> with(T... values){
            this.values.addAll(Arrays.stream(values).toList());
            return this;
        }

        public UnboundTagBuilder<T> with(Collection<T> values){
            this.values.addAll(values);
            return this;
        }

        public UnboundTagBuilder<T> with(MCCTag<T> tag){
            return with(tag.getValues().stream().map(MCCReference::get).collect(Collectors.toSet()));
        }

        public MCCTag<T> build(){
            return MCCPlatform.getInstance().getTypedKeyFactory().createUnboundTag(key, values);
        }
    }
}
