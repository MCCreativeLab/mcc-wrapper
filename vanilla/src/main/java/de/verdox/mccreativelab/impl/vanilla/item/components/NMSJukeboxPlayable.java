package de.verdox.mccreativelab.impl.vanilla.item.components;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.types.MCCJukeboxSong;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.JukeboxPlayable;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.JukeboxSong;

import de.verdox.mccreativelab.wrapper.item.components.MCCJukeboxPlayable;
import de.verdox.mccreativelab.wrapper.registry.MCCEitherReference;

public class NMSJukeboxPlayable extends MCCHandle<JukeboxPlayable> implements MCCJukeboxPlayable {
    public static final MCCConverter<JukeboxPlayable, NMSJukeboxPlayable> CONVERTER = converter(NMSJukeboxPlayable.class, JukeboxPlayable.class, NMSJukeboxPlayable::new, MCCHandle::getHandle);

    public NMSJukeboxPlayable(JukeboxPlayable handle) {
        super(handle);
    }

    public MCCEitherReference<MCCJukeboxSong> getSong() {
        var nms = getSongFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<MCCEitherReference<MCCJukeboxSong>>() {});
    }

    private EitherHolder<JukeboxSong> getSongFromImpl() {
        return handle == null ? null : handle.song();
    }

    public MCCJukeboxPlayable withSong(MCCEitherReference<MCCJukeboxSong> song) {
        var param0 = MCCPlatform.getInstance().getConversionService().unwrap(song, new TypeToken<EitherHolder<JukeboxSong>>() {});
        var param1 = getShowInTooltipFromImpl();
        return new NMSJukeboxPlayable(new JukeboxPlayable(param0, param1));
    }

    public boolean getShowInTooltip() {
        var nms = getShowInTooltipFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Boolean>() {});
    }

    private boolean getShowInTooltipFromImpl() {
        return handle == null ? false : handle.showInTooltip();
    }

    public MCCJukeboxPlayable withShowInTooltip(boolean showInTooltip) {
        var param0 = getSongFromImpl();
        var param1 = MCCPlatform.getInstance().getConversionService().unwrap(showInTooltip, new TypeToken<Boolean>() {});
        return new NMSJukeboxPlayable(new JukeboxPlayable(param0, param1));
    }
}