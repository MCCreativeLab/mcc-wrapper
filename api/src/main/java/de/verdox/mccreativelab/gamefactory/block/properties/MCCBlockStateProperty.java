package de.verdox.mccreativelab.gamefactory.block.properties;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

import java.util.stream.Stream;

/**
 * Represents a block state property
 */
public interface MCCBlockStateProperty<T extends Comparable<T>> {

    static MCCBlockStatePropertyFactory factory() {
        return MCCPlatform.getInstance().getGameFactory().getBlockStatePropertyFactory();
    }

    /**
     * Returns the name of the property
     * @return the name of the property
     */
    String getName();

    /**
     * Wraps to a value
     * @param value
     * @return
     */
    Value<T> value(T value);

    /**
     * Streams all possible values
     * @return the values
     */
    Stream<T> getPossibleValues();

    /**
     * @return the name for the given value
     */
    String getName(T value);

    /**
     * Returns the internal index of a provided value
     * @param value the provided value
     * @return the index
     */
    int getInternalIndex(T value);

    interface Value<T extends Comparable<T>> {
        MCCBlockStateProperty<T> property();

        T value();
    }
}
