package de.verdox.mccreativelab.wrapper.platform.cached.signal;

import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Sinks;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class SignalCacheTest {

    @Test
    void testSameSignalReturnedForSameKeyAndHandle() {
        SignalCache cache = new SignalCache();
        Object handle = new Object();
        Key key = Key.key("test", "test");

        Signal<String> s1 = cache.createFlux(key, handle, () -> Sinks.many().unicast().onBackpressureBuffer());
        Signal<String> s2 = cache.createFlux(key, handle, () -> Sinks.many().unicast().onBackpressureBuffer());

        assertSame(s1, s2);
    }

    @Test
    void testDifferentSignalForDifferentKeyOrHandle() {
        SignalCache cache = new SignalCache();
        Object handle1 = new Object();
        Object handle2 = new Object();
        Key key1 = Key.key("test", "test1");
        Key key2 = Key.key("test", "test2");

        Signal<String> s1 = cache.createFlux(key1, handle1, () -> Sinks.many().unicast().onBackpressureBuffer());
        Signal<String> s2 = cache.createFlux(key2, handle1, () -> Sinks.many().unicast().onBackpressureBuffer());
        Signal<String> s3 = cache.createFlux(key1, handle2, () -> Sinks.many().unicast().onBackpressureBuffer());

        assertNotSame(s1, s2);
        assertNotSame(s1, s3);
        assertNotSame(s2, s3);
    }

    @Test
    void testDisposablesAreDisposedOnGC() throws Exception {
        SignalCache cache = new SignalCache();
        AtomicBoolean disposed = new AtomicBoolean(false);

        Object handle = new Object();
        Key key = Key.key("test", "test");

        Signal<String> signal = new Signal<>(key, Sinks.many().unicast().onBackpressureBuffer());
        signal.cachedDisposables.add(() -> disposed.set(true)); // mock disposable

        // Manuell in die Map eintragen, um GC zu simulieren
        Map<Key, Signal<?>> map = new ConcurrentHashMap<>();
        map.put(key, signal);

        WeakReference<Object> weakHandle = new WeakReference<>(handle);
        cache.eventSinks.put(handle, map, values -> values.values().forEach(Signal::disposeAll));

        handle = null;
        for (int i = 0; i < 10 && weakHandle.get() != null; i++) {
            System.gc();
            Thread.sleep(100);
        }

        // Cleanup manuell triggern
        cache.eventSinks.cleanup();

        assertTrue(disposed.get(), "Disposable should be disposed after GC");
    }

    @Test
    void testSubscribeStoresDisposable() {
        Signal<String> signal = new Signal<>(Key.key("minecraft:test"), Sinks.many().multicast().onBackpressureBuffer());
        ObservedSignal<String> observed = signal.asFlux();

        AtomicInteger counter = new AtomicInteger();
        observed.subscribe(val -> counter.incrementAndGet());

        assertEquals(1, signal.cachedDisposables.size());
        signal.getSink().tryEmitNext("test");
        assertEquals(1, counter.get());
    }

    @Test
    void testDisposeAllDisposesSubscriptions() {
        Signal<String> signal = new Signal<>(Key.key("minecraft:test"), Sinks.many().multicast().onBackpressureBuffer());
        AtomicBoolean disposed = new AtomicBoolean(false);

        Disposable disposable = () -> disposed.set(true);
        signal.cachedDisposables.add(disposable);

        signal.disposeAll();
        assertTrue(disposed.get(), "Disposable should have been disposed");
    }
}
