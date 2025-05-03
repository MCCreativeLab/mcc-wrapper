package de.verdox.mccreativelab.impl.vanilla.mixins.proxy.util;

import de.verdox.mccreativelab.conversion.ConversionService;

public interface Proxy4 extends ProxyUtil {
    default <
            V1, C1,
            V2, C2,
            V3, C3,
            V4, C4,
            VR, CR
            >
    VR proxy(
            Function4<V1, V2, V3, V4, VR> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            NativeParam<V4, C4> param4,
            Function4<C1, C2, C3, C4, CR> customLogic
    ) {
        ConversionService conversionService = getConversionService();
        if (!isProxy()) {
            return vanillaLogic.apply(
                    param1.param(),
                    param2.param(),
                    param3.param(),
                    param4.param()
            );
        }
        return conversionService.unwrap(conversionService.unwrap(
                customLogic.apply(
                        conversionService.wrap(param1.param(), param1.apiType()),
                        conversionService.wrap(param2.param(), param2.apiType()),
                        conversionService.wrap(param3.param(), param3.apiType()),
                        conversionService.wrap(param4.param(), param4.apiType())
                )));
    }

    default <V1, C1, V2, C2, V3, C3, V4, C4> void proxy(
            VoidFunction4<V1, V2, V3, V4> vanillaLogic,
            NativeParam<V1, C1> p1,
            NativeParam<V2, C2> p2,
            NativeParam<V3, C3> p3,
            NativeParam<V4, C4> p4,
            VoidFunction4<C1, C2, C3, C4> customLogic
    ) {
        ConversionService conversionService = getConversionService();
        if (!isProxy()) {
            vanillaLogic.apply(
                    p1.param(),
                    p2.param(),
                    p3.param(),
                    p4.param()
            );
            return;
        }
        customLogic.apply(
                conversionService.wrap(p1.param(), p1.apiType()),
                conversionService.wrap(p2.param(), p2.apiType()),
                conversionService.wrap(p3.param(), p3.apiType()),
                conversionService.wrap(p4.param(), p4.apiType()));
    }

    interface Function4<A, B, C, D, R> { R apply(A a, B b, C c, D d); }
    interface VoidFunction4<A, B, C, D> { void apply(A a, B b, C c, D d); }
}

