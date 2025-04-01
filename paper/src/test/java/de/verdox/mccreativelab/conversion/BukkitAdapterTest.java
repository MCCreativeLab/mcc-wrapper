package de.verdox.mccreativelab.conversion;

import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockType;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class BukkitAdapterTest extends PaperTestBase {

    public record TestEntry<F, B, A>(F nativeObject, B expectedBukkitObject, Class<A> apiType) {
    }

    public record TestEntry2(Class<?> expected, Class<?> toWrap) {}

    private static final Set<TestEntry<?, ?, ?>> testEntries = new HashSet<>();
    private static final Set<TestEntry2> testEntries2 = new HashSet<>();

    static Stream<TestEntry<?, ?, ?>> testInputs() {
        return testEntries.stream();
    }

    static Stream<TestEntry2> testInputs2() {
        return testEntries2.stream();
    }

    @BeforeAll
    public static void setupTestEntries() {
        testEntries.add(new TestEntry<>(new ItemStack(Items.STONE), org.bukkit.inventory.ItemStack.of(Material.STONE), MCCItemStack.class));

        //testEntries2.add(new TestEntry2(MCCItemStack.class, org.bukkit.inventory.ItemStack.class));
        testEntries2.add(new TestEntry2(MCCItemStack.class, CraftItemStack.class));

        //testEntries2.add(new TestEntry2(MCCWorld.class, World.class));
        testEntries2.add(new TestEntry2(MCCWorld.class, CraftWorld.class));

        //testEntries2.add(new TestEntry2(MCCEntity.class, Entity.class));
        testEntries2.add(new TestEntry2(MCCEntity.class, CraftEntity.class));

        //testEntries2.add(new TestEntry2(MCCChunk.class, Chunk.class));
        testEntries2.add(new TestEntry2(MCCChunk.class, CraftChunk.class));

        //testEntries2.add(new TestEntry2(MCCBlockState.class, BlockData.class));
        testEntries2.add(new TestEntry2(MCCBlockState.class, CraftBlockData.class));

        //testEntries2.add(new TestEntry2(MCCEntityType.class, EntityType.class));
        testEntries2.add(new TestEntry2(MCCLocation.class, Location.class));

        //testEntries2.add(new TestEntry2(MCCBlock.class, Block.class));
        testEntries2.add(new TestEntry2(MCCBlock.class, CraftBlock.class));

        testEntries2.add(new TestEntry2(MCCBlockType.class, BlockType.class));
        testEntries2.add(new TestEntry2(MCCItemType.class, ItemType.class));
    }

    @ParameterizedTest
    @MethodSource("testInputs")
    public <F, B, A> void testBukkitAdapterWrap(TestEntry<F, B, A> testEntry) {
        // Convert native -> Api
        A apiType = MCCPlatform.getInstance().getConversionService().wrap(testEntry.nativeObject);
        // Convert bukkit -> Api
        A bukkitToApi = BukkitAdapter.wrap(testEntry.expectedBukkitObject);

        // Check if they are equal
        Assertions.assertEquals(apiType, bukkitToApi);
    }

    @ParameterizedTest
    @MethodSource("testInputs")
    public <F, B, A> void testBukkitAdapterUnwrap(TestEntry<F, B, A> testEntry) {
        // Convert native -> Api
        A apiType = MCCPlatform.getInstance().getConversionService().wrap(testEntry.nativeObject);
        // Convert api -> bukkit
        org.bukkit.inventory.ItemStack toBukkit = BukkitAdapter.unwrap(apiType);

        // Check if api = expected
        Assertions.assertEquals(testEntry.expectedBukkitObject, toBukkit);
    }

    @ParameterizedTest
    @MethodSource("testInputs2")
    public void test(TestEntry2 testEntry) {
        Assertions.assertEquals(testEntry.expected, BukkitAdapter.getConversionService().wrapClassType(testEntry.toWrap()));
    }
}
