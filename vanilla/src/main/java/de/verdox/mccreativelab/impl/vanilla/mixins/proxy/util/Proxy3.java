package de.verdox.mccreativelab.impl.vanilla.mixins.proxy.util;

import de.verdox.mccreativelab.conversion.ConversionService;

public interface Proxy3 extends ProxyUtil {
    default <
            V1, C1,
            V2, C2,
            V3, C3,
            VR, CR
            >
    VR proxy(
            Function3<V1, V2, V3, VR> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            Function3<C1, C2, C3, CR> customLogic
    ) {
        ConversionService conversionService = getConversionService();
        if (!isProxy()) {
            return vanillaLogic.apply(
                    param1.param(),
                    param2.param(),
                    param3.param()
            );
        }
        return conversionService.unwrap(conversionService.unwrap(
                customLogic.apply(
                        conversionService.wrap(param1.param(), param1.apiType()),
                        conversionService.wrap(param2.param(), param2.apiType()),
                        conversionService.wrap(param3.param(), param3.apiType())
                )));
    }

    default <V1, C1, V2, C2, V3, C3> void proxy(
            VoidFunction3<V1, V2, V3> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            VoidFunction3<C1, C2, C3> customLogic
    ) {
        ConversionService conversionService = getConversionService();
        if (!isProxy()) {
            vanillaLogic.apply(
                    param1.param(),
                    param2.param(),
                    param3.param()
            );
            return;
        }
        customLogic.apply(
                conversionService.wrap(param1.param(), param1.apiType()),
                conversionService.wrap(param2.param(), param2.apiType()),
                conversionService.wrap(param3.param(), param3.apiType()));
    }

    @FunctionalInterface
    interface Function3<A, B, C, R> {
        R apply(A a, B b, C c);
    }

    interface VoidFunction3<A, B, C> { void apply(A a, B b, C c); }
}
