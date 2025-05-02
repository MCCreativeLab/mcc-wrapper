package de.verdox.mccreativelab.wrapper.util;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.platform.cached.signal.ObservedSignal;
import net.kyori.adventure.key.Key;

/**
 * Indicates that this element receives a tick
 */
public interface MCCTicking {
    /**
     * Can be subscribed to
     */
    default ObservedSignal<Long> tickSignal() {
        return MCCPlatform.getInstance().createSignal(Key.key("minecraft", "tick"), this, new TypeToken<Long>() {}).asFlux();
    }
}
