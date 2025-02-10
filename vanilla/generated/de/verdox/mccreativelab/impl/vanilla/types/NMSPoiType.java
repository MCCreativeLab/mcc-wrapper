package de.verdox.mccreativelab.impl.vanilla.types;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.types.MCCPoiType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class NMSPoiType extends MCCHandle<PoiType> implements MCCPoiType  {

	public static final MCCConverter<PoiType, NMSPoiType> CONVERTER  = converter(NMSPoiType.class, PoiType.class, NMSPoiType::new, MCCHandle::getHandle);

	public NMSPoiType(PoiType handle){
		super(handle);
	}

	public Set<MCCBlockState> getMatchingStates(){
		var nms = getMatchingStatesFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Set<MCCBlockState>>() {});
	}

	private Set<BlockState> getMatchingStatesFromImpl(){
		return handle == null ? Set.of() : handle.matchingStates();
	}

	public int getMaxTickets(){
		var nms = getMaxTicketsFromImpl();
		return nms;
	}

	private int getMaxTicketsFromImpl(){
		return handle == null ? 0 : handle.maxTickets();
	}

	public int getValidRange(){
		var nms = getValidRangeFromImpl();
		return nms;
	}

	private int getValidRangeFromImpl(){
		return handle == null ? 0 : handle.validRange();
	}

}
