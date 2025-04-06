package de.verdox.mccreativelab.impl.vanilla.mixins;

import com.google.common.reflect.TypeToken;

import java.util.function.Function;
import java.util.function.Supplier;

public interface GameProxy {
    default <R1, R2> R1 proxy(
            Supplier<R1> vanillaLogic,
            ParamConversion<R1, R2> returnV,
            Supplier<R2> customLogic
    ) {

    }

    default <
            VANILLA_PARAM_1, CUSTOM_PARAM_1,
            VANILLA_RETURN, CUSTOM_RETURN
            >
    VANILLA_RETURN proxy(
            Function<VANILLA_PARAM_1, VANILLA_RETURN> vanillaLogic,
            ParamConversion<VANILLA_PARAM_1, CUSTOM_PARAM_1> param1,
            ParamConversion<VANILLA_RETURN, CUSTOM_RETURN> returnV,
            Function<CUSTOM_PARAM_1, CUSTOM_RETURN> customLogic
    ) {

    }

    default <F, T> ParamConversion<F, T> conv(TypeToken<F> nativeType, TypeToken<T> apiType) {
        return new ParamConversion<>(nativeType, apiType);
    }

    default <F, T> ParamConversion<F, T> conv(Class<F> nativeType, Class<T> apiType) {
        return new ParamConversion<>(TypeToken.of(nativeType), TypeToken.of(apiType));
    }

    default <T> ParamConversion<T, T> ident(Class<T> nativeType) {
        return conv(nativeType, nativeType);
    }


    record ParamConversion<F, T>(TypeToken<F> nativeType, TypeToken<T> apiType) {}
}
