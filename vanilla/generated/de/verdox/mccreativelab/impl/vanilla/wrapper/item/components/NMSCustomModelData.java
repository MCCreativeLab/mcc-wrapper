package de.verdox.mccreativelab.impl.vanilla.wrapper.item.components;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.item.components.MCCCustomModelData;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.component.CustomModelData;

public class NMSCustomModelData extends MCCHandle<CustomModelData> implements MCCCustomModelData  {

	public static final MCCConverter<CustomModelData, NMSCustomModelData> CONVERTER  = converter(NMSCustomModelData.class, CustomModelData.class, NMSCustomModelData::new, MCCHandle::getHandle);

	public NMSCustomModelData(CustomModelData handle){
		super(handle);
	}

	public int getValue(){
		var nms = getValueFromImpl();
		return nms;
	}

	private int getValueFromImpl(){
		return handle == null ? 0 : handle.value();
	}

	public MCCCustomModelData withValue(int value){
		var param0 = value;
		return new NMSCustomModelData(new CustomModelData(param0));
	}

}
