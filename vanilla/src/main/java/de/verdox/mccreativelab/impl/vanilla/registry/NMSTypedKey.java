package de.verdox.mccreativelab.impl.vanilla.registry;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistry;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import net.kyori.adventure.key.Key;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * An NMS implementation for a NMSTypedKey
 *
 * @param <T> the api type
 */
public class NMSTypedKey<T, F> extends MCCHandle<ResourceKey<F>> implements MCCTypedKey<T> {
    public static final MCCConverter<ResourceKey, NMSTypedKey> CONVERTER = converter(NMSTypedKey.class, ResourceKey.class, NMSTypedKey::new, resourceKey -> (ResourceKey) resourceKey.getHandle());
    private final Key key;
    private final Key registryKey;

    public NMSTypedKey(Key key, Key registryKey) {
        this(ResourceKey.create(ResourceKey.createRegistryKey(MCCPlatform.getInstance().getConversionService().unwrap(Objects.requireNonNull(registryKey, "Registry key cannot be null"), ResourceLocation.class)), MCCPlatform.getInstance().getConversionService().unwrap(Objects.requireNonNull(key, "Key cannot be null"), ResourceLocation.class)));
    }

    public NMSTypedKey(ResourceKey<F> resourceKey) {
        super(resourceKey);
        this.key = conversionService.wrap(Objects.requireNonNull(resourceKey.location(), "The conversion produced no value key. This is a bug"));
        this.registryKey = conversionService.wrap(Objects.requireNonNull(resourceKey.registry(), "The conversion produced no registry key. This is a bug"));
    }

    @Override
    public @Nullable T get() {
        return getAsOptionalReference().map(MCCReference::get).orElse(null);
    }

    @Override
    public MCCReference<T> getAsReference() {
        Optional<MCCReference<T>> reference = getAsOptionalReference();
        return reference.orElseThrow(() -> new IllegalStateException("Could not get " + handle + " as reference."));
    }

    @Override
    public Optional<MCCReference<T>> getAsOptionalReference() {
        MCCRegistry<T> registry = MCCPlatform.getInstance().getRegistryStorage().getMinecraftRegistry(registryKey);
        return registry.getReference(key());
    }

    @Override
    public @NotNull Key key() {
        return key;
    }

    @Override
    public Key getRegistryKey() {
        return registryKey;
    }
}
