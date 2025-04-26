package de.verdox.mccreativelab.impl.vanilla.item.equipment;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.item.equipment.MCCEquipmentAsset;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class NMSEquipmentAsset extends MCCHandle<EquipmentAsset> implements MCCEquipmentAsset {
    public static final MCCConverter<EquipmentAsset, NMSEquipmentAsset> CONVERTER = converter(NMSEquipmentAsset.class, EquipmentAsset.class, NMSEquipmentAsset::new, MCCHandle::getHandle);

    public NMSEquipmentAsset(EquipmentAsset handle) {
        super(handle);
    }
}
