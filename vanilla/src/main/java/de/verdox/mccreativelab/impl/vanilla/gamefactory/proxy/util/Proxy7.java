package de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.util;

public interface Proxy7 extends ProxyUtil {
    default <
            V1, C1,
            V2, C2,
            V3, C3,
            V4, C4,
            V5, C5,
            V6, C6,
            V7, C7,
            VR, CR
            >
    VR proxy(
            Function7<V1, V2, V3, V4, V5, V6, V7, VR> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            NativeParam<V4, C4> param4,
            NativeParam<V5, C5> param5,
            NativeParam<V6, C6> param6,
            NativeParam<V7, C7> param7,
            Function7<C1, C2, C3, C4, C5, C6, C7, CR> customLogic
    ) {
        return tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param(), param3.param(), param4.param(), param5.param(), param6.param(), param7.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi(), param3.asApi(), param4.asApi(), param5.asApi(), param6.asApi(), param7.asApi())
        );
    }

    default <
            V1, C1,
            V2, C2,
            V3, C3,
            V4, C4,
            V5, C5,
            V6, C6,
            V7, C7
            > void proxy(
            Proxy7.VoidFunction7<V1, V2, V3, V4, V5, V6, V7> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            NativeParam<V4, C4> param4,
            NativeParam<V5, C5> param5,
            NativeParam<V6, C6> param6,
            NativeParam<V7, C7> param7,
            Proxy7.VoidFunction7<C1, C2, C3, C4, C5, C6, C7> customLogic
    ) {
        tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param(), param3.param(), param4.param(), param5.param(), param6.param(), param7.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi(), param3.asApi(), param4.asApi(), param5.asApi(), param6.asApi(), param7.asApi())
        );
    }

    @FunctionalInterface
    interface Function7<A, B, C, D, E, F, G, R> {
        R apply(A a, B b, C c, D d, E e, F f, G g);
    }

    interface VoidFunction7<A, B, C, D, E, F, G> {
        void apply(A a, B b, C c, D d, E e, F f, G g);
    }
}
