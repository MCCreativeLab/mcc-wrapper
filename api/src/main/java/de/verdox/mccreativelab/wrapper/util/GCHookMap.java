package de.verdox.mccreativelab.wrapper.util;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GCHookMap<K, V> {

    private final Map<WeakKey<K>, Value<V>> internalMap = new ConcurrentHashMap<>();
    private final ReferenceQueue<K> referenceQueue = new ReferenceQueue<>();

    public V getOrPut(K key, V putValue, GCHook<V> putHook) {
        if (!containsKey(key)) {
            put(key, putValue, putHook);
            return putValue;
        }
        return get(key);
    }

    public V getOrPut(K key, V putValue) {
        if (!containsKey(key)) {
            put(key, putValue);
            return putValue;
        }
        return get(key);
    }

    public void put(K key, V value, GCHook<V> hook) {
        cleanup(); // prüft, ob Einträge entfernt wurden
        WeakKey<K> weakKey = new WeakKey<>(key, referenceQueue);
        internalMap.put(weakKey, new Value<>(value, hook));
    }

    public void put(K key, V value) {
        cleanup(); // prüft, ob Einträge entfernt wurden
        WeakKey<K> weakKey = new WeakKey<>(key, referenceQueue);
        internalMap.put(weakKey, new Value<>(value, null));
    }

    public boolean containsKey(K key) {
        cleanup(); // optional – je nach gewünschtem Verhalten
        return internalMap.containsKey(new WeakKey<>(key));
    }

    public V get(K key) {
        cleanup();
        return internalMap.get(new WeakKey<>(key)).value();
    }

    public void cleanup() {
        WeakKey<?> collectedKey;
        while ((collectedKey = (WeakKey<?>) referenceQueue.poll()) != null) {
            internalMap.remove(collectedKey).cleanup();
        }
    }

    private static class WeakKey<K> extends WeakReference<K> {
        private final int identityHashCode;

        public WeakKey(K referent, ReferenceQueue<K> queue) {
            super(referent, queue);
            this.identityHashCode = System.identityHashCode(referent);
        }

        public WeakKey(K referent) {
            super(referent);
            this.identityHashCode = System.identityHashCode(referent);
        }

        @Override
        public int hashCode() {
            return identityHashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof WeakKey<?> other)) return false;
            return this.get() == other.get();
        }
    }

    private record Value<V>(V value, @Nullable GCHook<V> hook) {
        private void cleanup() {
            if (hook() == null) {
                return;
            }
            hook().onCleanup(value);
        }
    }

    @FunctionalInterface
    public interface GCHook<V> {
        void onCleanup(V value);
    }
}