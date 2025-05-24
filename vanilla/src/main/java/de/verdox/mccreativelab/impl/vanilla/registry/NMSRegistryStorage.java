package de.verdox.mccreativelab.impl.vanilla.registry;


import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Lifecycle;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.impl.vanilla.registry.recipe.NMSRecipeManager;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.*;
import net.kyori.adventure.key.Key;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class NMSRegistryStorage implements MCCRegistryStorage {
    private static final Logger LOGGER = Logger.getLogger(MCCRegistryStorage.class.getSimpleName());

    private final Map<Key, DelayedFreezingRegistry<?>> CUSTOM_REGISTRIES = new HashMap<>();
    private final Map<Key, OpenRegistry<?>> CUSTOM_OPEN_REGISTRIES = new HashMap<>();
    private final Supplier<RegistryAccess.Frozen> fullRegistryAccess;
    private final Supplier<RegistryAccess.Frozen> reloadableRegistries;
    private final Supplier<RecipeManager> recipeManager;
    private boolean frozen;

    public NMSRegistryStorage() {
        this(() -> ((NMSPlatform) MCCPlatform.getInstance()).getServer().registries().compositeAccess(), () -> ((NMSPlatform) MCCPlatform.getInstance()).getServer().reloadableRegistries().lookup(), () -> ((NMSPlatform) MCCPlatform.getInstance()).getServer().getRecipeManager());
    }

    @VisibleForTesting
    public NMSRegistryStorage(RegistryAccess.Frozen fullRegistryAccess, HolderGetter.Provider reloadableRegistries, RecipeManager recipeManager) {
        this(() -> fullRegistryAccess, () -> reloadableRegistries, () -> recipeManager);
    }

    public NMSRegistryStorage(Supplier<RegistryAccess.Frozen> fullRegistryAccess, Supplier<HolderGetter.Provider> reloadableRegistries, Supplier<RecipeManager> recipeManager) {
        this.fullRegistryAccess = fullRegistryAccess;
        this.reloadableRegistries = () -> (RegistryAccess.Frozen) reloadableRegistries.get();
        this.recipeManager = recipeManager;
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
    public <T> MCCRegistry<T> getMinecraftRegistry(Key registryKey, TypeToken<T> type) {
        return getMinecraftRegistry(registryKey);
    }

    @Override
    public boolean deleteCustomMinecraftRegistry(Key key) {
        return CUSTOM_REGISTRIES.remove(key) != null;
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
        if (CUSTOM_REGISTRIES.containsKey(key) || CUSTOM_OPEN_REGISTRIES.containsKey(key)) {
            throw new IllegalStateException("A registry with the key " + key + " does already exist.");
        }

        ResourceLocation registryLocation = MCCPlatform.getInstance().getConversionService().unwrap(key);
        ResourceKey<? extends Registry<T>> registryKey = ResourceKey.createRegistryKey(registryLocation);
        DelayedFreezingRegistry<T> mappedRegistry = new DelayedFreezingRegistry<>(registryKey, Lifecycle.stable());
        CUSTOM_REGISTRIES.put(key, mappedRegistry);
        LOGGER.info("Creating the custom minecraft registry " + key);

        Holder<DelayedFreezingRegistry<T>> holder = new DelayedFreezingRegistryHolder<>(mappedRegistry, registryLocation, registryKey);

        return MCCPlatform.getInstance().getConversionService().wrap(holder, new TypeToken<>() {});
    }

    @Override
    public <T> MCCReference<OpenRegistry<T>> createOpenRegistry(Key key) {
        if (key.namespace().equals("minecraft")) {
            throw new IllegalStateException("The minecraft namespace is reserved for minecraft registries only. Please use another namespace");
        }
        if (CUSTOM_REGISTRIES.containsKey(key) || CUSTOM_OPEN_REGISTRIES.containsKey(key)) {
            throw new IllegalStateException("A registry with the key " + key + " does already exist.");
        }

        OpenRegistry<T> openRegistry = OpenRegistry.createUnboundRegistry(key);
        CUSTOM_OPEN_REGISTRIES.put(key, openRegistry);
        LOGGER.info("Creating the custom open registry " + key);

        AtomicReference<MCCReference<OpenRegistry<T>>> reference = new AtomicReference<>();
        AtomicReference<MCCTypedKey<OpenRegistry<T>>> typedKey = new AtomicReference<>();

        typedKey.set(new MCCTypedKey<>() {
            @Override
            public @Nullable OpenRegistry<T> get() {
                return openRegistry;
            }

            @Override
            public Key getRegistryKey() {
                return key;
            }

            @Override
            public MCCReference<OpenRegistry<T>> getAsReference() {
                return reference.get();
            }

            @Override
            public Optional<MCCReference<OpenRegistry<T>>> getAsOptionalReference() {
                return Optional.ofNullable(reference.get());
            }

            @Override
            public @NotNull Key key() {
                return key;
            }
        });

        reference.set(new MCCReference<>() {
            @Override
            public Optional<MCCTypedKey<OpenRegistry<T>>> unwrapKey() {
                return Optional.ofNullable(typedKey.get());
            }

            @Override
            public OpenRegistry<T> get() {
                return openRegistry;
            }
        });
        return reference.get();
    }

    @Nullable
    private <T> MCCRegistry<T> searchForRegistry(Key registryKey) {
        if (registryKey.equals(Key.key("minecraft", "root"))) {
            return MCCPlatform.getInstance().getConversionService().wrap(BuiltInRegistries.REGISTRY, new TypeToken<>() {});
        }

        if (!registryKey.namespace().equals("minecraft")) {
            if (CUSTOM_REGISTRIES.containsKey(registryKey)) {
                return MCCPlatform.getInstance().getConversionService().wrap(CUSTOM_REGISTRIES.get(registryKey), new TypeToken<>() {});
            } else if (CUSTOM_OPEN_REGISTRIES.containsKey(registryKey)) {
                return MCCPlatform.getInstance().getConversionService().wrap(CUSTOM_OPEN_REGISTRIES.get(registryKey), new TypeToken<>() {});
            } else {
                throw new NoSuchElementException("Could not find the registry " + registryKey);
            }
        }

        if (registryKey.equals(Key.key("minecraft", "recipe"))) {
            Objects.requireNonNull(recipeManager.get(), "The platform recipe manager was not initialized yet");
            return (MCCRegistry<T>) new NMSRecipeManager(recipeManager.get());
        }

        ResourceKey<? extends Registry<?>> registryResourceKey = ResourceKey.createRegistryKey(MCCPlatform.getInstance().getConversionService().unwrap(registryKey));

        Optional<Registry<Object>> optionalFoundRegistry = fullRegistryAccess.get().lookup(registryResourceKey);

        if (optionalFoundRegistry.isEmpty()) {
            optionalFoundRegistry = reloadableRegistries.get().lookup(registryResourceKey);
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
