package de.verdox.mccreativelab.impl.vanilla.wrapper.item.components;
import net.minecraft.world.item.component.ChargedProjectiles;
import java.lang.reflect.Field;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import java.util.List;
import de.verdox.mccreativelab.wrapper.item.components.MCCChargedProjectiles;
import java.lang.Object;
import net.minecraft.world.item.ItemStack;
public class NMSChargedProjectiles extends MCCHandle<ChargedProjectiles> implements MCCChargedProjectiles  {
	public static final MCCConverter<ChargedProjectiles, NMSChargedProjectiles> CONVERTER  = converter(NMSChargedProjectiles.class, ChargedProjectiles.class, NMSChargedProjectiles::new, MCCHandle::getHandle);
	public NMSChargedProjectiles(ChargedProjectiles handle){
		super(handle);
	}
	public List<MCCItemStack> getArg0(){
		var nms = getArg0FromImpl();
		return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<List<MCCItemStack>>(){});
	}

	private List<ItemStack> getArg0FromImpl(){
		List<ItemStack> nms;
		if(handle==null) return List.of();
		try {
			Field nmsField = ChargedProjectiles.class.getDeclaredField("arg0");
			nmsField.setAccessible(true);
			nms = (List<ItemStack>) nmsField.get(handle);
		} catch (Throwable e) { throw new RuntimeException(e); }
		return nms;
	}

	public MCCChargedProjectiles withArg0(List<MCCItemStack> arg0){
		var param0 = MCCPlatform.getInstance().getConversionService().unwrap(arg0, new TypeToken<List<ItemStack>>(){});
		return new NMSChargedProjectiles(ChargedProjectiles.of(param0));
	}
}