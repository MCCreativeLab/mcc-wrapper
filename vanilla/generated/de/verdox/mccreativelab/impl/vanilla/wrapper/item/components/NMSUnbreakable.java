package de.verdox.mccreativelab.impl.vanilla.wrapper.item.components;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.item.components.MCCUnbreakable;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.component.Unbreakable;

public class NMSUnbreakable extends MCCHandle<Unbreakable> implements MCCUnbreakable  {

	public static final MCCConverter<Unbreakable, NMSUnbreakable> CONVERTER  = converter(NMSUnbreakable.class, Unbreakable.class, NMSUnbreakable::new, MCCHandle::getHandle);

	public NMSUnbreakable(Unbreakable handle){
		super(handle);
	}

	public boolean getShowInTooltip(){
		var nms = getShowInTooltipFromImpl();
		return nms;
	}

	private boolean getShowInTooltipFromImpl(){
		return handle == null ? false : handle.showInTooltip();
	}

	public MCCUnbreakable withShowInTooltip(boolean showInTooltip){
		var param0 = showInTooltip;
		return new NMSUnbreakable(new Unbreakable(param0));
	}

}
