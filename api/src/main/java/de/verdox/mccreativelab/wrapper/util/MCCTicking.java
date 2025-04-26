package de.verdox.mccreativelab.wrapper.util;

import reactor.core.publisher.Flux;

/**
 * Indicates that this element receives a tick
 */
public interface MCCTicking {
    /**
     * Can be subscribed to
     */
    Flux<Long> tickSignal();
}
