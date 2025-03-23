package de.verdox.mccreativelab.impl.vanilla.types;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.types.MCCDamageType;
import net.minecraft.world.damagesource.DamageType;

public class NMSDamageType extends MCCHandle<DamageType> implements MCCDamageType {
    public static final MCCConverter<DamageType, NMSDamageType> CONVERTER = converter(NMSDamageType.class, DamageType.class, NMSDamageType::new, MCCHandle::getHandle);

    public NMSDamageType(DamageType handle) {
        super(handle);
    }
}
