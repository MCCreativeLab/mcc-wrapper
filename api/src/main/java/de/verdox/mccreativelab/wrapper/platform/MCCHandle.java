package de.verdox.mccreativelab.wrapper.platform;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.component.GameComponentRegistry;

import java.util.Objects;
import java.util.function.Function;

public class MCCHandle<T> {
    protected final T handle;
    protected ConversionService conversionService = MCCPlatform.getInstance().getConversionService();
    protected GameComponentRegistry componentRegistry = MCCPlatform.getInstance().getGameComponentRegistry();

    public MCCHandle(T handle) {
        this.handle = handle;
    }

    public T getHandle() {
        return handle;
    }

    protected static <F, T> MCCConverter<F, T> converterCustomResult(Class<T> apiType, Class<F> platformType, Function<F, MCCConverter.ConversionResult<T>> wrap, Function<T, MCCConverter.ConversionResult<F>> unwrap) {
        return new MCCConverter<>() {
            @Override
            public ConversionResult<T> wrap(F platform) {
                return wrap.apply(platform);
            }

            @Override
            public ConversionResult<F> unwrap(T api) {
                return unwrap.apply(api);
            }

            @Override
            public Class<T> apiImplementationClass() {
                return apiType;
            }

            @Override
            public Class<F> nativeMinecraftType() {
                return platformType;
            }

            @Override
            public String toString() {
                return toReadableString();
            }
        };
    }

    protected static <F, T> MCCConverter<F, T> converter(Class<T> apiType, Class<F> platformType, Function<F, T> wrap, Function<T, F> unwrap) {
        return new MCCConverter<>() {
            @Override
            public ConversionResult<T> wrap(F platform) {
                return done(wrap.apply(platform));
            }

            @Override
            public ConversionResult<F> unwrap(T api) {
                return done(unwrap.apply(api));
            }

            @Override
            public Class<T> apiImplementationClass() {
                return apiType;
            }

            @Override
            public Class<F> nativeMinecraftType() {
                return platformType;
            }

            @Override
            public String toString() {
                return "MCCConverter " + nativeMinecraftType() + " <-> " + apiImplementationClass();
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCCHandle<?> MCCHandle = (MCCHandle<?>) o;
        return Objects.equals(handle, MCCHandle.handle);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(handle);
    }

    protected <R> R readFieldFromHandle(String fieldName, TypeToken<R> type) {
        return readFieldFromHandle(getHandle(), fieldName, type);
    }

    protected <R> void writeFieldInHandle(String fieldName, R value) {
        writeFieldInHandle(getHandle(), fieldName, value);
    }

    public <R> R invokeMethodInHandle(String methodName, Object... args) {
        return invokeMethodInHandle(getHandle(), methodName, args);
    }

    public <H, R> R readFieldFromHandle(H handle, String fieldName, TypeToken<R> type) {
        return ReflectionUtils.readFieldFromClass(handle, fieldName, type);
    }

    public <H, R> void writeFieldInHandle(H handle, String fieldName, R value) {
        ReflectionUtils.writeFieldInClass(handle, fieldName, value);
    }

    public <H, R> R invokeMethodInHandle(H handle, String methodName, Object... args) {
        return ReflectionUtils.invokeMethodInClass(handle, methodName, args);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "handle=" + handle + '}';
    }
}
