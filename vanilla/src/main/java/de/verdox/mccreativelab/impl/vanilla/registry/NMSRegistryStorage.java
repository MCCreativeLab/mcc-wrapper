package de.verdox.mccreativelab.impl.vanilla.registry;


import com.mojang.serialization.Lifecycle;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistryStorage;
import net.kyori.adventure.key.Key;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NMSRegistryStorage implements MCCRegistryStorage {
    private final Map<Key, DelayedFreezingRegistry<?>> CUSTOM_REGISTRIES = new HashMap<>();
    private boolean frozen;

    @Override
    public @Nullable <T> MCCRegistry<T> getMinecraftRegistry(Key registryKey) {
        if(registryKey.equals(Key.key("minecraft", "root"))){
            return MCCPlatform.getInstance().getConversionService().wrap(BuiltInRegistries.REGISTRY);
        }
        
        Registry<? extends Registry<?>> mainRegistry = BuiltInRegistries.REGISTRY;
        ResourceKey<? extends Registry<?>> registryResourceKey = ResourceKey.createRegistryKey(MCCPlatform.getInstance().getConversionService().unwrap(registryKey));
        
        Registry<?> foundBuiltinRegistry = mainRegistry.get(registryResourceKey.location());
        if(foundBuiltinRegistry != null){
            return new NMSRegistry<>(foundBuiltinRegistry);
        }

        Optional<? extends HolderLookup.RegistryLookup<?>> registryLookup = VanillaRegistries.createLookup().lookup(registryResourceKey);

        if(registryLookup.isPresent()){
            return new NMSRegistryLookup<>(registryLookup.get());
        }
        
        throw new IllegalStateException("The registry "+registryKey+" does not exist");
    }

    @Override
    @MCCReflective
    public <T> MCCRegistry<T> createMinecraftRegistry(Key key) {
        if(frozen){
            throw new IllegalStateException("The minecraft registries are already frozen. You cannot create a new registry now. The custom registries are frozen on the 'Lifecycle.BEFORE_WORLD_LOAD' platform trigger.");
        }
        if(CUSTOM_REGISTRIES.containsKey(key) || getMinecraftRegistry(key) != null){
            throw new IllegalStateException("A minecraft registry with the key "+key+" does already exist.");
        }
/*
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


        CUSTOM_REGISTRIES.add(mappedRegistry);
        return MCCPlatform.getInstance().getConversionService().wrap(mainRegistry.register((ResourceKey) registryKey, mappedRegistry, RegistrationInfo.BUILT_IN));*/
        ResourceLocation registryLocation = MCCPlatform.getInstance().getConversionService().unwrap(key);
        ResourceKey<Registry<T>> registryKey = ResourceKey.createRegistryKey(registryLocation);
        DelayedFreezingRegistry<T> mappedRegistry = new DelayedFreezingRegistry<>(registryKey, Lifecycle.stable());
        CUSTOM_REGISTRIES.put(key, mappedRegistry);
        return MCCPlatform.getInstance().getConversionService().wrap(mappedRegistry);
    }

    @Override
    public void freezeCustomRegistries() {
        frozen = true;
        CUSTOM_REGISTRIES.values().forEach(DelayedFreezingRegistry::delayedFreeze);
    }
}
