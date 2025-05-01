package de.verdox.mccreativelab.wrapper.platform.cached.signal;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.util.GCHookMap;
import net.kyori.adventure.key.Key;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class SignalCache {
    public static final SignalCache INSTANCE = new SignalCache();

    GCHookMap<Object, Map<Key, Signal<?>>> eventSinks = new GCHookMap<>();

    SignalCache() {}

    public <NATIVE_HANDLE, VALUE> Signal<VALUE> createFlux(Key key, NATIVE_HANDLE nativeHandle, Supplier<Sinks.Many<VALUE>> sinkCreator) {
        Map<Key, Signal<?>> cache = eventSinks.getOrPut(nativeHandle, new ConcurrentHashMap<>(), values -> {
            for (Signal<?> value : values.values()) {
                value.disposeAll();
            }
        });
        if(cache.containsKey(key)) {
            return (Signal<VALUE>) cache.get(key);
        }
        var signal = new Signal<>(sinkCreator.get());
        cache.put(key, signal);
        return signal;
    }

    public <NATIVE_HANDLE, VALUE> Signal<VALUE> createFlux(Key key, NATIVE_HANDLE nativeHandle) {
        return createFlux(key, nativeHandle, () -> Sinks.many().multicast().directBestEffort());
    }

    public <NATIVE_HANDLE, VALUE> Signal<VALUE> createFlux(Key key, NATIVE_HANDLE nativeHandle, TypeToken<VALUE> type) {
        return createFlux(key, nativeHandle, () -> Sinks.many().multicast().directBestEffort());
    }
}
