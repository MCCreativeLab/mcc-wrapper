package de.verdox.mccreativelab.wrapper.platform;

import java.util.logging.Logger;

/**
 * Used to trigger certain lifecycle events in the platform startup process
 */
public interface MCCLifecycleTrigger {
    Logger LOGGER = Logger.getLogger(MCCLifecycleTrigger.class.getSimpleName());

    void bootstrap();

    void beforeWorldLoad();

    void afterWorldLoad();

    void onServerStartupComplete();
}
