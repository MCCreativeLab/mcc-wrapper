package de.verdox.mccreativelab.custom.block.properties;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface MCCBlockStatePropertyFactory {

    /**
     * Create a new EnumProperty with all Enum constants of the given class.
     */
    default  <T extends Enum<T>> MCCBlockStateProperty<T> create(String name, Class<T> clazz) {
        return create(name, clazz, enum_ -> true);
    }

    /**
     * Create a new EnumProperty with all Enum constants of the given class that match the given Predicate.
     */
    default <T extends Enum<T>> MCCBlockStateProperty<T> create(String name, Class<T> clazz, Predicate<T> filter) {
        return createEnum(name, (List<T>) Arrays.stream((Enum[])clazz.getEnumConstants()).filter(anEnum -> filter.test((T) anEnum)).collect(Collectors.toList()));
    }

    /**
     * Create a new EnumProperty with the specified values
     */
    default <T extends Enum<T>> MCCBlockStateProperty<T> createEnum(String name, T... enumValues) {
        return createEnum(name, List.of(enumValues));
    }

    <T extends Enum<T>> MCCBlockStateProperty<T> createEnum(String name, List<T> enumValues);

    /**
     * Creates a boolean property
     * @param name the name of the property
     * @return the new property
     */
    MCCBlockStateProperty<Boolean> createBoolean(String name);

    /**
     * Creates a new integer property
     * @param name the name of the property
     * @param min the min value of the property
     * @param max the max value of the property
     * @return the new property
     */
    MCCBlockStateProperty<Integer> createInteger(String name, int min, int max);

}
