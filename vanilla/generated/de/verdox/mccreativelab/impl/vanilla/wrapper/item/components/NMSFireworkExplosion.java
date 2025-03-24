package de.verdox.mccreativelab.impl.vanilla.wrapper.item.components;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.item.components.MCCFireworkExplosion;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.world.item.component.FireworkExplosion;

import java.util.List;

public class NMSFireworkExplosion extends MCCHandle<FireworkExplosion> implements MCCFireworkExplosion  {

	public static final MCCConverter<FireworkExplosion, NMSFireworkExplosion> CONVERTER  = converter(NMSFireworkExplosion.class, FireworkExplosion.class, NMSFireworkExplosion::new, MCCHandle::getHandle);

	public NMSFireworkExplosion(FireworkExplosion handle){
		super(handle);
	}

	public MCCFireworkExplosion.Shape getShape(){
		var nms = getShapeFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<MCCFireworkExplosion.Shape>() {});
	}

	private FireworkExplosion.Shape getShapeFromImpl(){
		return handle == null ? null : handle.shape();
	}

	public MCCFireworkExplosion withShape(MCCFireworkExplosion.Shape shape){
		var param0 = MCCPlatform.getInstance().getConversionService().unwrap(shape, new TypeToken<FireworkExplosion.Shape>() {});
		var param1 = getColorsFromImpl();
		var param2 = getFadeColorsFromImpl();
		var param3 = getHasTrailFromImpl();
		var param4 = getHasTwinkleFromImpl();
		return new NMSFireworkExplosion(new FireworkExplosion(param0, param1, param2, param3, param4));
	}

	public List getColors(){
		var nms = getColorsFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<List>() {});
	}

	private IntList getColorsFromImpl(){
		return handle == null ? IntList.of() : handle.colors();
	}

	public MCCFireworkExplosion withColors(List colors){
		var param0 = getShapeFromImpl();
		var param1 = MCCPlatform.getInstance().getConversionService().unwrap(colors, new TypeToken<IntList>() {});
		var param2 = getFadeColorsFromImpl();
		var param3 = getHasTrailFromImpl();
		var param4 = getHasTwinkleFromImpl();
		return new NMSFireworkExplosion(new FireworkExplosion(param0, param1, param2, param3, param4));
	}

	public List getFadeColors(){
		var nms = getFadeColorsFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<List>() {});
	}

	private IntList getFadeColorsFromImpl(){
		return handle == null ? IntList.of() : handle.fadeColors();
	}

	public MCCFireworkExplosion withFadeColors(List fadeColors){
		var param0 = getShapeFromImpl();
		var param1 = getColorsFromImpl();
		var param2 = MCCPlatform.getInstance().getConversionService().unwrap(fadeColors, new TypeToken<IntList>() {});
		var param3 = getHasTrailFromImpl();
		var param4 = getHasTwinkleFromImpl();
		return new NMSFireworkExplosion(new FireworkExplosion(param0, param1, param2, param3, param4));
	}

	public boolean getHasTrail(){
		var nms = getHasTrailFromImpl();
		return nms;
	}

	private boolean getHasTrailFromImpl(){
		return handle == null ? false : handle.hasTrail();
	}

	public MCCFireworkExplosion withHasTrail(boolean hasTrail){
		var param0 = getShapeFromImpl();
		var param1 = getColorsFromImpl();
		var param2 = getFadeColorsFromImpl();
		var param3 = hasTrail;
		var param4 = getHasTwinkleFromImpl();
		return new NMSFireworkExplosion(new FireworkExplosion(param0, param1, param2, param3, param4));
	}

	public boolean getHasTwinkle(){
		var nms = getHasTwinkleFromImpl();
		return nms;
	}

	private boolean getHasTwinkleFromImpl(){
		return handle == null ? false : handle.hasTwinkle();
	}

	public MCCFireworkExplosion withHasTwinkle(boolean hasTwinkle){
		var param0 = getShapeFromImpl();
		var param1 = getColorsFromImpl();
		var param2 = getFadeColorsFromImpl();
		var param3 = getHasTrailFromImpl();
		var param4 = hasTwinkle;
		return new NMSFireworkExplosion(new FireworkExplosion(param0, param1, param2, param3, param4));
	}

}
