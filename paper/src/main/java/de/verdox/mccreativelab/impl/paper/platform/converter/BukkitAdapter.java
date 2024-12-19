package de.verdox.mccreativelab.impl.paper.platform.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.conversion.ConversionServiceImpl;
import de.verdox.mccreativelab.conversion.NoConverterFoundException;
import de.verdox.mccreativelab.conversion.converter.EnumConverter;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.block.*;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlot;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockType;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.entity.*;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.inventory.CraftItemType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.function.Function;

public class BukkitAdapter {

    private static final ConversionService conversionService = new ConversionServiceImpl();

    public static void init() {
        conversionService.registerConverterForNewImplType(MCCCapturedBlockState.class, new CraftBlockStateConverter());
        conversionService.registerConverterForNewImplType(MCCEquipmentSlot.class, new EnumConverter<>(EquipmentSlot.class, MCCEquipmentSlot.class));
        conversionService.registerConverterForNewImplType(MCCBlockFace.class, new EnumConverter<>(BlockFace.class, MCCBlockFace.class));
    }


    static final BukkitCraftConverter<ItemStack, CraftItemStack, MCCItemStack> ITEM_STACK = register(MCCItemStack.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftItemStack::asNMSCopy, itemStack -> (CraftItemStack) itemStack.getBukkitStack());
    static final BukkitCraftConverter<ServerLevel, CraftWorld, MCCWorld> WORLD = register(MCCWorld.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftWorld::getHandle, Level::getWorld);
    static final BukkitCraftConverter<Container, CraftInventory, MCCContainer> CONTAINER = register(MCCContainer.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftInventory::getInventory, CraftInventory::new);
    static final BukkitCraftConverter<Entity, CraftEntity, MCCEntity> ENTITY = register(MCCEntity.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftEntity::getHandle, Entity::getBukkitEntity);
    static final BukkitCraftConverter<LevelChunk, CraftChunk, MCCChunk> CHUNK = register(MCCChunk.class, new TypeToken<>() {}, new TypeToken<>() {}, craftChunk -> (LevelChunk) craftChunk.getHandle(ChunkStatus.FULL), CraftChunk::new);
    static final BukkitCraftConverter<BlockState, CraftBlockData, MCCBlockState> BLOCK_DATA = register(MCCBlockState.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftBlockData::getState, CraftBlockData::createData);
    static final BukkitCraftConverter<EntityType, org.bukkit.entity.EntityType, MCCEntityType> ENTITY_TYPE = register(MCCEntityType.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftEntityType::bukkitToMinecraft, CraftEntityType::minecraftToBukkit);
    static final MCCConverter<Location, MCCLocation> LOCATION = register(new BukkitLocationConverter());
    static final MCCConverter<org.bukkit.block.Block, MCCBlock> BLOCK = register(new BukkitBlockConverter());
    static final BukkitCraftConverter<Block, BlockType, MCCBlockType> BLOCK_TYPE = register(MCCBlockType.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftBlockType::bukkitToMinecraftNew, CraftBlockType::minecraftToBukkitNew);
    static final BukkitCraftConverter<Item, ItemType, MCCItemType> ITEM_TYPE = register(MCCItemType.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftItemType::bukkitToMinecraftNew, CraftItemType::minecraftToBukkitNew);

    public static <F, T> T wrap(F nativeObject) {
        if (nativeObject instanceof Material material) {
            Item nmsItem = CraftItemType.bukkitToMinecraft(material);
            if (nmsItem != null) {
                return wrap(CraftItemType.minecraftToBukkitNew(nmsItem));
            }
            Block nmsBlock = CraftBlockType.bukkitToMinecraft(material);
            if (nmsBlock == null) {
                throw new NoConverterFoundException("Could not find a converter to convert the material " + material);
            }
            return wrap(CraftBlockType.minecraftToBukkitNew(nmsBlock));
        }

        return conversionService.wrap(nativeObject);
    }

    public static <F, T> F unwrap(T apiObject) {
        Object result = conversionService.unwrap(apiObject);
        if (result instanceof Material material) {
            Item nmsItem = CraftItemType.bukkitToMinecraft(material);
            if (nmsItem != null) {
                return (F) CraftItemType.minecraftToBukkitNew(nmsItem);
            }
            Block nmsBlock = CraftBlockType.bukkitToMinecraft(material);
            if (nmsBlock != null) {
                return (F) CraftBlockType.minecraftToBukkitNew(nmsBlock);
            }

        }
        return (F) result;
    }

    public static <F, T> F unwrap(@Nullable T objectToUnwrap, TypeToken<F> nativePlatformType) {
        return unwrap(objectToUnwrap);
    }

    public static <F, T> F unwrap(@Nullable T objectToUnwrap, Class<F> nativePlatformType) {
        return unwrap(objectToUnwrap);
    }

    public static <F, T> T wrap(@Nullable F objectToWrap, TypeToken<T> apiTypeToConvertTo) {
        return wrap(objectToWrap);
    }

    public static <F, T> T wrap(@Nullable F objectToWrap, Class<T> apiTypeToConvertTo) {
        return wrap(objectToWrap);
    }

    private static <F, B, A, T extends A> BukkitCraftConverter<F, B, A> register(Class<A> apiType, TypeToken<T> implType, TypeToken<B> bukkitType, Function<B, F> getter, Function<F, B> wrapper) {
        BukkitCraftConverter<F, B, T> converter = new BukkitCraftConverter<>(bukkitType, implType, getter, wrapper);
        conversionService.registerConverterForNewImplType(apiType, converter);
        return (BukkitCraftConverter<F, B, A>) converter;
    }

    private static <F, T> MCCConverter<F, T> register(MCCConverter<F, T> converter) {
        conversionService.registerConverterForNewImplType(converter.apiImplementationClass(), converter);
        return converter;
    }

    @TestOnly
    public static ConversionService getConversionService() {
        return conversionService;
    }
}
