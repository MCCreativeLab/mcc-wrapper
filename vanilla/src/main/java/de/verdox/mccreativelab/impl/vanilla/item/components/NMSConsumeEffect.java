package de.verdox.mccreativelab.impl.vanilla.item.components;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.item.components.MCCConsumeEffect;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

public class NMSConsumeEffect extends MCCHandle<ConsumeEffect> implements MCCConsumeEffect {
    public static final MCCConverter<ConsumeEffect, NMSConsumeEffect> CONVERTER = converter(NMSConsumeEffect.class, ConsumeEffect.class, NMSConsumeEffect::new, MCCHandle::getHandle);

    public NMSConsumeEffect(ConsumeEffect handle) {
        super(handle);
    }
}
