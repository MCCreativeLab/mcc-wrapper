package de.verdox.mccreativelab.impl.vanilla.registry;


import com.google.common.reflect.TypeToken;
import com.mojang.serialization.Lifecycle;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistryStorage;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import net.kyori.adventure.key.Key;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class NMSRegistryStorage implements MCCRegistryStorage {
    private final Set<DelayedFreezingRegistry<?>> CUSTOM_REGISTRIES = new HashSet<>();

    @Override
    public @Nullable <T> MCCRegistry<T> getRegistry(MCCTypedKey<MCCRegistry<T>> registryKey) {
        ResourceKey<? extends Registry<?>> resourceKey = MCCPlatform.getInstance().getConversionService().unwrap(registryKey);

        Registry<? extends Registry<?>> mainRegistry;

        ResourceLocation registryLocation = resourceKey.registry();
        if (registryLocation.equals(Registries.ROOT_REGISTRY_NAME)) {
            mainRegistry = BuiltInRegistries.REGISTRY;
        } else {
            throw new IllegalArgumentException("Registry of registries not found: " + resourceKey.registry());
        }
        return MCCPlatform.getInstance().getConversionService().wrap(mainRegistry.get(resourceKey.location()));
    }

    @Override
    public @Nullable <T> MCCRegistry<T> getMinecraftRegistry(Key registryKey) {
        Registry<? extends Registry<?>> mainRegistry = BuiltInRegistries.REGISTRY;
        return MCCPlatform.getInstance().getConversionService().wrap(mainRegistry.get(MCCPlatform.getInstance().getConversionService().unwrap(registryKey, ResourceLocation.class)));
    }

    @Override
    @MCCReflective
    public <T> MCCReference<MCCRegistry<T>> createMinecraftRegistry(Key key) {

        MappedRegistry<WritableRegistry<?>> mainRegistry = (MappedRegistry<WritableRegistry<?>>) BuiltInRegistries.REGISTRY;
        ResourceLocation registryLocation = MCCPlatform.getInstance().getConversionService().unwrap(key, new TypeToken<>() {});
        if (mainRegistry.containsKey(registryLocation)) {
            throw new IllegalArgumentException("A registry with the key " + key + " does already exist");
        }
        ResourceKey<Registry<T>> registryKey = ResourceKey.createRegistryKey(registryLocation);
        try {
            mainRegistry.validateWrite((ResourceKey) registryKey);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("The platform registries are already frozen. Please create new registries when the platform is bootstrapped.");
        }

        DelayedFreezingRegistry<T> mappedRegistry = new DelayedFreezingRegistry<>(registryKey, Lifecycle.stable());
        CUSTOM_REGISTRIES.add(mappedRegistry);
        return MCCPlatform.getInstance().getConversionService().wrap(mainRegistry.register((ResourceKey) registryKey, mappedRegistry, RegistrationInfo.BUILT_IN));
    }

    @Override
    public void freezeCustomRegistries() {
        CUSTOM_REGISTRIES.forEach(DelayedFreezingRegistry::delayedFreeze);
    }
}
