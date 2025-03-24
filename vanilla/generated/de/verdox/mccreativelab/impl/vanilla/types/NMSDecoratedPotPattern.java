package de.verdox.mccreativelab.impl.vanilla.types;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.types.MCCDecoratedPotPattern;
import net.kyori.adventure.key.Key;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

public class NMSDecoratedPotPattern extends MCCHandle<DecoratedPotPattern> implements MCCDecoratedPotPattern  {

	public static final MCCConverter<DecoratedPotPattern, NMSDecoratedPotPattern> CONVERTER  = converter(NMSDecoratedPotPattern.class, DecoratedPotPattern.class, NMSDecoratedPotPattern::new, MCCHandle::getHandle);

	public NMSDecoratedPotPattern(DecoratedPotPattern handle){
		super(handle);
	}

	public Key getAssetId(){
		var nms = getAssetIdFromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Key>() {});
	}

	private ResourceLocation getAssetIdFromImpl(){
		return handle == null ? null : handle.assetId();
	}

}
