package de.verdox.mccreativelab.impl.vanilla.registry;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.*;
import net.kyori.adventure.key.Key;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NMSRegistryLookup<T, R> extends MCCHandle<HolderLookup.RegistryLookup<R>> implements MCCRegistry<T> {
    public static final MCCConverter<HolderLookup.RegistryLookup, NMSRegistryLookup> CONVERTER = converter(NMSRegistryLookup.class, HolderLookup.RegistryLookup.class, NMSRegistryLookup::new, registry -> (HolderLookup.RegistryLookup) registry.getHandle());

    public NMSRegistryLookup(HolderLookup.RegistryLookup<R> handle) {
        super(handle);
    }

    @Override
    public @Nullable Key getKey(T value) {
        R unwrapped = conversionService.unwrap(value);
        return handle.listElements().filter(rReference -> rReference.value().equals(unwrapped)).findAny().map(rReference -> conversionService.wrap(rReference.key().location(), Key.class)).get();
    }

    @Override
    public Optional<MCCTypedKey<T>> getTypedKey(T value) {
        R unwrapped = conversionService.unwrap(value);
        return handle.listElements().filter(rReference -> rReference.value().equals(unwrapped)).findAny().map(rReference -> conversionService.wrap(rReference.key()));

    }

    @Override
    public @Nullable T get(@Nullable MCCTypedKey<T> key) {
        return conversionService.wrap(handle.get(conversionService.unwrap(key, new TypeToken<ResourceKey<R>>() {})).get().value());
    }

    @Override
    public @Nullable T get(@Nullable Key key) {
        return get(MCCPlatform.getInstance().getTypedKeyFactory().getKey(key, conversionService.wrap(handle.key().location())));
    }

    @Override
    public Optional<MCCReference<T>> getAny() {
        return handle.listElements().findAny().map(rReference -> conversionService.wrap(rReference));
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
        return handle.listElementIds().map(resourceKey -> conversionService.<ResourceKey<R>, MCCTypedKey<T>>wrap(resourceKey)).collect(Collectors.toSet());
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
        return getReference(MCCPlatform.getInstance().getTypedKeyFactory().getKey(key, conversionService.wrap(handle.key().location())));
    }

    @Override
    public Optional<MCCReference<T>> getReference(MCCTypedKey<T> key) {
        return conversionService.wrap(handle.get(conversionService.unwrap(key, new TypeToken<ResourceKey<R>>() {})));
    }

    @Override
    public MCCReference<T> wrapAsReference(T value) {
        Key key = getKey(value);
        if (key == null) {
            throw new IllegalArgumentException("Element " + value + " could not be found in registry " + this.handle.key());
        }
        return getReference(key).orElseThrow(() -> new IllegalArgumentException("Element " + value + " could not be found in registry " + this.handle.key()));
    }

    @Override
    public Optional<MCCReferenceSet<T>> getTag(MCCTag<T> tag) {
        TagKey<R> tagKey = conversionService.unwrap(tag);
        Optional<HolderSet.Named<R>> holderSet = handle.get(tagKey);
        return conversionService.wrap(holderSet);
    }

    @Override
    public MCCReferenceSet<T> getOrCreateTag(MCCTag<T> tag) {
        TagKey<R> tagKey = conversionService.unwrap(tag);
        HolderSet.Named<R> holderSet = handle.getOrThrow(tagKey);
        return conversionService.wrap(holderSet);
    }

    @Override
    public Stream<MCCTag<T>> getTagNames() {
        return handle.listTagIds().map(rTagKey -> conversionService.wrap(rTagKey));
    }

    @Override
    public MCCReference<T> register(MCCTypedKey<T> key, T value) {
        throw new UnsupportedOperationException("Trying to write to a registry that is already frozen " + this.handle.key());
    }
}