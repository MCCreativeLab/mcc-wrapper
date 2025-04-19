package de.verdox.mccreativelab.impl.vanilla.mixins.proxy.util;

import de.verdox.mccreativelab.conversion.ConversionService;

import java.util.function.Supplier;

public interface Proxy0 extends ProxyUtil {
    default <R1, R2> R1 proxy(
            Supplier<R1> vanillaLogic,
            Supplier<R2> customLogic
    ) {
        ConversionService conversionService = getConversionService();
        if (!isProxy()) {
            return vanillaLogic.get();
        }
        return conversionService.unwrap(conversionService.unwrap(customLogic.get()));
    }

    default void proxy(
            Runnable vanillaLogic,
            Runnable customLogic
    ) {
        ConversionService conversionService = getConversionService();
        if (!isProxy()) {
            vanillaLogic.run();
            return;
        }
        customLogic.run();
    }
}