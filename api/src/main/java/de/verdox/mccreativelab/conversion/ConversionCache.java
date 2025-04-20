package de.verdox.mccreativelab.conversion;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stores converters between native and api types.
 * Every native type is mapped to exactly one api type and vice versa.
 * An api type is always implemented by an impl type. Every api type may have multiple impl types.
 *
 * @param <V> What to map the classes with. Normally we use converters
 */
public class ConversionCache<V> {
    // An api type can have multiple impl types
    private final TypeHierarchyMap<List<Class<?>>> apiToImpls = new TypeHierarchyMap<>();
    // A native type might be referenced by multiple impl types
    private final TypeHierarchyMap<List<Class<?>>> nativeToImpls = new TypeHierarchyMap<>();
    // An impl type is always mapped to one api type
    private final TypeHierarchyMap<Class<?>> implToApi = new TypeHierarchyMap<>();
    // A native type is always mapped to one api type
    private final TypeHierarchyMap<Class<?>> nativeToApi = new TypeHierarchyMap<>();
    // The impl values are mapped to the respective values. Normally we use converters as values
    private final TypeHierarchyMap<V> implToValue = new TypeHierarchyMap<>();
    /**
     * Creates a mapping between an api, an impl, and a native type with an associated value.
     *
     * @param apiType    the api type
     * @param implType   the impl type that implements the api type
     * @param nativeType a native type
     * @param value      the value
     * @param <A>        the generic api type
     * @param <T>        the generic impl type
     * @param <F>        the generic native type
     */
    public <A, T extends A, F> void put(Class<A> apiType, Class<T> implType, Class<F> nativeType, V value) {
        if (nativeToApi.containsKey(nativeType) && !nativeToApi.get(nativeType).equals(apiType)) {
            throw new IllegalArgumentException("The native type " + nativeType + " is already mapped to the api type " + nativeToApi.get(nativeType) + ". You cannot map it to " + apiType);
        } else {
            Class<?> mappedApiTypeOfParentClass = nativeToApi.get(nativeType);
            if (mappedApiTypeOfParentClass != null && !mappedApiTypeOfParentClass.isAssignableFrom(apiType)) {
                throw new IllegalArgumentException("The native type " + nativeType + " has a parent type that is already mapped to the api type " + mappedApiTypeOfParentClass + " which is not an assignable from " + apiType);
            }
        }

        if (implToValue.containsKey(implType)) {
            throw new IllegalArgumentException("The impl type " + implType + " is already mapped to the value " + implToValue.get(implType) + ". However, you want to map it to " + value);
        }

        apiToImpls.computeIfAbsent(apiType, aClass -> new ArrayList<>()).addFirst(implType);
        nativeToImpls.computeIfAbsent(nativeType, aClass -> new ArrayList<>()).addFirst(implType);


        implToApi.put(implType, apiType);

        // We only allow one api type per native type so the wrap function will always produce the right results. Else you will have two api types and the system does not know which one to pick.
        // This is a result of java not knowing generic types at runtime.
        // But we want to have wrap functions in the ConversionService without the need to explicitly declare a type token.
        // A second result is that we cannot map bukkit api types and mcc api types to the same native type in the same conversion cache.
        // We might find a better solution for this in the future.
        nativeToApi.put(nativeType, apiType);


        implToValue.put(implType, value);
    }

    /**
     * Checks if the api type has a mapping in the cache
     *
     * @param apiType the api type
     * @return true if a mapping exists
     */
    public boolean knowsApiType(Class<?> apiType) {
        return apiToImpls.containsKey(apiType);
    }

    /**
     * Checks if the native type has a mapping in the cache
     *
     * @param nativeType the native type
     * @return true if a mapping exists
     */
    public boolean knowsNativeType(Class<?> nativeType) {
        return nativeToImpls.containsKey(nativeType);
    }

    /**
     * Returns a value associated with the provided impl type
     *
     * @param implType the impl type
     * @return the value if a mapping exists or null
     */
    @Nullable
    public V getValue(Class<?> implType) {
        return implToValue.getOrDefault(implType, null);
    }

    /**
     * Returns all values that are mapped to impl types which are mapped to the given native type
     *
     * @param nativeType the native type
     * @return the values
     */
    @NotNull
    public Stream<V> streamAllVariantsForNativeType(Class<?> nativeType) {
        List<Class<?>> foundImplTypes = nativeToImpls.get(nativeType);
        if (foundImplTypes == null) {
            return Stream.of();
        }

        return foundImplTypes.stream().map(implToValue::get);
    }

    /**
     * Returns all values that are mapped to impl types which are mapped to the given api type
     *
     * @param apiType the api type
     * @return the values
     */
    @NotNull
    public Stream<V> streamAllVariantsForApiType(Class<?> apiType) {
        List<Class<?>> foundImplTypes = apiToImpls.get(apiType);
        if (foundImplTypes == null) {
            return Stream.of();
        }
        return foundImplTypes.stream().map(implToValue::get);
    }

    /**
     * Returns the api type that is mapped to the given impl type
     *
     * @param implType the impl type
     * @return the api type
     */
    public Class<?> getApiTypeOfImplType(Class<?> implType) {
        return implToApi.get(implType);
    }

    public TypeHierarchyMap<Class<?>> getImplToApi() {
        return implToApi;
    }

    public TypeHierarchyMap<Class<?>> getNativeToApi() {
        return nativeToApi;
    }

    public TypeHierarchyMap<List<Class<?>>> getApiToImpls() {
        return apiToImpls;
    }

    public TypeHierarchyMap<List<Class<?>>> getNativeToImpls() {
        return nativeToImpls;
    }

    public TypeHierarchyMap<V> getImplToValue() {
        return implToValue;
    }

    @Override
    public String toString() {
        return "ConversionCache{" +
                "apiToImpls=" + apiToImpls +
                ", nativeToImpls=" + nativeToImpls +
                ", implToValue=" + implToValue +
                '}';
    }
}
