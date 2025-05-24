package de.verdox.mccreativelab.impl.paper.platform.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

import java.util.function.Function;

/**
 * @param <NATIVE> The native minecraft type
 * @param <CRAFT_TYPE> A craft bukkit object that wraps this native minecraft type but does not have an abstraction layer to share with other classes of this type like {@link org.bukkit.craftbukkit.inventory.CraftItemStack} or {@link org.bukkit.craftbukkit.entity.CraftEntity}
 * @param <API_IMPL> The MCC type to convert to
 */
public class BukkitCraftConverter<NATIVE, CRAFT_TYPE, API, API_IMPL> implements MCCConverter<CRAFT_TYPE, API_IMPL> {
    private final TypeToken<CRAFT_TYPE> bukkitType;
    private final TypeToken<API> typeToken;
    private final TypeToken<API_IMPL> apiImplType;
    private final Function<CRAFT_TYPE, NATIVE> getter;
    private final Function<NATIVE, CRAFT_TYPE> wrapper;

    public BukkitCraftConverter(TypeToken<CRAFT_TYPE> bukkitType, TypeToken<API> api, TypeToken<API_IMPL> apiImplType, Function<CRAFT_TYPE, NATIVE> getter, Function<NATIVE, CRAFT_TYPE> wrapper) {
        this.bukkitType = bukkitType;
        this.typeToken = api;
        this.apiImplType = apiImplType;
        this.getter = getter;
        this.wrapper = wrapper;
    }

    @Override
    public ConversionResult<API_IMPL> wrap(CRAFT_TYPE bukkitType) {
        NATIVE nmsObject = getter.apply(bukkitType);
        if (nmsObject == null) {
            return notDone(null);
        }
        API api = MCCPlatform.getInstance().getConversionService().wrap(nmsObject, typeToken);
        return done((API_IMPL) api);
    }

    @Override
    public ConversionResult<CRAFT_TYPE> unwrap(API_IMPL platformImplType) {
        NATIVE unwrappedNativeType = MCCPlatform.getInstance().getConversionService().unwrap(platformImplType);
        CRAFT_TYPE bukkitType = wrapper.apply(unwrappedNativeType);
        if (bukkitType == null) {
            return notDone(null);
        }
        return done(bukkitType);
    }

    @Override
    public Class<API_IMPL> apiImplementationClass() {
        return (Class<API_IMPL>) apiImplType.getRawType();
    }

    @Override
    public Class<CRAFT_TYPE> nativeMinecraftType() {
        return (Class<CRAFT_TYPE>) bukkitType.getRawType();
    }

    @Override
    public String toString() {
        return toReadableString();
    }
}
