package de.verdox.mccreativelab.impl.vanilla.mixins.proxy.util;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.impl.vanilla.mixins.proxy.GameProxy;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

public interface ProxyUtil {
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

    record NativeParam<F, T>(F param, TypeToken<T> apiType) {}
}
