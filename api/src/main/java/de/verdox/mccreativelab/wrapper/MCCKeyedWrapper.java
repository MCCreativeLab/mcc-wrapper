package de.verdox.mccreativelab.wrapper;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;

/**
 * A wrapper that holds a native platform
 */
public interface MCCKeyedWrapper extends Keyed, MCCWrapped {
    Key getRegistryKey();
}
