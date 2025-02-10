package de.verdox.mccreativelab.impl.vanilla.types;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.types.MCCFrogVariant;
import net.kyori.adventure.key.Key;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.FrogVariant;

public class NMSFrogVariant extends MCCHandle<FrogVariant> implements MCCFrogVariant  {

	public static final MCCConverter<FrogVariant, NMSFrogVariant> CONVERTER  = converter(NMSFrogVariant.class, FrogVariant.class, NMSFrogVariant::new, MCCHandle::getHandle);

	public NMSFrogVariant(FrogVariant handle){
		super(handle);
	}

	public Key getTexture(){
		var nms = getTextureFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Key>() {});
	}

	private ResourceLocation getTextureFromImpl(){
		return handle == null ? null : handle.texture();
	}

}
