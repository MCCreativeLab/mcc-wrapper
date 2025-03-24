package de.verdox.mccreativelab.impl.vanilla.wrapper.item.components;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.world.item.component.CustomModelData;
import java.util.List;
import java.lang.String;
import java.lang.Integer;
import java.lang.Float;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import java.lang.Object;
import de.verdox.mccreativelab.wrapper.item.components.MCCCustomModelData;
import java.lang.Boolean;
public class NMSCustomModelData extends MCCHandle<CustomModelData> implements MCCCustomModelData  {
	public static final MCCConverter<CustomModelData, NMSCustomModelData> CONVERTER  = converter(NMSCustomModelData.class, CustomModelData.class, NMSCustomModelData::new, MCCHandle::getHandle);
	public NMSCustomModelData(CustomModelData handle){
		super(handle);
	}
	public List<Float> getFloats(){
		var nms = getFloatsFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<List<Float>>(){});
	}

	private List<Float> getFloatsFromImpl(){
		return handle == null ? List.of() : handle.floats();
	}

	public MCCCustomModelData withFloats(List<Float> floats){
		var param0 = MCCPlatform.getInstance().getConversionService().unwrap(floats, new TypeToken<List<Float>>(){});
		var param1 = getFlagsFromImpl();
		var param2 = getStringsFromImpl();
		var param3 = getColorsFromImpl();
		return new NMSCustomModelData(new CustomModelData(param0, param1, param2, param3));
	}

	public List<Boolean> getFlags(){
		var nms = getFlagsFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<List<Boolean>>(){});
	}

	private List<Boolean> getFlagsFromImpl(){
		return handle == null ? List.of() : handle.flags();
	}

	public MCCCustomModelData withFlags(List<Boolean> flags){
		var param0 = getFloatsFromImpl();
		var param1 = MCCPlatform.getInstance().getConversionService().unwrap(flags, new TypeToken<List<Boolean>>(){});
		var param2 = getStringsFromImpl();
		var param3 = getColorsFromImpl();
		return new NMSCustomModelData(new CustomModelData(param0, param1, param2, param3));
	}

	public List<String> getStrings(){
		var nms = getStringsFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<List<String>>(){});
	}

	private List<String> getStringsFromImpl(){
		return handle == null ? List.of() : handle.strings();
	}

	public MCCCustomModelData withStrings(List<String> strings){
		var param0 = getFloatsFromImpl();
		var param1 = getFlagsFromImpl();
		var param2 = MCCPlatform.getInstance().getConversionService().unwrap(strings, new TypeToken<List<String>>(){});
		var param3 = getColorsFromImpl();
		return new NMSCustomModelData(new CustomModelData(param0, param1, param2, param3));
	}

	public List<Integer> getColors(){
		var nms = getColorsFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<List<Integer>>(){});
	}

	private List<Integer> getColorsFromImpl(){
		return handle == null ? List.of() : handle.colors();
	}

	public MCCCustomModelData withColors(List<Integer> colors){
		var param0 = getFloatsFromImpl();
		var param1 = getFlagsFromImpl();
		var param2 = getStringsFromImpl();
		var param3 = MCCPlatform.getInstance().getConversionService().unwrap(colors, new TypeToken<List<Integer>>(){});
		return new NMSCustomModelData(new CustomModelData(param0, param1, param2, param3));
	}
}