package de.verdox.mccreativelab.wrapper.platform.factory;

import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;

/**
 * Used to create minecraft objects
 */
public interface MCCElementFactory {
    /**
     * Creates an empty data component map
     * @return an empty data component map
     */
    MCCDataComponentMap createEmptyDataComponentMap();
}
