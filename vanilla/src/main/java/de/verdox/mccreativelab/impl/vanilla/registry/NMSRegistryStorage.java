package de.verdox.mccreativelab.impl.vanilla.registry;


import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Lifecycle;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistryStorage;
import net.kyori.adventure.key.Key;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class NMSRegistryStorage implements MCCRegistryStorage {
    private final Map<Key, DelayedFreezingRegistry<?>> CUSTOM_REGISTRIES = new HashMap<>();
    private final Supplier<RegistryAccess.Frozen> fullRegistryAccess;
    private final Supplier<RegistryAccess.Frozen> reloadableRegistries;
    private boolean frozen;

    public NMSRegistryStorage() {
        this(() -> ((NMSPlatform) MCCPlatform.getInstance()).getServer().registries().compositeAccess(), () -> ((NMSPlatform) MCCPlatform.getInstance()).getServer().reloadableRegistries().get());
    }

    @VisibleForTesting
    public NMSRegistryStorage(RegistryAccess.Frozen fullRegistryAccess, HolderGetter.Provider reloadableRegistries) {
        this(() -> fullRegistryAccess, () -> reloadableRegistries);
    }

    public NMSRegistryStorage(Supplier<RegistryAccess.Frozen> fullRegistryAccess, Supplier<HolderGetter.Provider> reloadableRegistries) {
        this.fullRegistryAccess = fullRegistryAccess;
        this.reloadableRegistries = () -> (RegistryAccess.Frozen) reloadableRegistries.get();
    }

    @Override
    public @Nullable <T> MCCRegistry<T> getMinecraftRegistry(Key registryKey) {
        MCCRegistry<T> registry = searchForRegistry(registryKey);
        if (registry == null) {
            throw new IllegalStateException("The registry " + registryKey + " does not exist");
        }
        return registry;
    }

    @Override
    @MCCReflective
    public <T> MCCReference<MCCRegistry<T>> createMinecraftRegistry(Key key) {
        if (frozen) {
            throw new IllegalStateException("The minecraft registries are already frozen. You cannot create a new registry now. The custom registries are frozen on the 'Lifecycle.BEFORE_WORLD_LOAD' platform trigger.");
        }
        if (key.namespace().equals("minecraft")) {
            throw new IllegalStateException("The minecraft namespace is reserved for minecraft registries only. Please use another namespace");
        }
        if (CUSTOM_REGISTRIES.containsKey(key)) {
            throw new IllegalStateException("A registry with the key " + key + " does already exist.");
        }

        ResourceLocation registryLocation = MCCPlatform.getInstance().getConversionService().unwrap(key);
        ResourceKey<? extends Registry<T>> registryKey = ResourceKey.createRegistryKey(registryLocation);
        DelayedFreezingRegistry<T> mappedRegistry = new DelayedFreezingRegistry<>(registryKey, Lifecycle.stable());
        CUSTOM_REGISTRIES.put(key, mappedRegistry);

        Holder<DelayedFreezingRegistry<T>> holder = new DelayedFreezingRegistryHolder<>(mappedRegistry, registryLocation, registryKey);

        return MCCPlatform.getInstance().getConversionService().wrap(holder);
    }

    @Nullable
    private <T> MCCRegistry<T> searchForRegistry(Key registryKey) {
        if (registryKey.equals(Key.key("minecraft", "root"))) {
            return MCCPlatform.getInstance().getConversionService().wrap(BuiltInRegistries.REGISTRY);
        }

        if (CUSTOM_REGISTRIES.containsKey(registryKey)) {
            return MCCPlatform.getInstance().getConversionService().wrap(CUSTOM_REGISTRIES.get(registryKey));
        }

        ResourceKey<? extends Registry<?>> registryResourceKey = ResourceKey.createRegistryKey(MCCPlatform.getInstance().getConversionService().unwrap(registryKey));

        Optional<Registry<Object>> optionalFoundRegistry = fullRegistryAccess.get().lookup(registryResourceKey);

        if (optionalFoundRegistry.isEmpty()) {
            optionalFoundRegistry = (Optional<Registry<Object>>) reloadableRegistries.get().lookup(registryResourceKey);
        }

        return optionalFoundRegistry.<MCCRegistry<T>>map(NMSRegistryLookup::new).orElse(null);
    }

    @Override
    public void freezeCustomRegistries() {
        frozen = true;
        CUSTOM_REGISTRIES.values().forEach(DelayedFreezingRegistry::delayedFreeze);
    }

    private record DelayedFreezingRegistryHolder<T>(DelayedFreezingRegistry<T> mappedRegistry,
                                                    ResourceLocation registryLocation,
                                                    ResourceKey<? extends Registry<T>> registryKey) implements Holder<DelayedFreezingRegistry<T>> {

        @Override
        public DelayedFreezingRegistry<T> value() {
            return mappedRegistry;
        }

        @Override
        public boolean isBound() {
            return true;
        }

        @Override
        public boolean is(ResourceLocation id) {
            return registryLocation.equals(id);
        }

        @Override
        public boolean is(ResourceKey<DelayedFreezingRegistry<T>> key) {
            return key.equals(registryKey);
        }

        @Override
        public boolean is(Predicate<ResourceKey<DelayedFreezingRegistry<T>>> predicate) {
            ResourceKey<DelayedFreezingRegistry<T>> cast = (ResourceKey<DelayedFreezingRegistry<T>>) registryKey;
            return predicate.test(cast);
        }

        @Override
        public boolean is(Holder<DelayedFreezingRegistry<T>> entry) {
            return mappedRegistry.equals(entry.value()) && entry.is(registryLocation);
        }

        @Override
        public Kind kind() {
            return Kind.REFERENCE;
        }

        @Override
        public Either<ResourceKey<DelayedFreezingRegistry<T>>, DelayedFreezingRegistry<T>> unwrap() {
            ResourceKey<DelayedFreezingRegistry<T>> cast = (ResourceKey<DelayedFreezingRegistry<T>>) registryKey;
            return Either.left(cast);
        }

        @Override
        public Optional<ResourceKey<DelayedFreezingRegistry<T>>> unwrapKey() {
            ResourceKey<DelayedFreezingRegistry<T>> cast = (ResourceKey<DelayedFreezingRegistry<T>>) registryKey;
            return Optional.of(cast);
        }

        @Override
        public boolean is(TagKey<DelayedFreezingRegistry<T>> tag) {
            return false;
        }

        @Override
        public Stream<TagKey<DelayedFreezingRegistry<T>>> tags() {
            return Stream.empty();
        }

        @Override
        public boolean canSerializeIn(HolderOwner<DelayedFreezingRegistry<T>> owner) {
            return true;
        }
    }
}
