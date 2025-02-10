package de.verdox.mccreativelab.impl.vanilla.types;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.types.MCCArmorMaterial;
import de.verdox.mccreativelab.wrapper.types.MCCTrimMaterial;
import net.kyori.adventure.text.Component;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.Map;

public class NMSTrimMaterial extends MCCHandle<TrimMaterial> implements MCCTrimMaterial  {

	public static final MCCConverter<TrimMaterial, NMSTrimMaterial> CONVERTER  = converter(NMSTrimMaterial.class, TrimMaterial.class, NMSTrimMaterial::new, MCCHandle::getHandle);

	public NMSTrimMaterial(TrimMaterial handle){
		super(handle);
	}

	public String getAssetName(){
		var nms = getAssetNameFromImpl();
		return nms;
	}

	private String getAssetNameFromImpl(){
		return handle == null ? null : handle.assetName();
	}

	public MCCReference<MCCItemType> getIngredient(){
		var nms = getIngredientFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<MCCReference<MCCItemType>>() {});
	}

	private Holder<Item> getIngredientFromImpl(){
		return handle == null ? null : handle.ingredient();
	}

	public float getItemModelIndex(){
		var nms = getItemModelIndexFromImpl();
		return nms;
	}

	private float getItemModelIndexFromImpl(){
		return handle == null ? 0 : handle.itemModelIndex();
	}

	public Map<String, MCCReference<MCCArmorMaterial>> getOverrideArmorMaterials(){
		var nms = getOverrideArmorMaterialsFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Map<String, MCCReference<MCCArmorMaterial>>>() {});
	}

	private Map<Holder<ArmorMaterial>, String> getOverrideArmorMaterialsFromImpl(){
		return handle == null ? Map.of() : handle.overrideArmorMaterials();
	}

	public Component getDescription(){
		var nms = getDescriptionFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Component>() {});
	}

	private net.minecraft.network.chat.Component getDescriptionFromImpl(){
		return handle == null ? null : handle.description();
	}

}
