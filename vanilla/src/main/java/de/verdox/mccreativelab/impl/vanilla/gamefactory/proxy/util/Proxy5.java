package de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.util;

public interface Proxy5 extends ProxyUtil {
    default <
            V1, C1,
            V2, C2,
            V3, C3,
            V4, C4,
            V5, C5,
            VR, CR
            >
    VR proxy(
            Function5<V1, V2, V3, V4, V5, VR> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            NativeParam<V4, C4> param4,
            NativeParam<V5, C5> param5,
            Function5<C1, C2, C3, C4, C5, CR> customLogic
    ) {
        return tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param(), param3.param(), param4.param(), param5.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi(), param3.asApi(), param4.asApi(), param5.asApi())
        );
    }

    default <V1, C1, V2, C2, V3, C3, V4, C4, V5, C5> void proxy(
            VoidFunction5<V1, V2, V3, V4, V5> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            NativeParam<V4, C4> param4,
            NativeParam<V5, C5> param5,
            VoidFunction5<C1, C2, C3, C4, C5> customLogic
    ) {
        tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param(), param3.param(), param4.param(), param5.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi(), param3.asApi(), param4.asApi(), param5.asApi())
        );
    }

    @FunctionalInterface
    interface Function5<A, B, C, D, E, R> {
        R apply(A a, B b, C c, D d, E e);
    }

    interface VoidFunction5<A, B, C, D, E> {
        void apply(A a, B b, C c, D d, E e);
    }
}
