package de.verdox.mccreativelab.serializer;

import de.verdox.mccreativelab.NMSTestBase;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.entity.MCCEffect;
import de.verdox.mccreativelab.wrapper.entity.MCCEffectType;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.platform.serialization.MCCSerializers;
import de.verdox.mccreativelab.wrapper.typed.*;
import de.verdox.vserializer.generic.SerializationElement;
import de.verdox.vserializer.generic.Serializer;
import de.verdox.vserializer.json.JsonSerializerContext;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SerializerTests extends NMSTestBase {
    private static final Set<TestEntry<?>> testEntries = new HashSet<>();
    private static final JsonSerializerContext jsonContext = new JsonSerializerContext();

    public record TestEntry<T>(T objectToSerialize, Serializer<T> serializer) {}

    public static Set<TestEntry<?>> provideTestEntries() {
        return testEntries;
    }

    private static <T> void entry(T objectToSerialize, Serializer<T> serializer) {
        testEntries.add(new TestEntry<>(objectToSerialize, serializer));
    }

    static {
        entry(Key.key("test", "test"), MCCSerializers.KEY);
        entry(Component.text("Test"), MCCSerializers.COMPONENT);
        entry(MCCBlocks.STONE.get(), MCCBlockType.SERIALIZER);
        entry(MCCEffects.DIG_SPEED.get(), MCCEffectType.SERIALIZER);
        entry(MCCEntityTypes.MARKER.get(), MCCEntityType.SERIALIZER);
        entry(MCCItems.STONE.get(), MCCItemType.SERIALIZER);
        entry(MCCItems.STONE.get().createItem(), MCCSerializers.ITEM_STACK);
        entry(MCCPlatform.getInstance().getElementFactory().createEmptyDataComponentMap().edit(MCCDataComponentTypes.REPAIR_COST.get(), editor -> editor.set(3)), MCCSerializers.DATA_COMPONENT_MAP);
    }

    @ParameterizedTest
    @MethodSource("provideTestEntries")
    public <T> void testKeySerializer(TestEntry<T> testEntry) {
        Objects.requireNonNull(testEntry.serializer(), "The provided serializer cannot be null");
        Objects.requireNonNull(testEntry.objectToSerialize(), "The object to serialize cannot be null!");
        SerializationElement serializationElement = testEntry.serializer.serialize(jsonContext, testEntry.objectToSerialize());
        T deserialized = testEntry.serializer.deserialize(serializationElement);
        Assertions.assertEquals(testEntry.objectToSerialize(), deserialized);
    }

}
