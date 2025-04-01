package de.verdox.mccreativelab;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.kyori.adventure.key.Key;
import net.minecraft.world.flag.FeatureFlags;

import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class TestBase {
    private static final Set<Key> EXCLUDED_REGISTRY_KEYS = Set.of(
            Key.key("minecraft", "dimension"),
            Key.key("minecraft", "recipe"),
            Key.key("minecraft", "advancement")
    );

    public static void bootstrap(Supplier<MCCPlatform> supplier) {
        Objects.requireNonNull(supplier);
        if (!MCCPlatform.INSTANCE.isSetup()) {
            RegistryHelper.setup(FeatureFlags.REGISTRY.allFlags());
            //MCCPlatform mccPlatform = new PaperPlatform(RegistryHelper.getRegistry(), RegistryHelper.getDataPack().fullRegistries().lookup());
            MCCPlatform.INSTANCE.setup(supplier.get(), MCCPlatform::init);
            MCCPlatform.getInstance().getRegistryStorage().freezeCustomRegistries();
        }
    }

    public static <A, F> ApiNativePair<A, F> createPair(A api, F nativeType) {
        return new ApiNativePair<>(api, nativeType);
    }

    public record ApiNativePair<A, F>(A api, F nms) {}

    // Our Test Suite cannot bootstrap all registries properly. Until we find a solution to this we exclude them
    public static Set<Key> getExcludedRegistries() {
        return EXCLUDED_REGISTRY_KEYS;
    }
}
