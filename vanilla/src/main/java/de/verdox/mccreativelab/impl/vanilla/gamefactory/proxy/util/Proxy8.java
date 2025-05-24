package de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.util;

public interface Proxy8 extends ProxyUtil {
    default <
            V1, C1,
            V2, C2,
            V3, C3,
            V4, C4,
            V5, C5,
            V6, C6,
            V7, C7,
            V8, C8,
            VR, CR
            >
    VR proxy(
            Function8<V1, V2, V3, V4, V5, V6, V7, V8, VR> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            NativeParam<V4, C4> param4,
            NativeParam<V5, C5> param5,
            NativeParam<V6, C6> param6,
            NativeParam<V7, C7> param7,
            NativeParam<V8, C8> param8,
            Function8<C1, C2, C3, C4, C5, C6, C7, C8, CR> customLogic
    ) {
        return tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param(), param3.param(), param4.param(), param5.param(), param6.param(), param7.param(), param8.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi(), param3.asApi(), param4.asApi(), param5.asApi(), param6.asApi(), param7.asApi(), param8.asApi())
        );
    }

    default <
            V1, C1,
            V2, C2,
            V3, C3,
            V4, C4,
            V5, C5,
            V6, C6,
            V7, C7,
            V8, C8
            > void proxy(
            Proxy8.VoidFunction8<V1, V2, V3, V4, V5, V6, V7, V8> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            NativeParam<V4, C4> param4,
            NativeParam<V5, C5> param5,
            NativeParam<V6, C6> param6,
            NativeParam<V7, C7> param7,
            NativeParam<V8, C8> param8,
            Proxy8.VoidFunction8<C1, C2, C3, C4, C5, C6, C7, C8> customLogic
    ) {
        tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param(), param3.param(), param4.param(), param5.param(), param6.param(), param7.param(), param8.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi(), param3.asApi(), param4.asApi(), param5.asApi(), param6.asApi(), param7.asApi(), param8.asApi())
        );
    }

    @FunctionalInterface
    interface Function8<A, B, C, D, E, F, G, H, R> {
        R apply(A a, B b, C c, D d, E e, F f, G g, H h);
    }

    interface VoidFunction8<A, B, C, D, E, F, G, H> {
        void apply(A a, B b, C c, D d, E e, F f, G g, H h);
    }
}

