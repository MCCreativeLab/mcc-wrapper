package de.verdox.mccreativelab;

import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import org.junit.jupiter.api.BeforeAll;

public class NMSTestBase extends TestBase {

    @BeforeAll
    public static void bootstrap() {
        bootstrap(() -> new NMSPlatform(RegistryHelper.getRegistry(), RegistryHelper.getDataPack().fullRegistries().lookup()));
    }
}
