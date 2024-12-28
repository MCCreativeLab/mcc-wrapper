package de.verdox.mccreativelab.impl.vanilla.platform.factory;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.impl.vanilla.registry.NMSRegistry;
import de.verdox.mccreativelab.impl.vanilla.registry.NMSTag;
import de.verdox.mccreativelab.impl.vanilla.registry.NMSTypedKey;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.platform.factory.TypedKeyFactory;
import de.verdox.mccreativelab.wrapper.registry.*;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class NMSTypedKeyFactory implements TypedKeyFactory {
    @Override
    public <T> MCCTypedKey<T> getKey(Key key, Key registryKey, TypeToken<T> type) {
        return new NMSTypedKey<>(key, registryKey);
    }

    @Override
    public <T> MCCTypedKey<T> getKey(Key key, Key registryKey) {
        return new NMSTypedKey<>(key, registryKey);
    }

    @Override
    public <T> MCCTag<T> createTag(Key key, Key registryKey, TypeToken<T> type) {
        return new NMSTag<>(key, registryKey);
    }

    @Override
    public <T> MCCTag<T> createTag(Key key, Key registryKey) {
        return new NMSTag<>(key, registryKey);
    }

    @Override
    public <T> MCCReferenceSet<T> createImmutableSetWithoutKey(List<MCCReference<T>> references) {
        List<? extends Holder<?>> list = MCCPlatform.getInstance().getConversionService().unwrap(references, new TypeToken<>() {});
        HolderSet<?> holders = HolderSet.direct(list.toArray(Holder[]::new));
        return MCCPlatform.getInstance().getConversionService().wrap(holders, new TypeToken<>() {});
    }

    @Override
    public <T> MCCTag<T> createUnboundTag(Key key, Collection<T> values) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(values);
        return new MCCTag<>() {
            private final MCCReferenceSet<T> set = MCCPlatform.getInstance().getConversionService().wrap(HolderSet.direct(values.stream().map(Holder::direct).toArray(Holder[]::new)));

            @Override
            public boolean isFor(MCCTypedKey<? extends MCCRegistry<?>> registryKey) {
                return false;
            }

            @Override
            public MCCTypedKey<? extends MCCRegistry<T>> getRegistryKey() {
                throw new UnsupportedOperationException("Unbound MCCTags are not bound to a registry");
            }

            @Override
            public MCCRegistry<T> getRegistry() {
                throw new UnsupportedOperationException("Unbound MCCTags are not bound to a registry");
            }

            @Override
            public @NotNull Key key() {
                return key;
            }

            @Override
            public boolean contains(T value) {
                return values.contains(value);
            }

            @Override
            public MCCReferenceSet<T> getValues() {
                return set;
            }
        };
    }
}
