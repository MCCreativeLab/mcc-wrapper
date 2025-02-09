package de.verdox.mccreativelab.impl.vanilla.wrapper.item.components;
import net.minecraft.world.food.FoodProperties;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import java.lang.Object;
import de.verdox.mccreativelab.wrapper.item.components.MCCFoodProperties;
public class NMSFoodProperties extends MCCHandle<FoodProperties> implements MCCFoodProperties  {
	public static final MCCConverter<FoodProperties, NMSFoodProperties> CONVERTER  = converter(NMSFoodProperties.class, FoodProperties.class, NMSFoodProperties::new, MCCHandle::getHandle);
	public NMSFoodProperties(FoodProperties handle){
		super(handle);
	}
	public MCCFoodProperties.Builder createBuilder(){
		return new NMSBuilder(null);
	}

	public int getNutrition(){
		var nms = getNutritionFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Integer>(){});
	}

	private int getNutritionFromImpl(){
		return handle == null ? 0 : handle.nutrition();
	}

	public MCCFoodProperties withNutrition(int nutrition){
		var param0 = MCCPlatform.getInstance().getConversionService().unwrap(nutrition, new TypeToken<Integer>(){});
		var param1 = getSaturationFromImpl();
		var param2 = getCanAlwaysEatFromImpl();
		return new NMSFoodProperties(new FoodProperties(param0, param1, param2));
	}

	public float getSaturation(){
		var nms = getSaturationFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Float>(){});
	}

	private float getSaturationFromImpl(){
		return handle == null ? 0 : handle.saturation();
	}

	public MCCFoodProperties withSaturation(float saturation){
		var param0 = getNutritionFromImpl();
		var param1 = MCCPlatform.getInstance().getConversionService().unwrap(saturation, new TypeToken<Float>(){});
		var param2 = getCanAlwaysEatFromImpl();
		return new NMSFoodProperties(new FoodProperties(param0, param1, param2));
	}

	public boolean getCanAlwaysEat(){
		var nms = getCanAlwaysEatFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Boolean>(){});
	}

	private boolean getCanAlwaysEatFromImpl(){
		return handle == null ? false : handle.canAlwaysEat();
	}

	public MCCFoodProperties withCanAlwaysEat(boolean canAlwaysEat){
		var param0 = getNutritionFromImpl();
		var param1 = getSaturationFromImpl();
		var param2 = MCCPlatform.getInstance().getConversionService().unwrap(canAlwaysEat, new TypeToken<Boolean>(){});
		return new NMSFoodProperties(new FoodProperties(param0, param1, param2));
	}

	public static class NMSBuilder extends MCCHandle<FoodProperties.Builder> implements MCCFoodProperties.Builder  {
			public static final MCCConverter<FoodProperties.Builder, NMSFoodProperties.NMSBuilder> CONVERTER  = converter(NMSFoodProperties.NMSBuilder.class, FoodProperties.Builder.class, NMSFoodProperties.NMSBuilder::new, MCCHandle::getHandle);
		public NMSBuilder(FoodProperties.Builder handle){
			super(handle);
		}
		}}