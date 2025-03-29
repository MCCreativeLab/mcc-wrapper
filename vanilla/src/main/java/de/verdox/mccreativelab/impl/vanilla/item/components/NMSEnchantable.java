package de.verdox.mccreativelab.impl.vanilla.item.components;

import de.verdox.mccreativelab.wrapper.item.components.MCCEnchantable;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import com.google.common.reflect.TypeToken;
import net.minecraft.world.item.enchantment.Enchantable;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;

public class NMSEnchantable extends MCCHandle<Enchantable> implements MCCEnchantable {
    public static final MCCConverter<Enchantable, NMSEnchantable> CONVERTER = converter(NMSEnchantable.class, Enchantable.class, NMSEnchantable::new, MCCHandle::getHandle);

    public NMSEnchantable(Enchantable handle) {
        super(handle);
    }

    public int getValue() {
        var nms = getValueFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Integer>() {});
    }

    private int getValueFromImpl() {
        return handle == null ? 0 : handle.value();
    }

    public MCCEnchantable withValue(int value) {
        var param0 = MCCPlatform.getInstance().getConversionService().unwrap(value, new TypeToken<Integer>() {});
        return new NMSEnchantable(new Enchantable(param0));
    }
}