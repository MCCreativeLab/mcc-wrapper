package de.verdox.mccreativelab.impl.vanilla.item.components;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;

import java.util.Optional;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.UseCooldown;

import de.verdox.mccreativelab.wrapper.item.components.MCCUseCooldown;
import net.kyori.adventure.key.Key;

public class NMSUseCooldown extends MCCHandle<UseCooldown> implements MCCUseCooldown {
    public static final MCCConverter<UseCooldown, NMSUseCooldown> CONVERTER = converter(NMSUseCooldown.class, UseCooldown.class, NMSUseCooldown::new, MCCHandle::getHandle);

    public NMSUseCooldown(UseCooldown handle) {
        super(handle);
    }

    public float getSeconds() {
        var nms = getSecondsFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Float>() {});
    }

    private float getSecondsFromImpl() {
        return handle == null ? 0 : handle.seconds();
    }

    public MCCUseCooldown withSeconds(float seconds) {
        var param0 = MCCPlatform.getInstance().getConversionService().unwrap(seconds, new TypeToken<Float>() {});
        var param1 = getCooldownGroupFromImpl();
        return new NMSUseCooldown(new UseCooldown(param0, param1));
    }

    public Optional<Key> getCooldownGroup() {
        var nms = getCooldownGroupFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Optional<Key>>() {});
    }

    private Optional<ResourceLocation> getCooldownGroupFromImpl() {
        return handle == null ? null : handle.cooldownGroup();
    }

    public MCCUseCooldown withCooldownGroup(Optional<Key> cooldownGroup) {
        var param0 = getSecondsFromImpl();
        var param1 = MCCPlatform.getInstance().getConversionService().unwrap(cooldownGroup, new TypeToken<Optional<ResourceLocation>>() {});
        return new NMSUseCooldown(new UseCooldown(param0, param1));
    }
}