package de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.util;

import java.util.function.Supplier;

public interface Proxy0 extends ProxyUtil {
    default <R1, R2> R1 proxy(
            Supplier<R1> vanillaLogic,
            Supplier<R2> customLogic
    ) {
        return tryCustomOrVanilla(vanillaLogic, customLogic);
    }

    default void proxy(
            Runnable vanillaLogic,
            Runnable customLogic
    ) {
        tryCustomOrVanilla(vanillaLogic, customLogic);
    }
}