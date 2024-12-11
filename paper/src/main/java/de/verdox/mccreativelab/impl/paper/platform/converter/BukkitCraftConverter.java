package de.verdox.mccreativelab.impl.paper.platform.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

import java.util.function.Function;

/**
 * @param <F> The native minecraft type
 * @param <B> A craft bukkit object that wraps this native minecraft type but does not have an abstraction layer to share with other classes of this type like {@link org.bukkit.craftbukkit.inventory.CraftItemStack} or {@link org.bukkit.craftbukkit.entity.CraftEntity}
 * @param <T> The MCC type to convert to
 */
public class BukkitCraftConverter<F, B, T> implements MCCConverter<B, T> {
    private final TypeToken<F> nativeType;
    private final TypeToken<B> bukkitType;
    private final TypeToken<T> apiType;
    private final Function<B, F> getter;
    private final Function<F, B> wrapper;

    public BukkitCraftConverter(TypeToken<F> nativeType, TypeToken<B> bukkitType, TypeToken<T> apiType, Function<B, F> getter, Function<F, B> wrapper) {
        this.nativeType = nativeType;
        this.bukkitType = bukkitType;
        this.apiType = apiType;
        this.getter = getter;
        this.wrapper = wrapper;
    }

    @Override
    public ConversionResult<T> wrap(B nativeType) {
        F nmsObject = getter.apply(nativeType);
        if (nmsObject == null) {
            return notDone(null);
        }
        return done(MCCPlatform.getInstance().getConversionService().wrap(nmsObject, apiType));
    }

    @Override
    public ConversionResult<B> unwrap(T platformImplType) {
        F unwrappedNativeType = MCCPlatform.getInstance().getConversionService().unwrap(platformImplType, nativeType);
        B bukkitType = wrapper.apply(unwrappedNativeType);
        if (bukkitType == null) {
            return notDone(null);
        }
        return done(bukkitType);
    }

    @Override
    public Class<T> apiImplementationClass() {
        return (Class<T>) apiType.getRawType();
    }

    @Override
    public Class<B> nativeMinecraftType() {
        return (Class<B>) bukkitType.getRawType();
    }
}
