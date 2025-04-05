package de.verdox.mccreativelab.impl.vanilla.block;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.block.MCCBlockProperties;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class NMSBlockProperties extends MCCHandle<BlockBehaviour.Properties> implements MCCBlockProperties {
    public static final MCCConverter<BlockBehaviour.Properties, NMSBlockProperties> CONVERTER = converter(NMSBlockProperties.class, BlockBehaviour.Properties.class, NMSBlockProperties::new, MCCHandle::getHandle);

    public NMSBlockProperties(BlockBehaviour.Properties handle) {
        super(handle);
    }
}
