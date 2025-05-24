package de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.util;

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
        return tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param(), param3.param(), param4.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi(), param3.asApi(), param4.asApi())
        );
    }

    default <V1, C1, V2, C2, V3, C3, V4, C4> void proxy(
            VoidFunction4<V1, V2, V3, V4> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            NativeParam<V4, C4> param4,
            VoidFunction4<C1, C2, C3, C4> customLogic
    ) {
        tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param(), param3.param(), param4.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi(), param3.asApi(), param4.asApi())
        );
    }

    interface Function4<A, B, C, D, R> {
        R apply(A a, B b, C c, D d);
    }

    interface VoidFunction4<A, B, C, D> {
        void apply(A a, B b, C c, D d);
    }
}

