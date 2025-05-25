package de.verdox.mccreativelab.impl.paper.platform.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.conversion.ConversionServiceImpl;
import de.verdox.mccreativelab.conversion.converter.EnumConverter;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.block.NMSBlockState;
import de.verdox.mccreativelab.impl.vanilla.block.NMSBlockType;
import de.verdox.mccreativelab.impl.vanilla.entity.NMSEntity;
import de.verdox.mccreativelab.impl.vanilla.entity.NMSEntityType;
import de.verdox.mccreativelab.impl.vanilla.entity.types.NMSPlayer;
import de.verdox.mccreativelab.impl.vanilla.inventory.NMSContainer;
import de.verdox.mccreativelab.impl.vanilla.item.NMSItemStack;
import de.verdox.mccreativelab.impl.vanilla.item.NMSItemType;
import de.verdox.mccreativelab.impl.vanilla.world.NMSWorld;
import de.verdox.mccreativelab.impl.vanilla.world.chunk.NMSChunk;
import de.verdox.mccreativelab.wrapper.block.*;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlot;
import de.verdox.mccreativelab.wrapper.entity.MCCTeleportFlag;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import de.verdox.mccreativelab.wrapper.world.coordinates.Pos;
import io.papermc.paper.entity.TeleportFlag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftEntityType;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.inventory.CraftItemType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class BukkitAdapter {

    private static final ConversionService conversionService = new ConversionServiceImpl();

    public static final EnumConverter<TeleportFlag.EntityState, MCCTeleportFlag> TELEPORT_FLAG = new EnumConverter<>(TeleportFlag.EntityState.class, MCCTeleportFlag.class);
    public static final EnumConverter<BlockFace, MCCBlockFace> BLOCK_FACE = new EnumConverter<>(BlockFace.class, MCCBlockFace.class);
    public static final EnumConverter<EquipmentSlot, MCCEquipmentSlot> EQUIPMENT_SLOT = new EnumConverter<>(EquipmentSlot.class, MCCEquipmentSlot.class);
    public static final CraftBlockStateConverter BLOCK_STATE = new CraftBlockStateConverter();

    public static void init() {
        conversionService.registerConverterForNewImplType(MCCCapturedBlockState.class, BLOCK_STATE);
        conversionService.registerConverterForNewImplType(MCCEquipmentSlot.class, EQUIPMENT_SLOT);
        conversionService.registerConverterForNewImplType(MCCBlockFace.class, BLOCK_FACE);
        conversionService.registerConverterForNewImplType(MCCTeleportFlag.class, TELEPORT_FLAG);
    }


    static final BukkitCraftConverter<ItemStack, CraftItemStack, MCCItemStack, NMSItemStack> ITEM_STACK = register(MCCItemStack.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftItemStack::asNMSCopy, itemStack -> (CraftItemStack) itemStack.getBukkitStack());
    static final BukkitCraftConverter<ServerLevel, CraftWorld, MCCWorld, NMSWorld> WORLD = register(MCCWorld.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftWorld::getHandle, Level::getWorld);
    static final BukkitCraftConverter<Container, CraftInventory, MCCContainer, NMSContainer> CONTAINER = register(MCCContainer.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftInventory::getInventory, CraftInventory::new);
    static final BukkitCraftConverter<Entity, CraftEntity, MCCEntity, NMSEntity<?>> ENTITY = register(MCCEntity.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftEntity::getHandle, Entity::getBukkitEntity);
    static final BukkitCraftConverter<Player, CraftPlayer, MCCPlayer, NMSPlayer> PLAYER = register(MCCPlayer.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftPlayer::getHandle, (player) -> (CraftPlayer) player.getBukkitEntity());
    static final BukkitCraftConverter<LevelChunk, CraftChunk, MCCChunk, NMSChunk> CHUNK = register(MCCChunk.class, new TypeToken<>() {}, new TypeToken<>() {}, craftChunk -> (LevelChunk) craftChunk.getHandle(ChunkStatus.FULL), CraftChunk::new);
    static final BukkitCraftConverter<BlockState, CraftBlockData, MCCBlockState, NMSBlockState> BLOCK_DATA = register(MCCBlockState.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftBlockData::getState, CraftBlockData::createData);
    static final BukkitCraftConverter<EntityType, org.bukkit.entity.EntityType, MCCEntityType, NMSEntityType> ENTITY_TYPE = register(MCCEntityType.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftEntityType::bukkitToMinecraft, CraftEntityType::minecraftToBukkit);
    static final MCCConverter<Location, MCCLocation> LOCATION = register(new BukkitLocationConverter());
    static final MCCConverter<org.bukkit.block.Block, MCCBlock> BLOCK = register(new BukkitBlockConverter());

    static final BukkitCraftConverter<Block, BlockType, MCCBlockType, NMSBlockType> BLOCK_TYPE = register(MCCBlockType.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftBlockType::bukkitToMinecraftNew, CraftBlockType::minecraftToBukkitNew);
    static final BukkitCraftConverter<Item, ItemType, MCCItemType, NMSItemType> ITEM_TYPE = register(MCCItemType.class, new TypeToken<>() {}, new TypeToken<>() {}, CraftItemType::bukkitToMinecraftNew, CraftItemType::minecraftToBukkitNew);

    public static <
            BUKKIT,
            MCC,
            BUKKIT_COL extends Collection<BUKKIT>,
            MCC_COL extends Collection<MCC>>
    MCC_COL toMcc(BUKKIT_COL collection, Function<BUKKIT, MCC> toMcc, Supplier<MCC_COL> collectionCreator) {
        MCC_COL mccCollection = collectionCreator.get();

        for (BUKKIT bukkit : collection) {
            mccCollection.add(toMcc.apply(bukkit));
        }
        return mccCollection;
    }

    public static <
            BUKKIT,
            MCC,
            BUKKIT_COL extends Collection<BUKKIT>,
            MCC_COL extends Collection<MCC>>
    BUKKIT_COL toBukkit(MCC_COL collection, Function<MCC, BUKKIT> toBukkit, Supplier<BUKKIT_COL> collectionCreator) {
        BUKKIT_COL bukkitCollection = collectionCreator.get();

        for (MCC mcc : collection) {
            bukkitCollection.add(toBukkit.apply(mcc));
        }
        return bukkitCollection;
    }

    // PLAYER

    public static MCCPlayer toMcc(HumanEntity bukkit) {
        if(bukkit == null) return null;
        CraftPlayer craft = (CraftPlayer) bukkit;
        return MCCPlatform.getInstance().getConversionService().wrap(craft.getHandle(), MCCPlayer.class);
    }

    public static HumanEntity toBukkit(MCCPlayer mcc) {
        if(mcc == null) return null;
        NMSPlayer nms = (NMSPlayer) mcc;
        return nms.getHandle().getBukkitEntity();
    }

    // ENTITY

    public static MCCEntity toMcc(org.bukkit.entity.Entity bukkit) {
        if(bukkit == null) return null;
        CraftEntity craft = (CraftEntity) bukkit;
        return MCCPlatform.getInstance().getConversionService().wrap(craft.getHandle(), MCCEntity.class);
    }

    public static org.bukkit.entity.Entity toBukkit(MCCEntity mcc) {
        if(mcc == null) return null;
        NMSEntity<?> nms = (NMSEntity<?>) mcc;
        return nms.getHandle().getBukkitEntity();
    }

    // ITEMSTACK

    public static MCCItemStack toMcc(org.bukkit.inventory.ItemStack bukkit) {
        if(bukkit == null) return null;
        CraftItemStack craft = (CraftItemStack) bukkit;
        return MCCPlatform.getInstance().getConversionService().wrap(craft.handle, MCCItemStack.class);
    }

    public static org.bukkit.inventory.ItemStack toBukkit(MCCItemStack mcc) {
        if(mcc == null) return null;
        NMSItemStack nms = (NMSItemStack) mcc;
        return nms.getHandle().asBukkitMirror();
    }

    // BLOCK

    public static MCCBlock toMcc(org.bukkit.block.Block bukkit) {
        if(bukkit == null) return null;
        CraftBlock craft = (CraftBlock) bukkit;
        return new BukkitBlockConverter().wrap(craft).value();
    }

    public static org.bukkit.block.Block toBukkit(MCCBlock mcc) {
        if(mcc == null) return null;
        return toBukkit(mcc.getWorld()).getBlockAt(toBukkit(mcc.getLocation()));
    }

    // INVENTORY

    public static MCCContainer toMcc(Inventory bukkit) {
        if(bukkit == null) return null;
        CraftInventory craft = (CraftInventory) bukkit;
        return MCCPlatform.getInstance().getConversionService().wrap(craft.getInventory(), MCCContainer.class);
    }

    // CHUNK

    public static MCCChunk toMcc(Chunk bukkit) {
        if(bukkit == null) return null;
        CraftChunk craft = (CraftChunk) bukkit;
        return MCCPlatform.getInstance().getConversionService().wrap(craft.getHandle(ChunkStatus.FULL), MCCChunk.class);
    }

    // WORLD

    public static MCCWorld toMcc(World bukkit) {
        if(bukkit == null) return null;
        CraftWorld craft = (CraftWorld) bukkit;
        return MCCPlatform.getInstance().getConversionService().wrap(craft.getHandle(), MCCWorld.class);
    }

    public static World toBukkit(MCCWorld mcc) {
        if(mcc == null) return null;
        NMSWorld nms = (NMSWorld) mcc;
        return nms.getHandle().getWorld();
    }

    // LOCATION

    public static MCCLocation toMcc(Location bukkit) {
        if(bukkit == null) return null;
        return new BukkitLocationConverter().wrap(bukkit).value();
    }

    public static Location toBukkit(MCCLocation mcc) {
        if(mcc == null) return null;
        return new BukkitLocationConverter().unwrap(mcc).value();
    }

    public static Location toBukkit(MCCWorld mccWorld, Pos<?> pos) {
        if(pos == null) return null;
        if (pos instanceof MCCLocation location) {
            return toBukkit(location);
        }
        if(mccWorld == null) return null;
        return toBukkit(new MCCLocation(mccWorld, pos.toPos()));
    }

    //

    public static TeleportFlag.EntityState toBukkit(MCCTeleportFlag mcc) {
        if(mcc == null) return null;
        return new EnumConverter<>(TeleportFlag.EntityState.class, MCCTeleportFlag.class).unwrap(mcc).value();
    }

    public static MCCBlockFace toMcc(BlockFace bukkit) {
        if(bukkit == null) return null;
        return BLOCK_FACE.wrap(bukkit).value();
    }

    public static MCCBlockState toMcc(BlockData bukkit) {
        if(bukkit == null) return null;
        return BLOCK_DATA.wrap((CraftBlockData) bukkit).value();
    }

    public static MCCEquipmentSlot toMcc(EquipmentSlot bukkit) {
        return EQUIPMENT_SLOT.wrap(bukkit).value();
    }

    public static MCCBlockType toMcc(BlockType bukkit) {
        if(bukkit == null) return null;
        CraftBlockType<?> craftBlockType = (CraftBlockType<?>) bukkit;
        return conversionService.wrap(craftBlockType.getHandle(), MCCBlockType.class);
    }

    public static MCCItemType toMcc(ItemType bukkit) {
        if(bukkit == null) return null;
        CraftItemType<?> craftItemType = (CraftItemType<?>) bukkit;
        return conversionService.wrap(craftItemType.getHandle(), MCCItemType.class);
    }

    public static MCCEntityType<?> toMcc(org.bukkit.entity.EntityType bukkit) {
        if(bukkit == null) return null;
        return ENTITY_TYPE.wrap(bukkit).value();
    }

    @Deprecated
    private static <NATIVE, CRAFT_TYPE, API, API_IMPL extends API> BukkitCraftConverter<NATIVE, CRAFT_TYPE, API, API_IMPL> register(TypeToken<API> apiType, TypeToken<API_IMPL> implType, TypeToken<CRAFT_TYPE> bukkitType, Function<CRAFT_TYPE, NATIVE> getter, Function<NATIVE, CRAFT_TYPE> wrapper) {
        BukkitCraftConverter<NATIVE, CRAFT_TYPE, API, API_IMPL> converter = new BukkitCraftConverter<>(bukkitType, apiType, implType, getter, wrapper);
        conversionService.registerConverterForNewImplType(apiType.getRawType(), converter);
        return converter;
    }

    @Deprecated
    private static <NATIVE, CRAFT_TYPE, API, API_IMPL extends API> BukkitCraftConverter<NATIVE, CRAFT_TYPE, API, API_IMPL> register(Class<API> apiType, TypeToken<API_IMPL> implType, TypeToken<CRAFT_TYPE> bukkitType, Function<CRAFT_TYPE, NATIVE> getter, Function<NATIVE, CRAFT_TYPE> wrapper) {
        BukkitCraftConverter<NATIVE, CRAFT_TYPE, API, API_IMPL> converter = new BukkitCraftConverter<>(bukkitType, TypeToken.of(apiType), implType, getter, wrapper);
        conversionService.registerConverterForNewImplType(apiType, converter);
        return converter;
    }

    @Deprecated
    private static <F, T> MCCConverter<F, T> register(MCCConverter<F, T> converter) {
        conversionService.registerConverterForNewImplType(converter.apiImplementationClass(), converter);
        return converter;
    }

    @TestOnly
    public static ConversionService getConversionService() {
        return conversionService;
    }
}
