package de.verdox.mccreativelab.wrapper.item.components;

import java.lang.String;
import java.util.Map;

/**
 * Specifies block state properties which get applied to the block after it's placed.
 */
public interface MCCBlockItemStateProperties extends MCCItemComponent {
    public Map<String, String> getProperties();

    public MCCBlockItemStateProperties withProperties(Map<String, String> properties);
}