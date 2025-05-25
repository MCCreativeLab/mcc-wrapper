package de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.util;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.GameProxy;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface ProxyUtil {
    Logger LOGGER = Logger.getLogger(ProxyUtil.class.getName());

    default <F, T> GameProxy.NativeParam<F, T> param(F nativeObject, TypeToken<T> apiType) {
        return new GameProxy.NativeParam<>(nativeObject, apiType);
    }

    default <F, T> GameProxy.NativeParam<F, T> param(F nativeObject, Class<T> apiType) {
        return new GameProxy.NativeParam<>(nativeObject, TypeToken.of(apiType));
    }

    default <T> GameProxy.NativeParam<T, T> param(T nativeObject) {
        return (GameProxy.NativeParam<T, T>) new GameProxy.NativeParam<>(nativeObject, TypeToken.of(nativeObject.getClass()));
    }

    boolean isProxy();

    default ConversionService getConversionService() {
        return MCCPlatform.getInstance().getConversionService();
    }

    record NativeParam<F, T>(F param, TypeToken<T> apiType) {
        T asApi() {
            return MCCPlatform.getInstance().getConversionService().wrap(param, apiType);
        }
    }

    default <VANILLA, MCC> VANILLA tryCustomOrVanilla(Supplier<VANILLA> vanillaLogic, Supplier<MCC> customLogic) {
        if (!isProxy()) {
            return vanillaLogic.get();
        }
        try {
            MCC customLogicResult = customLogic.get();
            return getConversionService().unwrap(customLogicResult);
        } catch (Throwable throwable) {
            LOGGER.log(Level.SEVERE, "There was an error while trying to rum custom logic for a proxy.", throwable);
            return vanillaLogic.get();
        }
    }

    default void tryCustomOrVanilla(Runnable vanillaLogic, Runnable customLogic) {
        if (!isProxy()) {
            vanillaLogic.run();
            return;
        }
        try {
            customLogic.run();
        } catch (Throwable throwable) {
            LOGGER.log(Level.SEVERE, "There was an error while trying to rum custom logic for a proxy.", throwable);
            vanillaLogic.run();
        }
    }
}
