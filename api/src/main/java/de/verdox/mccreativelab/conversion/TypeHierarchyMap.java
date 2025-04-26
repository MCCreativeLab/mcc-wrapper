package de.verdox.mccreativelab.conversion;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * This hierarchy map is used to group values by provided class types.
 * However, if you put a new type into the map that extends an already existing class then the new value associated with it is inserted in the tree structure.
 * Now when retrieving a value from the map with a provided type it will search for the type that fits best and return the associated result.
 *
 * @param <V>
 */
public class TypeHierarchyMap<V> implements Map<Class<?>, V> {
    private final HierarchyNode<V> root = new HierarchyNode<>(Object.class, null, null); // null as value is only allowed for the root node

    @Override
    public int size() {
        return root.size();
    }

    @Override
    public boolean isEmpty() {
        return root.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return root.containsKey(key);
    }

    /**
     * Is used to check whether a class is either contained as a direct key in the map or a superclass of the type is found.
     *
     * @param key the type
     * @return true if a relative was found
     */
    public boolean canFindRelative(Class<?> key) {
        return root.canFindRelative(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return root.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return root.get(key);
    }

    @Override
    public @Nullable V put(Class<?> key, V value) {
        return root.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return root.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends Class<?>, ? extends V> m) {
        root.putAll(m);
    }

    @Override
    public void clear() {
        root.clear();
    }

    @Override
    public @NotNull Set<Class<?>> keySet() {
        return root.keySet();
    }

    @Override
    public @NotNull Collection<V> values() {
        return root.values();
    }

    @Override
    public @NotNull Set<Entry<Class<?>, V>> entrySet() {
        return root.entrySet();
    }

    @Override
    public String toString() {
        return "TypeHierarchyMap{" +
            "root=" + root +
            '}';
    }

    private static class HierarchyNode<V> implements Map<Class<?>, V> {
        private final Class<?> typeClass;
        private V value;

        private HierarchyNode<V> parent;
        private final Map<Class<?>, HierarchyNode<V>> childTypes = new ConcurrentHashMap<>();

        private final Map<Class<?>, HierarchyNode<V>> lookupCache = new ConcurrentHashMap<>();
        private final Map<HierarchyNode<V>, Set<Class<?>>> nodesToLookupClasses = new ConcurrentHashMap<>();

        private HierarchyNode(Class<?> typeClass, V value, HierarchyNode<V> parent) {
            Objects.requireNonNull(typeClass);
            if (!typeClass.equals(Object.class) && value == null) {
                throw new IllegalArgumentException("Value of hierarchy node cannot be null");
            }
            this.typeClass = typeClass;
            this.value = value;
            this.parent = parent;
        }


        @Override
        public int size() {
            return childTypes.values().stream().mapToInt(HierarchyNode::size).sum() + (value != null ? 1 : 0);
        }

        @Override
        public boolean isEmpty() {
            return value == null && childTypes.isEmpty();
        }

        public boolean canFindRelative(Class<?> clazz) {
            Objects.requireNonNull(clazz);
            if (isEmpty()) {
                return false;
            }

            if (!this.typeClass.equals(Object.class) && this.typeClass.isAssignableFrom(clazz)) {
                return true;
            }

            if (childTypes.containsKey(clazz)) {
                return true;
            }

            return childTypes.keySet().stream().filter(aClass -> aClass.isAssignableFrom(clazz)).map(childTypes::get).anyMatch(node -> node.canFindRelative(clazz));
        }

        @Override
        public boolean containsKey(Object key) {
            Objects.requireNonNull(key);
            if (isEmpty() || !(key instanceof Class<?> clazz)) {
                return false;
            }

            if (this.typeClass.equals(clazz)) {
                return true;
            }

            if (childTypes.containsKey(clazz)) {
                return true;
            }

            return childTypes.keySet().stream().filter(aClass -> aClass.isAssignableFrom(clazz)).map(childTypes::get).anyMatch(node -> node.containsKey(clazz));
        }

        @Override
        public boolean containsValue(Object value) {
            Objects.requireNonNull(value);
            return childTypes.containsValue(value) || childTypes.values().stream().anyMatch(node -> node.containsValue(value));
        }

        @Override
        public V get(Object key) {
            Objects.requireNonNull(key);
            if (isEmpty() || !(key instanceof Class<?> clazz)) {
                return null;
            }


            // Check if the provided key directly belongs to this node
            if (this.typeClass.equals(clazz)) {
                return this.value;
            }

            // Check if there is a direct child mapping
            if (childTypes.containsKey(clazz)) {
                return childTypes.get(clazz).value;
            }

            // Has an indirect child mapping ever been found? If yes we return it here to have less computations
            HierarchyNode<V> cached = lookupCache.get(clazz);
            if (cached != null) {
                // Prevent stack overflow.
                if (cached.equals(this)) {
                    return cached.value;
                }
                return cached.get(clazz);
            }

            // No direct child mapping was cached. We need to search for it now.
            Class<?> foundAssignable = childTypes.keySet().stream()
                    .filter(aClass -> aClass.isAssignableFrom(clazz))
                    .findAny().orElse(null);

            // If we have found one -> save it to our mapping cache
            if (foundAssignable != null) {
                HierarchyNode<V> resolvedNode = childTypes.get(foundAssignable);
                cacheLookup(clazz, resolvedNode);
                return resolvedNode.get(clazz);
            }

            // No child was found. As a consequence this node is the best we got
            if (this.typeClass.isAssignableFrom(clazz)) {
                cacheLookup(clazz, this);
                return this.value;
            }

            return null;
        }

        @Override
        public @Nullable V put(Class<?> key, V value) {
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);
            if (key.equals(Object.class)) {
                throw new IllegalArgumentException("Cannot use Object.class as a key");
            }

            if (this.typeClass.equals(key)) {
                V oldValue = this.value;
                this.value = value;
                return oldValue;
            }

            if (childTypes.containsKey(key)) {
                return childTypes.get(key).put(key, value);
            }

            Class<?> foundAssignable = childTypes.keySet().stream().filter(aClass -> aClass.isAssignableFrom(key)).findAny().orElse(null);
            if (foundAssignable != null) {
                return childTypes.get(foundAssignable).put(key, value);
            }

            try {
                return putAndReorganize(key, value);
            } finally {
                // nach erfolgreichem Hinzufügen einer neuen Node
                clearAffectedCacheEntriesAfterInsert(key);
            }
        }


        @Override
        public V remove(Object key) {
            Objects.requireNonNull(key);
            if (key.equals(Object.class)) {
                throw new IllegalArgumentException("Cannot remove Object.class from the hierarchy");
            }

            if (isEmpty() || !(key instanceof Class<?> clazz)) {
                return null;
            }


            if (childTypes.containsKey(clazz)) {
                HierarchyNode<V> removed = childTypes.remove(clazz);
                clearCacheUpwards(removed);
                return removed.value;
            }

            Class<?> foundAssignable = childTypes.keySet().stream().filter(aClass -> aClass.isAssignableFrom(clazz)).findAny().orElse(null);
            if (foundAssignable != null) {
                return childTypes.get(foundAssignable).remove(key);
            }

            return null;
        }

        @Override
        public void putAll(@NotNull Map<? extends Class<?>, ? extends V> m) {
            m.entrySet().stream().forEach(entry -> put(entry.getKey(), entry.getValue()));
        }

        @Override
        public void clear() {
            childTypes.clear();
            nodesToLookupClasses.clear();
            lookupCache.clear();
        }

        @Override
        public @NotNull Set<Class<?>> keySet() {
            Set<Class<?>> result = new HashSet<>();
            if (this.value != null) {
                result.add(this.typeClass);
            }
            childTypes.values().stream().forEach(vHierarchyNode -> result.addAll(vHierarchyNode.keySet()));
            return result;
        }

        @Override
        public @NotNull Collection<V> values() {
            List<V> result = new ArrayList<>(32);
            if (this.value != null) {
                result.add(this.value);
            }
            childTypes.values().stream().forEach(vHierarchyNode -> result.addAll(vHierarchyNode.values()));
            return result;
        }

        @Override
        public @NotNull Set<Entry<Class<?>, V>> entrySet() {
            Map<Class<?>, V> result = new ConcurrentHashMap<>();
            if (this.value != null) {
                result.put(this.typeClass, this.value);
            }

            childTypes.values().stream().forEach(result::putAll);
            return result.entrySet();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HierarchyNode<?> that = (HierarchyNode<?>) o;
            return Objects.equals(typeClass, that.typeClass) && Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(typeClass, value);
        }

        @Override
        public String toString() {
            return "HierarchyNode{" +
                    "type=" + typeClass +
                    ", value=" + value +
                    ", childTypes=" + childTypes +
                    '}';
        }

        private void cacheLookup(Class<?> classToLookFor, HierarchyNode<V> foundNode) {
            lookupCache.put(classToLookFor, foundNode);
            nodesToLookupClasses.computeIfAbsent(foundNode, vHierarchyNode -> new HashSet<>()).add(classToLookFor);
        }

        private void clearCache(HierarchyNode<V> node) {
            Set<Class<?>> keys = nodesToLookupClasses.remove(node);
            if (keys != null) {
                keys.forEach(lookupCache::remove);
            }
        }

        private void clearCacheUpwards(HierarchyNode<V> node) {
            HierarchyNode<V> current = node;
            while (current != null) {
                current.clearCache(node);
                current = current.parent;
            }
        }

        private void clearAffectedCacheEntriesAfterInsert(Class<?> newlyAddedClass) {
            // A new class key was added into the hierarchy. Invalidate all caches that are related to this class since the new class might be a better fit
            for (Map.Entry<Class<?>, HierarchyNode<V>> entry : lookupCache.entrySet()) {
                Class<?> lookupClass = entry.getKey();
                HierarchyNode<V> resolvedNode = entry.getValue();

                if (newlyAddedClass.isAssignableFrom(lookupClass)) {
                    // The new class might be a better fit
                    clearCacheUpwards(resolvedNode);
                }
            }
        }

        private @Nullable V putAndReorganize(Class<?> key, V value) {
            Map<Class<?>, HierarchyNode<V>> toReOrganize = childTypes.entrySet().stream().filter(pair -> key.isAssignableFrom(pair.getKey())).collect(Collectors.toMap(Entry::getKey, Entry::getValue));

            toReOrganize.keySet().forEach(childTypes::remove);
            toReOrganize.values().forEach(this::clearCacheUpwards);

            // Add new node
            HierarchyNode<V> newNode = new HierarchyNode<>(key, value, this);

            if (!toReOrganize.isEmpty()) {
                toReOrganize.forEach((aClass, vHierarchyNode) -> {
                    vHierarchyNode.parent = newNode;
                    newNode.childTypes.put(aClass, vHierarchyNode);
                });
                newNode.childTypes.putAll(toReOrganize);
            }

            HierarchyNode<V> previous = childTypes.put(key, newNode);
            return previous != null ? previous.value : null;
        }
    }
}
