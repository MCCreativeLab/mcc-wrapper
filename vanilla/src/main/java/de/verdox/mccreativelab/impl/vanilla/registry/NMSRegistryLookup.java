package de.verdox.mccreativelab.impl.vanilla.registry;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.*;
import net.kyori.adventure.key.Key;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NMSRegistryLookup<T, R> extends MCCHandle<HolderLookup<R>> implements MCCRegistry<T> {
    public static final MCCConverter<HolderLookup, NMSRegistryLookup> CONVERTER = converter(NMSRegistryLookup.class, HolderLookup.class, holderLookup -> {

        if (holderLookup instanceof HolderLookup.RegistryLookup<?> registryLookup) {
            return new NMSRegistryLookup<>(registryLookup);
        }
        throw new IllegalStateException("The HolderLookup type " + holderLookup.getClass() + " is not known.");

    }, registry -> (HolderLookup) registry.getHandle());
    private final ResourceKey<? extends Registry<? extends R>> registryKey;

    public NMSRegistryLookup(HolderLookup.RegistryLookup<R> handle) {
        this(handle, handle.key());
    }

    public NMSRegistryLookup(HolderLookup<R> handle, ResourceKey<? extends Registry<? extends R>> registryKey) {
        super(handle);
        this.registryKey = registryKey;
    }

    @Override
    public @Nullable Key getKey(T value) {
        R unwrapped = conversionService.unwrap(value);
        return handle.listElements().filter(rReference -> rReference.value().equals(unwrapped)).findAny().map(rReference -> conversionService.wrap(rReference.key().location(), Key.class)).get();
    }

    @Override
    public Optional<MCCTypedKey<T>> getTypedKey(T value) {
        R unwrapped = conversionService.unwrap(value);
        return handle.listElements().filter(rReference -> rReference.value().equals(unwrapped)).findAny().map(rReference -> conversionService.wrap(rReference.key(), new TypeToken<>() {}));

    }

    @Override
    public @Nullable T get(@Nullable MCCTypedKey<T> key) {
        return conversionService.wrap(handle.get(conversionService.unwrap(key, new TypeToken<ResourceKey<R>>() {})).get().value());
    }

    @Override
    public @Nullable T get(@Nullable Key key) {
        return get(MCCPlatform.getInstance().getTypedKeyFactory().getKey(key, conversionService.wrap(registryKey.location(), new TypeToken<>() {})));
    }

    @Override
    public Optional<MCCReference<T>> getAny() {
        return handle.listElements().findAny().map(rReference -> conversionService.wrap(rReference, new TypeToken<>() {}));
    }

    @Override
    public T getOrThrow(MCCTypedKey<T> key) {
        return conversionService.wrap(handle.getOrThrow(conversionService.unwrap(key, new TypeToken<ResourceKey<R>>() {})).value());
    }

    @Override
    public Set<Key> keySet() {
        return handle.listElementIds().map(ResourceKey::location).map(resourceLocation -> conversionService.wrap(resourceLocation, Key.class)).collect(Collectors.toSet());
    }

    @Override
    public Set<MCCTypedKey<T>> registryKeySet() {
        return handle.listElementIds().map(resourceKey -> conversionService.wrap(resourceKey, new TypeToken<MCCTypedKey<T>>() {})).collect(Collectors.toSet());
    }

    @Override
    public boolean containsKey(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean containsKey(MCCTypedKey<T> key) {
        return get(key) != null;
    }

    @Override
    public Optional<MCCReference<T>> getReference(Key key) {
        return getReference(MCCPlatform.getInstance().getTypedKeyFactory().getKey(key, conversionService.wrap(registryKey.location(), new TypeToken<>() {})));
    }

    @Override
    public Optional<MCCReference<T>> getReference(MCCTypedKey<T> key) {
        return conversionService.wrap(handle.get(conversionService.unwrap(key, new TypeToken<ResourceKey<R>>() {})), new TypeToken<>() {});
    }

    @Override
    public MCCReference<T> wrapAsReference(T value) {
        Key key = getKey(value);
        if (key == null) {
            throw new IllegalArgumentException("Element " + value + " could not be found in registry " + registryKey);
        }
        return getReference(key).orElseThrow(() -> new IllegalArgumentException("Element " + value + " could not be found in registry " + registryKey));
    }

    @Override
    public Optional<MCCReferenceSet<T>> getTagValues(MCCTag<T> tag) {
        TagKey<R> tagKey = conversionService.unwrap(tag);
        Optional<HolderSet.Named<R>> holderSet = handle.get(tagKey);
        return conversionService.wrap(holderSet, new TypeToken<>() {});
    }

    @Override
    public MCCReferenceSet<T> getOrCreateTag(MCCTag<T> tag) {
        TagKey<R> tagKey = conversionService.unwrap(tag);
        HolderSet.Named<R> holderSet = handle.getOrThrow(tagKey);
        return conversionService.wrap(holderSet, new TypeToken<>() {});
    }

    @Override
    public Iterable<MCCReference<T>> iterate(MCCTag<T> tag) {
        return new Iterable<>() {
            @Override
            public @NotNull Iterator<MCCReference<T>> iterator() {
                return getOrCreateTag(tag).iterator();
            }
        };
    }

    @Override
    public Stream<MCCTag<T>> getTagNames() {
        return handle.listTagIds().map(rTagKey -> conversionService.wrap(rTagKey, new TypeToken<>() {}));
    }

    @Override
    public MCCReference<T> register(MCCTypedKey<T> key, T value) {
        throw new UnsupportedOperationException("Trying to write to a registry that is already frozen " + registryKey);
    }
}
