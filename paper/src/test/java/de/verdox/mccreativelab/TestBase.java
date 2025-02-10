package de.verdox.mccreativelab;

import de.verdox.mccreativelab.impl.paper.platform.PaperPlatform;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    @BeforeAll
    public static void bootstrap() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        Bootstrap.validate();
        if(!MCCPlatform.INSTANCE.isSetup()) {
            MCCPlatform.INSTANCE.setup(new PaperPlatform(), MCCPlatform::init);
        }
    }
}
