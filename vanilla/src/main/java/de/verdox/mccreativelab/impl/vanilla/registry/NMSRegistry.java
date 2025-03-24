package de.verdox.mccreativelab.impl.vanilla.registry;

import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.*;
import net.kyori.adventure.key.Key;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

public class NMSRegistry<T, R> extends MCCHandle<Registry<R>> implements MCCRegistry<T> {
    public static final MCCConverter<Registry, NMSRegistry> CONVERTER = converter(NMSRegistry.class, Registry.class, NMSRegistry::new, registry -> (Registry) registry.getHandle());
    private final ConversionService conversionService;

    public NMSRegistry(Registry<R> handle) {
        super(handle);
        conversionService = MCCPlatform.getInstance().getConversionService();
    }

    private R unwrap(T value) {
        if (value == null)
            return null;
        return conversionService.unwrap(value);
    }

    private T wrap(R value) {
        if (value == null)
            return null;
        return conversionService.wrap(value);
    }

    private ResourceLocation unwrap(Key key) {
        return conversionService.unwrap(key);
    }

    @Override
    public @Nullable Key getKey(T value) {
        return conversionService.wrap(handle.getKey(unwrap(value)));
    }

    @Override
    public Optional<MCCTypedKey<T>> getTypedKey(T value) {
        return conversionService.wrap(handle.getResourceKey(unwrap(value)));
    }

    @Override
    public @Nullable T get(@Nullable MCCTypedKey<T> key) {
        return key.get();
    }

    @Override
    public @Nullable T get(@Nullable Key id) {
        return wrap(handle.getValue(unwrap(id)));
    }

    @Override
    public Optional<MCCReference<T>> getAny() {
        return conversionService.wrap(handle.getAny());
    }

    @Override
    public T getOrThrow(MCCTypedKey<T> key) {
        return key.getAsReference().get();
    }

    @Override
    public Set<Key> keySet() {
        return conversionService.wrap(handle.keySet());
    }

    @Override
    public Set<MCCTypedKey<T>> registryKeySet() {
        return conversionService.wrap(handle.registryKeySet());
    }

    @Override
    public boolean containsKey(Key key) {
        return handle.containsKey(unwrap(key));
    }

    @Override
    public boolean containsKey(MCCTypedKey<T> key) {
        return handle.containsKey(ResourceKey.create(ResourceKey.createRegistryKey(unwrap(key.getRegistryKey())), unwrap(key.key())));
    }

    @Override
    public Optional<MCCReference<T>> getReference(Key key) {
        return conversionService.wrap(handle.get(unwrap(key)));
    }

    @Override
    public Optional<MCCReference<T>> getReference(MCCTypedKey<T> key) {
        return key.getAsOptionalReference();
    }

    @Override
    public MCCReference<T> wrapAsReference(T value) {
        return conversionService.wrap(handle.wrapAsHolder(unwrap(value)));
    }

    @Override
    public Optional<MCCReferenceSet<T>> getTagValues(MCCTag<T> tag) {
        return Optional.of(getOrCreateTag(tag)) ;
    }

    @Override
    public MCCReferenceSet<T> getOrCreateTag(MCCTag<T> tag) {
        List<MCCReference<T>> list = new ArrayList<>();
        handle.getTagOrEmpty(conversionService.unwrap(tag)).iterator().forEachRemaining(rHolder -> list.add(conversionService.wrap(rHolder)));
        return MCCPlatform.getInstance().getTypedKeyFactory().createImmutableSetWithoutKey(list);
    }

    @Override
    public Iterable<MCCReference<T>> iterate(MCCTag<T> tag) {
        return getOrCreateTag(tag);
    }

    @Override
    public Stream<MCCTag<T>> getTagNames() {
        return handle.getTags().map(conversionService::wrap);
    }

    @Override
    public MCCReference<T> register(MCCTypedKey<T> key, T value) {
        WritableRegistry<R> writableRegistry = (WritableRegistry<R>) handle;
        return conversionService.wrap(writableRegistry.register(conversionService.unwrap(key), conversionService.unwrap(value), RegistrationInfo.BUILT_IN));
    }
}
