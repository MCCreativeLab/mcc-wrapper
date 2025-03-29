package de.verdox.mccreativelab.impl.vanilla.item.components;

import net.minecraft.world.item.enchantment.Repairable;
import net.minecraft.world.item.Item;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.registry.MCCReferenceSet;

import net.minecraft.core.HolderSet;
import de.verdox.mccreativelab.wrapper.item.components.MCCRepairable;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;

public class NMSRepairable extends MCCHandle<Repairable> implements MCCRepairable {
    public static final MCCConverter<Repairable, NMSRepairable> CONVERTER = converter(NMSRepairable.class, Repairable.class, NMSRepairable::new, MCCHandle::getHandle);

    public NMSRepairable(Repairable handle) {
        super(handle);
    }

    public MCCReferenceSet<MCCItemType> getItems() {
        var nms = getItemsFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<MCCReferenceSet<MCCItemType>>() {});
    }

    private HolderSet<Item> getItemsFromImpl() {
        return handle == null ? null : handle.items();
    }

    public MCCRepairable withItems(MCCReferenceSet<MCCItemType> items) {
        var param0 = MCCPlatform.getInstance().getConversionService().unwrap(items, new TypeToken<HolderSet<Item>>() {});
        return new NMSRepairable(new Repairable(param0));
    }
}