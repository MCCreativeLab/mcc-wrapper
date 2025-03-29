package de.verdox.mccreativelab.impl.vanilla.item.components;

import net.minecraft.world.item.component.ChargedProjectiles;

import java.lang.reflect.Field;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;

import java.util.List;

import de.verdox.mccreativelab.wrapper.item.components.MCCChargedProjectiles;

import net.minecraft.world.item.ItemStack;

public class NMSChargedProjectiles extends MCCHandle<ChargedProjectiles> implements MCCChargedProjectiles {
    public static final MCCConverter<ChargedProjectiles, NMSChargedProjectiles> CONVERTER = converter(NMSChargedProjectiles.class, ChargedProjectiles.class, NMSChargedProjectiles::new, MCCHandle::getHandle);

    public NMSChargedProjectiles(ChargedProjectiles handle) {
        super(handle);
    }

    public List<MCCItemStack> getProjectiles() {
        var nms = handle.getItems();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<List<MCCItemStack>>() {});
    }

    public MCCChargedProjectiles withProjectiles(List<MCCItemStack> projectiles) {
        var param0 = MCCPlatform.getInstance().getConversionService().unwrap(projectiles, new TypeToken<List<ItemStack>>() {});
        return new NMSChargedProjectiles(ChargedProjectiles.of(param0));
    }
}