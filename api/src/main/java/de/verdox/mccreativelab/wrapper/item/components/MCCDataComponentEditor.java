package de.verdox.mccreativelab.wrapper.item.components;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Used to edit an item component
 *
 * @param <R> the generic type of the component
 * @param <T> the component type
 */
public interface MCCDataComponentEditor<R, T extends MCCDataComponentType<R>> {

    /**
     * Reads the value from the component
     *
     * @return the component value
     */
    @Nullable R get();

    /**
     * Sets the value in the component
     *
     * @param data the new value
     */
    void set(@Nullable R data);

    /**
     * Creates a new value
     *
     * @return the new value
     */
    @NotNull R create();

    /**
     * Creates a new component data and applies some changes to it and returns the object or a new one.
     * @param function the change function
     */
    default void with(@NotNull Function<R, R> function) {
        Objects.requireNonNull(function);
        R component = create();
        set(function.apply(component));
    }

    /**
     * Gets the {@link MCCDataComponentType}
     * @return the type
     */
    T type();
}
