package de.verdox.mccreativelab.wrapper.platform.factory;

import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.item.components.MCCItemComponent;

/**
 * Used to create minecraft objects
 */
public interface MCCElementFactory {
    /**
     * Creates an empty data component map
     * @return an empty data component map
     */
    MCCDataComponentMap createEmptyDataComponentMap();

    /**
     * Creates an empty component of a provided data class
     * @param componentClass the data class
     * @return the empty component
     * @param <T> the generic component type
     */
    <T extends MCCItemComponent> T createEmptyComponent(Class<T> componentClass);
}
