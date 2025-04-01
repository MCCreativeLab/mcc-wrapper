package de.verdox.mccreativelab.conversion;

import de.verdox.mccreativelab.RegistryHelper;
import de.verdox.mccreativelab.TestBase;
import de.verdox.mccreativelab.impl.paper.platform.PaperPlatform;
import org.junit.jupiter.api.BeforeAll;

public class PaperTestBase extends TestBase {

    @BeforeAll
    public static void bootstrap() {
        bootstrap(() -> new PaperPlatform(RegistryHelper.getRegistry(), RegistryHelper.getDataPack().fullRegistries().lookup()));
    }

}
