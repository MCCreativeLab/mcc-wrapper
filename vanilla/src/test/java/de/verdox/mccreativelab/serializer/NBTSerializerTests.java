package de.verdox.mccreativelab.serializer;

import de.verdox.mccreativelab.NMSTestBase;
import de.verdox.mccreativelab.RegistryHelper;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.impl.vanilla.platform.serialization.nbt.NBTSerializationContext;
import de.verdox.vserializer.generic.SerializationContext;
import de.verdox.vserializer.generic.SerializationElement;
import de.verdox.vserializer.tests.test.SerializerTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NBTSerializerTests extends SerializerTests {
    public static final NBTSerializationContext NBT_SERIALIZATION_CONTEXT = new NBTSerializationContext();

    @BeforeAll
    public static void boostrap() {
        NMSTestBase.bootstrap(() -> new NMSPlatform(RegistryHelper.getRegistry(), RegistryHelper.getDataPack().fullRegistries().lookup(), RegistryHelper.getDataPack().getRecipeManager()));
    }

    @Override
    public SerializationContext context() {
        return NBT_SERIALIZATION_CONTEXT;
    }

    private File writeStreamToTempFile(InputStream inputStream, String prefix, String suffix) throws IOException {
        File tempFile = File.createTempFile(prefix, suffix);
        tempFile.deleteOnExit();

        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead); // Fix: 0 offset
            }
        }

        return tempFile;
    }
}
