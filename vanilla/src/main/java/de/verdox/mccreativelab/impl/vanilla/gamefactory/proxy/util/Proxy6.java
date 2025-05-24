package de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.util;

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
        return tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param(), param3.param(), param4.param(), param5.param(), param6.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi(), param3.asApi(), param4.asApi(), param5.asApi(), param6.asApi())
        );
    }

    default <V1, C1, V2, C2, V3, C3, V4, C4, V5, C5, V6, C6> void proxy(
            VoidFunction6<V1, V2, V3, V4, V5, V6> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            NativeParam<V4, C4> param4,
            NativeParam<V5, C5> param5,
            NativeParam<V6, C6> param6,
            VoidFunction6<C1, C2, C3, C4, C5, C6> customLogic
    ) {
        tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param(), param3.param(), param4.param(), param5.param(), param6.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi(), param3.asApi(), param4.asApi(), param5.asApi(), param6.asApi())
        );
    }

    @FunctionalInterface
    interface Function6<A, B, C, D, E, F, R> {
        R apply(A a, B b, C c, D d, E e, F f);
    }

    interface VoidFunction6<A, B, C, D, E, F> {
        void apply(A a, B b, C c, D d, E e, F f);
    }
}
