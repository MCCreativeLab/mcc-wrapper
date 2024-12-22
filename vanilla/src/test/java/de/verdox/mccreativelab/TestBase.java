package de.verdox.mccreativelab;

import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    private static boolean bootstrapDone;

    @BeforeAll
    public static void bootstrap() {
        if(!MCCPlatform.INSTANCE.isSetup()) {
            RegistryHelper.setup(FeatureFlags.REGISTRY.allFlags());
            MCCPlatform.INSTANCE.setup(new NMSPlatform(), MCCPlatform::init);
        }
    }
}
