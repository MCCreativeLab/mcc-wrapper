package de.verdox.mccreativelab.impl.vanilla.registry;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Registry;

import java.util.Optional;

public class NMSRootRegistry extends NMSRegistry<MCCRegistry<?>, Registry<?>> {
    public NMSRootRegistry(Registry<Registry<?>> handle) {
        super(handle);
    }

    @Override
    public Optional<MCCReference<MCCRegistry<?>>> getReference(Key key) {
        try {
            return Optional.of(wrapAsReference(MCCPlatform.getInstance().getRegistryStorage().getMinecraftRegistry(key)));
        } catch (Throwable e) {
            return Optional.empty();
        }
    }
}
