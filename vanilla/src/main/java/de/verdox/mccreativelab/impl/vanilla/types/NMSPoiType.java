package de.verdox.mccreativelab.impl.vanilla.types;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.world.level.block.state.BlockState;
import com.google.common.reflect.TypeToken;

import java.util.Set;

import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;

import de.verdox.mccreativelab.wrapper.types.MCCPoiType;

public class NMSPoiType extends MCCHandle<PoiType> implements MCCPoiType {
    public static final MCCConverter<PoiType, NMSPoiType> CONVERTER = converter(NMSPoiType.class, PoiType.class, NMSPoiType::new, MCCHandle::getHandle);

    public NMSPoiType(PoiType handle) {
        super(handle);
    }

    public Set<MCCBlockState> getMatchingStates() {
        var nms = getMatchingStatesFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Set<MCCBlockState>>() {});
    }

    private Set<BlockState> getMatchingStatesFromImpl() {
        return handle == null ? Set.of() : handle.matchingStates();
    }

    public int getMaxTickets() {
        var nms = getMaxTicketsFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Integer>() {});
    }

    private int getMaxTicketsFromImpl() {
        return handle == null ? 0 : handle.maxTickets();
    }

    public int getValidRange() {
        var nms = getValidRangeFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Integer>() {});
    }

    private int getValidRangeFromImpl() {
        return handle == null ? 0 : handle.validRange();
    }
}