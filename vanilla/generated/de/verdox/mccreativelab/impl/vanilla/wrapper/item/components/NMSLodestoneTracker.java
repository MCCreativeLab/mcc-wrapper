package de.verdox.mccreativelab.impl.vanilla.wrapper.item.components;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.item.components.MCCLodestoneTracker;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.item.component.LodestoneTracker;

import java.util.Optional;

public class NMSLodestoneTracker extends MCCHandle<LodestoneTracker> implements MCCLodestoneTracker  {

	public static final MCCConverter<LodestoneTracker, NMSLodestoneTracker> CONVERTER  = converter(NMSLodestoneTracker.class, LodestoneTracker.class, NMSLodestoneTracker::new, MCCHandle::getHandle);

	public NMSLodestoneTracker(LodestoneTracker handle){
		super(handle);
	}

	public Optional<MCCLocation> getTarget(){
		var nms = getTargetFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Optional<MCCLocation>>() {});
	}

	private Optional<GlobalPos> getTargetFromImpl(){
		return handle == null ? null : handle.target();
	}

	public MCCLodestoneTracker withTarget(Optional<MCCLocation> target){
		var param0 = MCCPlatform.getInstance().getConversionService().unwrap(target, new TypeToken<Optional<GlobalPos>>() {});
		var param1 = getTrackedFromImpl();
		return new NMSLodestoneTracker(new LodestoneTracker(param0, param1));
	}

	public boolean getTracked(){
		var nms = getTrackedFromImpl();
		return nms;
	}

	private boolean getTrackedFromImpl(){
		return handle == null ? false : handle.tracked();
	}

	public MCCLodestoneTracker withTracked(boolean tracked){
		var param0 = getTargetFromImpl();
		var param1 = tracked;
		return new NMSLodestoneTracker(new LodestoneTracker(param0, param1));
	}

}
