package de.verdox.mccreativelab.wrapper.platform.cached.signal;

import reactor.core.Disposable;
import reactor.core.publisher.Sinks;

import java.util.HashSet;
import java.util.Set;

public class Signal<T> {
    private final Sinks.Many<T> sink;
    final Set<Disposable> cachedDisposables = new HashSet<>();

    public Signal(Sinks.Many<T> sink) {
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
}
