package de.verdox.mccreativelab.impl.paper.event;

import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import org.bukkit.event.Event;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class BukkitEventContainer<T extends Event> {
    public static <T extends Event> BukkitEventContainer<T> of(T event) {
        return new BukkitEventContainer<>(event);
    }

    private final T event;

    private BukkitEventContainer(T event) {
        this.event = event;
    }

    public <S1, S2> void set(BiConsumer<T, S1> setter, S2 data) {
        setter.accept(event, BukkitAdapter.unwrap(data));
    }

    public <R1, R2> R2 get(Function<T, R1> getter) {
        return BukkitAdapter.unwrap(getter.apply(event));
    }
}
