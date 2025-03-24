package de.verdox.mccreativelab.impl.vanilla.types;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.kyori.adventure.sound.Sound;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.Holder;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.types.MCCInstrument;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.Instrument;

import net.kyori.adventure.text.Component;

public class NMSInstrument extends MCCHandle<Instrument> implements MCCInstrument {
    public static final MCCConverter<Instrument, NMSInstrument> CONVERTER = converter(NMSInstrument.class, Instrument.class, NMSInstrument::new, MCCHandle::getHandle);

    public NMSInstrument(Instrument handle) {
        super(handle);
    }

    public MCCReference<Sound> getSoundEvent() {
        var nms = getSoundEventFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<MCCReference<Sound>>() {});
    }

    private Holder<SoundEvent> getSoundEventFromImpl() {
        return handle == null ? null : handle.soundEvent();
    }

    public float getUseDuration() {
        var nms = getUseDurationFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Float>() {});
    }

    private float getUseDurationFromImpl() {
        return handle == null ? 0 : handle.useDuration();
    }

    public float getRange() {
        var nms = getRangeFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Float>() {});
    }

    private float getRangeFromImpl() {
        return handle == null ? 0 : handle.range();
    }

    public Component getDescription() {
        var nms = getDescriptionFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Component>() {});
    }

    private net.minecraft.network.chat.Component getDescriptionFromImpl() {
        return handle == null ? null : handle.description();
    }
}