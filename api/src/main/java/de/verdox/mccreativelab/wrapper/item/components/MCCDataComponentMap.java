package de.verdox.mccreativelab.wrapper.item.components;

import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public interface MCCDataComponentMap extends Iterable<MCCDataComponentType<?>> {
    /**
     * Edits an item component
     *
     * @param dataComponentType the component type
     * @param editor            the editor
     * @param <R>               the generic data type
     * @param <T>               the component data type
     * @return the same item but with changed components
     */
    <R, T extends MCCDataComponentType<R>> MCCDataComponentMap edit(T dataComponentType, Consumer<MCCDataComponentEditor<R, T>> editor);

    /**
     * Edits an item component and returns the updated value. Can also be used as a getter.
     *
     * @param dataComponentType the component type
     * @param editor            the editor
     * @param <R>               the generic data type
     * @param <T>               the component data type
     */
    <R, T extends MCCDataComponentType<R>> R editAndGet(T dataComponentType, Function<MCCDataComponentEditor<R, T>, R> editor);

    /**
     * Gets the stored value by its data component type.
     *
     * @param dataComponentType the component type
     * @param <R>               the generic data type
     * @param <T>               the component data type
     */
    @Nullable
    <R, T extends MCCDataComponentType<R>> R get(T dataComponentType);

    /**
     * Sets the stored value of a data component type.
     *
     * @param dataComponentType the component type
     * @param <R>               the generic data type
     * @param <T>               the component data type
     */
    <R, T extends MCCDataComponentType<R>> void set(T dataComponentType, @Nullable R value);

    /**
     * Sets the stored value of a data component type.
     *
     * @param typedDataComponentType the typed component type
     * @param <R>               the generic data type
     */
    default <R> void set(MCCTypedDataComponentType<R> typedDataComponentType) {
        set(typedDataComponentType.key(), typedDataComponentType.value());
    }

    /**
     * Checks if the component map contains a particular type
     * @param type the type
     * @return true if exists in the map
     */
    boolean has(MCCDataComponentType<?> type);

    /**
     * Stores all key value pairs from a provided map in this map
     * @param map the other map
     */
    default void putAll(MCCDataComponentMap map) {
        for (MCCTypedDataComponentType<?> mccTypedDataComponentType : map.pairSet()) {
            set(mccTypedDataComponentType);
        }
    }

    /**
     * Returns the amount of data components saved in this map
     * @return the amount
     */
    int size();

    /**
     * Returns whether this component map is empty
     * @return true if it is empty
     */
    boolean isEmpty();

    /**
     * Returns the key set of data components in this map
     * @return the key set
     */
    Set<MCCDataComponentType<?>> keySet();

    /**
     * Returns a set consisting of key value pairs in this map
     * @return the set
     */
    Set<MCCTypedDataComponentType<?>> pairSet();
}
