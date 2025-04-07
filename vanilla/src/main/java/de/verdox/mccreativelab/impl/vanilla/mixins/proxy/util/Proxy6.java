package de.verdox.mccreativelab.impl.vanilla.mixins.proxy.util;

import de.verdox.mccreativelab.conversion.ConversionService;

public interface Proxy6 extends ProxyUtil {
    default <
            V1, C1,
            V2, C2,
            V3, C3,
            V4, C4,
            V5, C5,
            V6, C6,
            VR, CR
            >
    VR proxy(
            Function6<V1, V2, V3, V4, V5, V6, VR> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            NativeParam<V4, C4> param4,
            NativeParam<V5, C5> param5,
            NativeParam<V6, C6> param6,
            Function6<C1, C2, C3, C4, C5, C6, CR> customLogic
    ) {
        ConversionService conversionService = getConversionService();
        if (!isProxy()) {
            return vanillaLogic.apply(
                    param1.param(),
                    param2.param(),
                    param3.param(),
                    param4.param(),
                    param5.param(),
                    param6.param()
            );
        }
        return conversionService.unwrap(conversionService.unwrap(
                customLogic.apply(
                        conversionService.wrap(param1.param(), param1.apiType()),
                        conversionService.wrap(param2.param(), param2.apiType()),
                        conversionService.wrap(param3.param(), param3.apiType()),
                        conversionService.wrap(param4.param(), param4.apiType()),
                        conversionService.wrap(param5.param(), param5.apiType()),
                        conversionService.wrap(param6.param(), param6.apiType())
                )));
    }

    default <V1, C1, V2, C2, V3, C3, V4, C4, V5, C5, V6, C6> void proxy(
            VoidFunction6<V1, V2, V3, V4, V5, V6> vanillaLogic,
            NativeParam<V1, C1> p1,
            NativeParam<V2, C2> p2,
            NativeParam<V3, C3> p3,
            NativeParam<V4, C4> p4,
            NativeParam<V5, C5> p5,
            NativeParam<V6, C6> p6,
            VoidFunction6<C1, C2, C3, C4, C5, C6> customLogic
    ) {
        ConversionService conversionService = getConversionService();
        if (!isProxy()) {
            vanillaLogic.apply(
                    p1.param(),
                    p2.param(),
                    p3.param(),
                    p4.param(),
                    p5.param(),
                    p6.param()
            );
            return;
        }
        customLogic.apply(
                conversionService.wrap(p1.param(), p1.apiType()),
                conversionService.wrap(p2.param(), p2.apiType()),
                conversionService.wrap(p3.param(), p3.apiType()),
                conversionService.wrap(p4.param(), p4.apiType()),
                conversionService.wrap(p5.param(), p5.apiType()),
                conversionService.wrap(p6.param(), p6.apiType()));
    }

    @FunctionalInterface
    interface Function6<A, B, C, D, E, F, R> {
        R apply(A a, B b, C c, D d, E e, F f);
    }

    interface VoidFunction6<A, B, C, D, E, F> {
        void apply(A a, B b, C c, D d, E e, F f);
    }
}
