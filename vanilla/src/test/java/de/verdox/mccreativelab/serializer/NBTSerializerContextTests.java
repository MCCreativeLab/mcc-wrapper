package de.verdox.mccreativelab.serializer;

import de.verdox.mccreativelab.impl.vanilla.platform.serialization.nbt.NBTSerializationContext;
import de.verdox.vserializer.generic.SerializationContext;
import de.verdox.vserializer.tests.test.SerializerContextTests;

public class NBTSerializerContextTests extends SerializerContextTests {
    public static final NBTSerializationContext NBT_SERIALIZATION_CONTEXT = new NBTSerializationContext();

    @Override
    public SerializationContext context() {
        return NBT_SERIALIZATION_CONTEXT;
    }
}
