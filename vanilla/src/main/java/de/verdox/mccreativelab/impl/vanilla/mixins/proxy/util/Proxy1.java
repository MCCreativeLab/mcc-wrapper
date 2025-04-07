package de.verdox.mccreativelab.impl.vanilla.mixins.proxy.util;

import de.verdox.mccreativelab.conversion.ConversionService;

public interface Proxy1 extends ProxyUtil {
    default <V1, C1, VR, CR> VR proxy(
            Function1<V1, VR> vanillaLogic,
            NativeParam<V1, C1> param1,
            Function1<C1, CR> customLogic
    ) {
        ConversionService conversionService = getConversionService();
        if (!isProxy()) return vanillaLogic.apply(param1.param());
        return conversionService.unwrap(conversionService.unwrap(
                customLogic.apply(conversionService.wrap(param1.param(), param1.apiType()))));
    }

    default <V1, C1> void proxy(
            VoidFunction1<V1> vanillaLogic,
            NativeParam<V1, C1> param1,
            VoidFunction1<C1> customLogic
    ) {
        ConversionService conversionService = getConversionService();
        if (!isProxy()) {
            vanillaLogic.apply(param1.param());
            return;
        }
        customLogic.apply(conversionService.wrap(param1.param(), param1.apiType()));
    }

    interface Function1<A, R> {
        R apply(A a);
    }

    interface VoidFunction1<A> {
        void apply(A a);
    }
}
