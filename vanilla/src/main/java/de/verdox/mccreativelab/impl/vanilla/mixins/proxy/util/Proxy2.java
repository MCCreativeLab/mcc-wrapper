package de.verdox.mccreativelab.impl.vanilla.mixins.proxy.util;

import de.verdox.mccreativelab.conversion.ConversionService;

public interface Proxy2 extends ProxyUtil {
    default <
            V1, C1,
            V2, C2,
            VR, CR
            >
    VR proxy(
            Function2<V1, V2, VR> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            Function2<C1, C2, CR> customLogic
    ) {
        ConversionService conversionService = getConversionService();
        if (!isProxy()) {
            return vanillaLogic.apply(
                    param1.param(),
                    param2.param()
            );
        }
        return conversionService.unwrap(conversionService.unwrap(
                customLogic.apply(
                        conversionService.wrap(param1.param(), param1.apiType()),
                        conversionService.wrap(param2.param(), param2.apiType())
                )));
    }


    default <V1, C1, V2, C2> void proxy(
            VoidFunction2<V1, V2> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            VoidFunction2<C1, C2> customLogic
    ) {
        ConversionService conversionService = getConversionService();
        if (!isProxy()) {
            vanillaLogic.apply(
                    param1.param(),
                    param2.param()
            );
            return;
        }
        customLogic.apply(
                conversionService.wrap(param1.param(), param1.apiType()),
                conversionService.wrap(param2.param(), param2.apiType()));
    }

    @FunctionalInterface
    interface Function2<A, B, R> {
        R apply(A a, B b);
    }

    interface VoidFunction2<A, B> {
        void apply(A a, B b);
    }
}
