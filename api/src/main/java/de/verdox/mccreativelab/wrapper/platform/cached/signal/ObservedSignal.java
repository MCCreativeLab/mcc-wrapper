package de.verdox.mccreativelab.wrapper.platform.cached.signal;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Function;

public class ObservedSignal<T> implements Keyed {
    private final Signal<T> parent;
    private final Flux<T> flux;

    public ObservedSignal(Signal<T> parent, Flux<T> flux) {
        this.parent = parent;
        this.flux = flux;
    }

    public ObservedSignal<T> flux(Function<Flux<T>, Flux<T>> map) {
        return new ObservedSignal<>(parent, map.apply(flux));
    }

    public Sinks.Many<T> sink() {
        return parent.getSink();
    }

    public Disposable subscribe(Consumer<? super T> consume) {
        Disposable disposable = flux.subscribe(consume);
        parent.cachedDisposables.add(disposable);
        return disposable;
    }

    @Override
    public @NotNull Key key() {
        return parent.key();
    }
}
