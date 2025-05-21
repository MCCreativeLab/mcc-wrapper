package de.verdox.mccreativelab.gametest.world;

import com.google.gson.reflect.TypeToken;
import de.verdox.mccreativelab.gametest.GameTest;
import de.verdox.mccreativelab.gametest.GameTestSequence;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.typed.MCCEntityTypes;
import de.verdox.mccreativelab.wrapper.world.MCCEntitySpawnReason;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCBlockPos;

public class WorldTest {
    public static final GameTestSequence CHUNK_TESTS = GameTestSequence.create()
            .withTest(
                    GameTest.create("loadOrCreate", "chunk", new TypeToken<MCCChunk>() {})
                            .withAsyncCode(() -> {
                                MCCWorld world = MCCPlatform.getInstance().getWorlds().getFirst();
                                return world.getOrLoadChunk(new MCBlockPos(1024, 0, 1024));
                            })
                            .addPostCondition(MCCChunk::isLoaded, "Chunk must be loaded")
                            .addPostCondition(mccChunk -> mccChunk.get(new MCBlockPos(1024, 0, 1024)) != null, "The Block position should be accessible by the block")
            )
            .withTest(
                    GameTest.create("loadImmediately", "chunk", new TypeToken<MCCChunk>() {})
                            .withSyncCode(() -> {
                                MCCWorld world = MCCPlatform.getInstance().getWorlds().getFirst();
                                return world.getChunkImmediately(new MCBlockPos(-1024, 0, -1024));
                            })
                            .addPostCondition(mccChunk -> !mccChunk.isLoaded(), "Chunk should not be loaded currently")
            );

    public static final GameTestSequence ENTITY_TESTS = GameTestSequence.create()
            .withTest(
                    GameTest.create("summonEntity", "entity", new TypeToken<MCCEntity>() {})
                            .withAsyncCode(() -> {
                                MCCWorld world = MCCPlatform.getInstance().getWorlds().getFirst();
                                return world.summon(new MCBlockPos(-1024, 0, 1024), MCCEntityTypes.INTERACTION_ENTITY.get(), MCCEntitySpawnReason.SPAWN_ITEM_USE);
                            })
            );
}
