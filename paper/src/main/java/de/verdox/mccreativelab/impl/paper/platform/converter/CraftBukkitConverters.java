package de.verdox.mccreativelab.impl.paper.platform.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Function;

@ApiStatus.Experimental
public class CraftBukkitConverters {
    public static final MCCConverter<BlockState, BlockData> CRAFT_BLOCK_STATE = register(BlockState.class, CraftBlockData.class, BlockBehaviour.BlockStateBase::createCraftBlockData, CraftBlockData::getState, new TypeToken<>() {});
    public static final MCCConverter<Entity, org.bukkit.entity.Entity> CRAFT_ENTITY = register(Entity.class, CraftEntity.class, (a) -> CraftEntity.getEntity((CraftServer) Bukkit.getServer(), a), CraftEntity::getHandle, new TypeToken<>() {});

    public static final MCCConverter<Item, Material> ITEM = register(Item.class, Material.class, CraftMagicNumbers::getMaterial, CraftMagicNumbers::getItem, new TypeToken<>() {});
    public static final MCCConverter<Block, Material> BLOCK = register(Block.class, Material.class, CraftMagicNumbers::getMaterial, CraftMagicNumbers::getBlock, new TypeToken<>() {});

    public static final MCCConverter<ServerLevel, World> WORLD = register(ServerLevel.class, CraftWorld.class, Level::getWorld, CraftWorld::getHandle, new TypeToken<>() {});
    public static final MCCConverter<LevelChunk, Chunk> CHUNK = register(LevelChunk.class, CraftChunk.class, CraftChunk::new, craftChunk -> (LevelChunk) craftChunk.getHandle(ChunkStatus.FULL), new TypeToken<>() {});

    public static void init() {
    }

    private static <F, A, B extends A> MCCConverter<F, A> register(Class<F> nmsType, Class<B> craftBukkitType, Function<F, B> to, Function<B, F> from, TypeToken<A> apiType) {
        MCCConverter<F, B> converter = new MCCConverter<>() {
            @Override
            public ConversionResult<B> wrap(F nativeType) {
                return done(nativeType != null ? to.apply(nativeType) : null);
            }

            @Override
            public ConversionResult<F> unwrap(B platformImplType) {
                return done(platformImplType != null ? from.apply(platformImplType) : null);
            }

            @Override
            public Class<B> apiImplementationClass() {
                return craftBukkitType;
            }

            @Override
            public Class<F> nativeMinecraftType() {
                return nmsType;
            }
        };
        MCCPlatform.getInstance().getConversionService().registerConverterForNewImplType(apiType.getRawType(), converter);
        return (MCCConverter<F, A>) converter;
    }
}
