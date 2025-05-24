package de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.util;

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
        return tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi())
        );
    }


    default <V1, C1, V2, C2> void proxy(
            VoidFunction2<V1, V2> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            VoidFunction2<C1, C2> customLogic
    ) {
        tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi())
        );
    }

    @FunctionalInterface
    interface Function2<A, B, R> {
        R apply(A a, B b);
    }

    interface VoidFunction2<A, B> {
        void apply(A a, B b);
    }
}
