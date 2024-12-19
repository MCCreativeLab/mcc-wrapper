package de.verdox.mccreativelab.impl.vanilla.registry;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * The minecraft registry will be frozen on bootstrap. This registry delays freezing
 * @param <T> the registry type
 */
public class DelayedFreezingRegistry<T> extends MappedRegistry<T> {
    public DelayedFreezingRegistry(ResourceKey<? extends Registry<T>> key, Lifecycle lifecycle) {
        super(key, lifecycle, false);
    }

    @Override
    public Registry<T> freeze() {
        return this;
    }

    public void delayedFreeze(){
        super.freeze();
    }
}
