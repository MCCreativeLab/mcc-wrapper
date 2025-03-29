package de.verdox.mccreativelab.impl.vanilla.item.components;

import de.verdox.mccreativelab.wrapper.item.components.MCCBlockItemStateProperties;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.component.BlockItemStateProperties;

import java.lang.String;
import java.util.Map;

public class NMSBlockItemStateProperties extends MCCHandle<BlockItemStateProperties> implements MCCBlockItemStateProperties {
    public static final MCCConverter<BlockItemStateProperties, NMSBlockItemStateProperties> CONVERTER = converter(NMSBlockItemStateProperties.class, BlockItemStateProperties.class, NMSBlockItemStateProperties::new, MCCHandle::getHandle);

    public NMSBlockItemStateProperties(BlockItemStateProperties handle) {
        super(handle);
    }

    public Map<String, String> getProperties() {
        var nms = getPropertiesFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Map<String, String>>() {});
    }

    private Map<String, String> getPropertiesFromImpl() {
        return handle == null ? Map.of() : handle.properties();
    }

    public MCCBlockItemStateProperties withProperties(Map<String, String> properties) {
        var param0 = MCCPlatform.getInstance().getConversionService().unwrap(properties, new TypeToken<Map<String, String>>() {});
        return new NMSBlockItemStateProperties(new BlockItemStateProperties(param0));
    }
}