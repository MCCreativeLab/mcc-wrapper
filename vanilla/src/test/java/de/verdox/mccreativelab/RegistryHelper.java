package de.verdox.mccreativelab;

import com.google.common.util.concurrent.MoreExecutors;
import net.minecraft.SharedConstants;
import net.minecraft.commands.Commands;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.biome.Biome;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

import java.util.Locale;

public final class RegistryHelper {

    private static ReloadableServerResources dataPack;
    private static RegistryAccess.Frozen registry;

    private RegistryHelper() {
    }

    public static ReloadableServerResources getDataPack() {
        if (RegistryHelper.dataPack == null) {
            RegistryHelper.throwError("dataPack");
        }
        return RegistryHelper.dataPack;
    }

    public static RegistryAccess.Frozen getRegistry() {
        if (RegistryHelper.registry == null) {
            RegistryHelper.throwError("registry");
        }
        return RegistryHelper.registry;
    }

    public static RegistryAccess.Frozen createRegistry(FeatureFlagSet featureFlagSet) {
        return RegistryHelper.createLayers(RegistryHelper.createResourceManager(featureFlagSet)).compositeAccess().freeze();
    }

    public static void setup(FeatureFlagSet featureFlagSet) {
        System.setProperty("Paper.pushPaperAssetsRoot", "true"); // Paper - build system changes - push asset root
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();

        MultiPackResourceManager resourceManager = RegistryHelper.createResourceManager(featureFlagSet);
        LayeredRegistryAccess<RegistryLayer> layers = RegistryHelper.createLayers(resourceManager);
        RegistryHelper.registry = layers.compositeAccess().freeze();
        // Register vanilla pack
        RegistryHelper.dataPack = ReloadableServerResources.loadResources(resourceManager, layers, featureFlagSet, Commands.CommandSelection.DEDICATED, 0, MoreExecutors.directExecutor(), MoreExecutors.directExecutor()).join();
        // Bind tags
        RegistryHelper.dataPack.updateRegistryTags();
    }

    public static <T extends Keyed> Class<T> updateClass(Class<T> aClass, NamespacedKey key) {
        Class<T> theClass;
        // Some registries have extra Typed classes such as BlockType and ItemType.
        // To avoid class cast exceptions during init mock the Typed class.
        // To get the correct class, we just use the field type.
        try {
            theClass = (Class<T>) aClass.getField(key.getKey().toUpperCase(Locale.ROOT).replace('.', '_')).getType();
        } catch (ClassCastException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        return theClass;
    }

    private static MultiPackResourceManager createResourceManager(FeatureFlagSet featureFlagSet) {
        // Populate available packs
        PackRepository resourceRepository = ServerPacksSource.createVanillaTrustedRepository();
        resourceRepository.reload();
        // Set up resource manager
        return new MultiPackResourceManager(PackType.SERVER_DATA, resourceRepository.getAvailablePacks().stream().filter(pack -> pack.getRequestedFeatures().isSubsetOf(featureFlagSet)).map(Pack::open).toList());
    }

    private static LayeredRegistryAccess<RegistryLayer> createLayers(MultiPackResourceManager resourceManager) {
        // add tags and loot tables for unit tests
        LayeredRegistryAccess<RegistryLayer> layers = RegistryLayer.createRegistryAccess();
        // Paper start - load registry here to ensure bukkit object registry are correctly delayed if needed
        try {
            Class.forName("org.bukkit.Registry");
        } catch (final ClassNotFoundException ignored) {}
        // Paper end - load registry here to ensure bukkit object registry are correctly delayed if needed
        layers = WorldLoader.loadAndReplaceLayer(resourceManager, layers, RegistryLayer.WORLDGEN, RegistryDataLoader.WORLDGEN_REGISTRIES);

        return layers;
    }

    private static void throwError(String field) {
        throw new IllegalStateException(String.format("""
                Trying to access %s will it is not setup.
                Make sure that either the class or method you test has the right test environment annotation present.
                You can find them in the package src/test/java/org.bukkit.support.environment""", field));
    }
}
