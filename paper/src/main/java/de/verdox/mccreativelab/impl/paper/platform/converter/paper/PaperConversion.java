package de.verdox.mccreativelab.impl.paper.platform.converter.paper;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.conversion.ConversionServiceImpl;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.util.Handleable;
import org.bukkit.inventory.Inventory;

import java.util.function.Function;

public interface PaperConversion {

    ConversionService conversionService = new ConversionServiceImpl();

    MCCConverter<Entity, CraftEntity> ENTITY = register(new TypeToken<org.bukkit.entity.Entity>() {}, new TypeToken<>() {}, new TypeToken<>() {}, Entity::getBukkitEntity, CraftEntity::getHandle);
    MCCConverter<ItemStack, CraftItemStack> ITEM_STACK = register(new TypeToken<org.bukkit.inventory.ItemStack>() {}, new TypeToken<>() {}, new TypeToken<>() {}, stack -> (CraftItemStack) stack.getBukkitStack(), (stack) -> stack.handle);
    MCCConverter<ServerLevel, CraftWorld> WORLD = register(new TypeToken<World>() {}, new TypeToken<>() {}, new TypeToken<>() {}, Level::getWorld, CraftWorld::getHandle);
    MCCConverter<Container, CraftInventory> CONTAINER = register(new TypeToken<Inventory>() {}, new TypeToken<>() {}, new TypeToken<>() {}, CraftInventory::new, CraftInventory::getInventory);
    MCCConverter<LevelChunk, CraftChunk> CHUNK = register(new TypeToken<Chunk>() {}, new TypeToken<>() {}, new TypeToken<>() {}, CraftChunk::new, craftChunk -> (LevelChunk) craftChunk.getHandle(ChunkStatus.FULL));
    MCCConverter<BlockState, CraftBlockData> BLOCK_STATE = register(new TypeToken<BlockData>() {}, new TypeToken<>() {}, new TypeToken<>() {}, BlockBehaviour.BlockStateBase::createCraftBlockData, CraftBlockData::getState);
    //MCCConverter<Block, CraftBlockType<?>> BLOCK_TYPE = handleable(new TypeToken<BlockType>() {}, new TypeToken<CraftBlockType<?>>() {}, new TypeToken<Block>() {}, CraftBlockType::minecraftToBukkitNew);

    private static <NATIVE, CRAFT_TYPE extends BUKKIT_TYPE, BUKKIT_TYPE> MCCConverter<NATIVE, CRAFT_TYPE> register(
            TypeToken<BUKKIT_TYPE> bukkitType,
            TypeToken<CRAFT_TYPE> craftType,
            TypeToken<NATIVE> nativeType,
            Function<NATIVE, ? extends CRAFT_TYPE> nativeToCraft,
            Function<CRAFT_TYPE, ? extends NATIVE> craftToNative) {

        MCCConverter<NATIVE, CRAFT_TYPE> converter = new MCCConverter<>() {
            @Override
            public ConversionResult<CRAFT_TYPE> wrap(NATIVE nativeType) {
                return done(nativeToCraft.apply(nativeType));
            }

            @Override
            public ConversionResult<NATIVE> unwrap(CRAFT_TYPE platformImplType) {
                return done(craftToNative.apply(platformImplType));
            }

            @Override
            public Class<CRAFT_TYPE> apiImplementationClass() {
                return (Class<CRAFT_TYPE>) craftType.getRawType();
            }

            @Override
            public Class<NATIVE> nativeMinecraftType() {
                return (Class<NATIVE>) nativeType.getRawType();
            }
        };

        conversionService.registerConverterForNewImplType(bukkitType.getRawType(), converter);
        return converter;
    }

    private static <NATIVE, CRAFT_TYPE extends Handleable<NATIVE>, BUKKIT_TYPE> MCCConverter<NATIVE, CRAFT_TYPE> handleable(
            TypeToken<BUKKIT_TYPE> bukkitType,
            TypeToken<CRAFT_TYPE> craftType,
            TypeToken<NATIVE> nativeType,
            Function<NATIVE, CRAFT_TYPE> nativeToCraft
            ) {
        MCCConverter<NATIVE, CRAFT_TYPE> converter = new MCCConverter<>() {
            @Override
            public ConversionResult<CRAFT_TYPE> wrap(NATIVE nativeType) {
                return done(nativeToCraft.apply(nativeType));
            }

            @Override
            public ConversionResult<NATIVE> unwrap(CRAFT_TYPE platformImplType) {
                return done(platformImplType.getHandle());
            }

            @Override
            public Class<CRAFT_TYPE> apiImplementationClass() {
                return (Class<CRAFT_TYPE>) craftType.getRawType();
            }

            @Override
            public Class<NATIVE> nativeMinecraftType() {
                return (Class<NATIVE>) nativeType.getRawType();
            }
        };
        conversionService.registerConverterForNewImplType((Class<? super CRAFT_TYPE>) bukkitType.getRawType(), converter);
        return converter;
    }
}
