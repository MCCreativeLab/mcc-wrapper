package de.verdox.mccreativelab.impl.vanilla.item.components;

import de.verdox.mccreativelab.TestBase;
import de.verdox.mccreativelab.impl.vanilla.wrapper.item.components.NMSUnbreakable;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.typed.MCCDataComponentTypes;
import de.verdox.mccreativelab.wrapper.typed.MCCItems;
import net.kyori.adventure.text.Component;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Unbreakable;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class ItemComponentsTest extends TestBase {
    private static final Set<ItemComponentTestEntry<?, ?>> testEntries = new HashSet<>();

    public record ItemComponentTestEntry<A, F>(ApiNativePair<MCCDataComponentType<A>, DataComponentType<F>> type,
                                               ApiNativePair<A, F> value, @Nullable Function<A, A> editor) {
        ItemComponentTestEntry(ApiNativePair<MCCDataComponentType<A>, DataComponentType<F>> type,
                               ApiNativePair<A, F> value) {
            this(type, value, null);
        }
    }

    @BeforeAll
    public static void setup() {
        testEntries.add(new ItemComponentTestEntry<>(createPair(MCCDataComponentTypes.MAX_STACK_SIZE.get(), DataComponents.MAX_STACK_SIZE), createPair(1, 1)));
        testEntries.add(new ItemComponentTestEntry<>(createPair(MCCDataComponentTypes.MAX_DAMAGE.get(), DataComponents.MAX_DAMAGE), createPair(1, 1)));
        testEntries.add(new ItemComponentTestEntry<>(createPair(MCCDataComponentTypes.DAMAGE.get(), DataComponents.DAMAGE), createPair(1, 1)));
        testEntries.add(new ItemComponentTestEntry<>(createPair(MCCDataComponentTypes.UNBREAKABLE.get(), DataComponents.UNBREAKABLE), createPair(new NMSUnbreakable(null).withShowInTooltip(true), new Unbreakable(true)), value -> value.withShowInTooltip(true)));
    }

    public static Stream<ItemComponentTestEntry<?, ?>> provideTestEntries() {
        return testEntries.stream();
    }

    @ParameterizedTest
    @MethodSource("provideTestEntries")
    public <A, F> void testSetAndGet(ItemComponentTestEntry<A, F> testEntry) {
        MCCItemStack mccItemStack = MCCItems.STONE.get().createItem();
        mccItemStack.components().set(testEntry.type().api(), testEntry.value().api());
        ItemStack stack = MCCPlatform.getInstance().getConversionService().unwrap(mccItemStack);
        Assertions.assertEquals(testEntry.value().nms(), stack.get(testEntry.type().nms()));
    }

    @ParameterizedTest
    @MethodSource("provideTestEntries")
    public <A, F> void testEditThenSet(ItemComponentTestEntry<A, F> testEntry) {
        MCCItemStack mccItemStack = MCCItems.STONE.get().createItem();
        mccItemStack.components().edit(testEntry.type().api(), editor -> {
            editor.set(testEntry.value().api());
        });
        ItemStack stack = MCCPlatform.getInstance().getConversionService().unwrap(mccItemStack);
        Assertions.assertEquals(testEntry.value().nms(), stack.get(testEntry.type().nms()));
    }

    @ParameterizedTest
    @MethodSource("provideTestEntries")
    public <A, F> void testEditUseWith(ItemComponentTestEntry<A, F> testEntry) {
        if (testEntry.editor() == null) {
            return;
        }
        MCCItemStack mccItemStack = MCCItems.STONE.get().createItem();
        mccItemStack.components().edit(testEntry.type().api(), editor -> editor.with(testEntry.editor()));
        ItemStack stack = MCCPlatform.getInstance().getConversionService().unwrap(mccItemStack);
        Assertions.assertEquals(testEntry.value().nms(), stack.get(testEntry.type().nms()));
    }

    @ParameterizedTest
    @MethodSource("provideTestEntries")
    public <A, F> void testCreateNonNull(ItemComponentTestEntry<A, F> testEntry) {
        if (testEntry.editor() == null) {
            return;
        }
        MCCItemStack mccItemStack = MCCItems.STONE.get().createItem();
        mccItemStack.components().edit(testEntry.type().api(), editor -> {
            Assertions.assertNotNull(editor.create());
        });

    }
}
