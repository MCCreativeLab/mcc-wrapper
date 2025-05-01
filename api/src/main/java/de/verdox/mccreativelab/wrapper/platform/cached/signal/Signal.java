package de.verdox.mccreativelab.wrapper.platform.cached.signal;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;
import reactor.core.Disposable;
import reactor.core.publisher.Sinks;

import java.util.HashSet;
import java.util.Set;

public class Signal<T> implements Keyed {
    private final Key key;
    private final Sinks.Many<T> sink;
    final Set<Disposable> cachedDisposables = new HashSet<>();

    public Signal(Key key, Sinks.Many<T> sink) {
        this.key = key;
        this.sink = sink;
    }

    public Sinks.Many<T> getSink() {
        return sink;
    }

    public ObservedSignal<T> asFlux() {
        return new ObservedSignal<>(this, sink.asFlux());
    }

    void disposeAll() {
        for (Disposable cachedDisposable : cachedDisposables) {
            if (cachedDisposable.isDisposed()) {
                continue;
            }
            cachedDisposable.dispose();
        }
    }

    @Override
    public @NotNull Key key() {
        return key;
    }
}
