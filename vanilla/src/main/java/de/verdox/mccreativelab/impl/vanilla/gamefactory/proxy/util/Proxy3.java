package de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.util;

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
        return tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param(), param3.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi(), param3.asApi())
        );
    }

    default <V1, C1, V2, C2, V3, C3> void proxy(
            VoidFunction3<V1, V2, V3> vanillaLogic,
            NativeParam<V1, C1> param1,
            NativeParam<V2, C2> param2,
            NativeParam<V3, C3> param3,
            VoidFunction3<C1, C2, C3> customLogic
    ) {
        tryCustomOrVanilla(
                () -> vanillaLogic.apply(param1.param(), param2.param(), param3.param()),
                () -> customLogic.apply(param1.asApi(), param2.asApi(), param3.asApi())
        );
    }

    @FunctionalInterface
    interface Function3<A, B, C, R> {
        R apply(A a, B b, C c);
    }

    interface VoidFunction3<A, B, C> { void apply(A a, B b, C c); }
}
