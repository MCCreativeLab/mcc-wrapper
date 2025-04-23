package de.verdox.mccreativelab.impl.vanilla.platform.serialization;

import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.platform.serialization.MCCSerializers;
import de.verdox.vserializer.generic.Serializer;
import org.apache.commons.lang3.NotImplementedException;

public class NMSSerializers implements MCCSerializers {
    @Override
    public Serializer<MCCBlockState> BLOCK_STATE() {
        throw new NotImplementedException();
    }
}
