package de.verdox.mccreativelab.impl.vanilla.item.components;

import net.minecraft.world.item.component.UseRemainder;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;

import de.verdox.mccreativelab.wrapper.item.components.MCCUseRemainder;
import net.minecraft.world.item.ItemStack;

public class NMSUseRemainder extends MCCHandle<UseRemainder> implements MCCUseRemainder {
    public static final MCCConverter<UseRemainder, NMSUseRemainder> CONVERTER = converter(NMSUseRemainder.class, UseRemainder.class, NMSUseRemainder::new, MCCHandle::getHandle);

    public NMSUseRemainder(UseRemainder handle) {
        super(handle);
    }

    public MCCItemStack getConvertInto() {
        var nms = getConvertIntoFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<MCCItemStack>() {});
    }

    private ItemStack getConvertIntoFromImpl() {
        return handle == null ? null : handle.convertInto();
    }

    public MCCUseRemainder withConvertInto(MCCItemStack convertInto) {
        var param0 = MCCPlatform.getInstance().getConversionService().unwrap(convertInto, new TypeToken<ItemStack>() {});
        return new NMSUseRemainder(new UseRemainder(param0));
    }
}