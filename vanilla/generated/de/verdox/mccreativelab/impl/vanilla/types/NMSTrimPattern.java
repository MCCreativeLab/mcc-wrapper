package de.verdox.mccreativelab.impl.vanilla.types;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.types.MCCTrimPattern;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;

public class NMSTrimPattern extends MCCHandle<TrimPattern> implements MCCTrimPattern  {

	public static final MCCConverter<TrimPattern, NMSTrimPattern> CONVERTER  = converter(NMSTrimPattern.class, TrimPattern.class, NMSTrimPattern::new, MCCHandle::getHandle);

	public NMSTrimPattern(TrimPattern handle){
		super(handle);
	}

	public Key getAssetId(){
		var nms = getAssetIdFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Key>() {});
	}

	private ResourceLocation getAssetIdFromImpl(){
		return handle == null ? null : handle.assetId();
	}

	public MCCReference<MCCItemType> getTemplateItem(){
		var nms = getTemplateItemFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<MCCReference<MCCItemType>>() {});
	}

	private Holder<Item> getTemplateItemFromImpl(){
		return handle == null ? null : handle.templateItem();
	}

	public Component getDescription(){
		var nms = getDescriptionFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Component>() {});
	}

	private net.minecraft.network.chat.Component getDescriptionFromImpl(){
		return handle == null ? null : handle.description();
	}

	public boolean getDecal(){
		var nms = getDecalFromImpl();
		return nms;
	}

	private boolean getDecalFromImpl(){
		return handle == null ? false : handle.decal();
	}

}
