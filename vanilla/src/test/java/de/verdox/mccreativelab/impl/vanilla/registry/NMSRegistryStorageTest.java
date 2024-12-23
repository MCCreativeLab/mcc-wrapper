package de.verdox.mccreativelab.impl.vanilla.registry;

import de.verdox.mccreativelab.TestBase;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.support.ModifierSupport;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class NMSRegistryStorageTest extends TestBase {
    private static final Set<Key> REGISTRY_KEYS = new HashSet<>();

    @BeforeAll
    public static void collectAllRegistries() throws IllegalAccessException {
        for (Field declaredField : Registries.class.getDeclaredFields()) {
            if (!ModifierSupport.isStatic(declaredField) || !ModifierSupport.isFinal(declaredField) || !ModifierSupport.isPublic(declaredField)) {
                continue;
            }
            if (!declaredField.getType().equals(ResourceKey.class)) {
                continue;
            }
            ResourceKey<Registry<?>> registry = (ResourceKey<Registry<?>>) declaredField.get(null);
            Key registryKey = MCCPlatform.getInstance().getConversionService().wrap(registry.location());
            REGISTRY_KEYS.add(registryKey);
        }
    }

    public static Stream<Key> provideRegistryKeys() {
        return REGISTRY_KEYS.stream();
    }

    @ParameterizedTest
    @MethodSource("provideRegistryKeys")
    public void testGetRegistry(Key registryKey) {
        if(getExcludedRegistries().contains(registryKey)){
            return;
        }
        Assertions.assertDoesNotThrow(() -> MCCPlatform.getInstance().getRegistryStorage().getMinecraftRegistry(registryKey));
    }
}
