package de.verdox.mccreativelab.conversion;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ConversionCache<V> {
    private final TypeHierarchyMap<List<Class<?>>> apiToImpls = new TypeHierarchyMap<>();
    private final TypeHierarchyMap<List<Class<?>>> nativeToImpls = new TypeHierarchyMap<>();
    private final TypeHierarchyMap<Class<?>> implToApi = new TypeHierarchyMap<>();

    private final TypeHierarchyMap<Class<?>> nativeToApi = new TypeHierarchyMap<>();

    private final TypeHierarchyMap<V> implToValue = new TypeHierarchyMap<>();

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

        apiToImpls.computeIfAbsent(apiType, aClass -> new LinkedList<>()).addFirst(implType);
        nativeToImpls.computeIfAbsent(nativeType, aClass -> new LinkedList<>()).addFirst(implType);


        implToApi.put(implType, apiType);
        // We only allow one api type per native type so the wrap function will always produce the right results. Else you will have two api types and the system does not know which one to pick
        nativeToApi.put(nativeType, apiType);


        implToValue.put(implType, value);
    }

    public boolean knowsApiType(Class<?> apiType) {
        return apiToImpls.containsKey(apiType);
    }

    public boolean knowsNativeType(Class<?> nativeType) {
        return nativeToImpls.containsKey(nativeType);
    }

    @Nullable
    public V getValue(Class<?> implType) {
        return implToValue.getOrDefault(implType, null);
    }

    @NotNull
    public Stream<V> getAllVariantsForNativeType(Class<?> nativeType) {
        List<Class<?>> foundImplTypes = nativeToImpls.get(nativeType);
        if (foundImplTypes == null) {
            return Stream.of();
        }

        return foundImplTypes.stream().map(implToValue::get);
    }

    @NotNull
    public Stream<V> getAllVariantsForApiType(Class<?> apiType) {
        List<Class<?>> foundImplTypes = apiToImpls.get(apiType);
        if (foundImplTypes == null) {
            return Stream.of();
        }
        return foundImplTypes.stream().map(implToValue::get);
    }

    public Class<?> getApiTypeOfImplType(Class<?> implType) {
        return implToApi.get(implType);
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
