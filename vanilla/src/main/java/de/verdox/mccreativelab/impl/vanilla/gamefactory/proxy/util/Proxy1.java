package de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.util;

public interface Proxy1 extends ProxyUtil {
    default <V1, C1, VR, CR> VR proxy(
            Function1<V1, VR> vanillaLogic,
            NativeParam<V1, C1> param1,
            Function1<C1, CR> customLogic
    ) {
        return tryCustomOrVanilla(() -> vanillaLogic.apply(param1.param()), () -> customLogic.apply(param1.asApi()));
    }

    default <V1, C1> void proxy(
            VoidFunction1<V1> vanillaLogic,
            NativeParam<V1, C1> param1,
            VoidFunction1<C1> customLogic
    ) {
        tryCustomOrVanilla(() -> vanillaLogic.apply(param1.param()), () -> customLogic.apply(param1.asApi()));
    }

    interface Function1<A, R> {
        R apply(A a);
    }

    interface VoidFunction1<A> {
        void apply(A a);
    }
}
