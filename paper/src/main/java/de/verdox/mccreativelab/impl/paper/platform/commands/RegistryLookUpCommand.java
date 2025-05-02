package de.verdox.mccreativelab.impl.paper.platform.commands;

import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.registry.OpenRegistry;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class RegistryLookUpCommand<T> extends AbstractRegistryLookUpCommand<T, OpenRegistry<T>> {

    public RegistryLookUpCommand(@NotNull String name, String subCommand, OpenRegistry<T> registry, BiConsumer<MCCPlayer, T> consumeEntry) {
        super(name, subCommand, registry, consumeEntry);
    }

    public RegistryLookUpCommand(@NotNull String name, OpenRegistry<T> registry, BiConsumer<MCCPlayer, T> consumeEntry) {
        super(name, registry, consumeEntry);
    }

    @Override
    protected Stream<String> streamRegistryKeys() {
        return registry.keySet().stream().map(Key::asString);
    }

    @Override
    protected boolean contains(String key) {
        return registry.containsKey(Key.key(key));
    }

    @Override
    protected T getValueFromRegistry(String key) {
        return registry.get(Key.key(key));
    }
}
