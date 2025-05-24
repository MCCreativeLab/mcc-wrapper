package de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy;

import com.google.common.reflect.TypeToken;
import com.mojang.serialization.MapCodec;
import de.verdox.mccreativelab.gamefactory.block.MCCCustomBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.inventory.MCCMenuProvider;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.misc.MCCRandomSource;
import de.verdox.mccreativelab.wrapper.misc.MCCStateMirror;
import de.verdox.mccreativelab.wrapper.misc.MCCStateRotation;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyBlockState extends BlockState implements GameProxy {

    public static final Map<BlockState, MCCCustomBlockState> replaced = new HashMap<>();

    public ProxyBlockState(Block block, Reference2ObjectArrayMap<Property<?>, Comparable<?>> reference2ObjectArrayMap, MapCodec<BlockState> mapCodec) {
        super(block, reference2ObjectArrayMap, mapCodec);
    }


    //We don't use Override here because in paper this method is final
    public boolean hasRandomTicks() {
        return proxy(super::isRandomlyTicking, () -> getProxy().isRandomlyTicking());
    }

    @Override
    public boolean requiresCorrectToolForDrops() {
        return proxy(super::requiresCorrectToolForDrops, () -> getProxy().requiresCorrectToolForDrops());
    }

    @Override
    public void spawnAfterBreak(ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        proxy(
                super::spawnAfterBreak,
                param(level, MCCWorld.class),
                param(pos),
                param(stack, MCCItemStack.class),
                param(dropExperience),
                (world, blockPos, itemStack, aBoolean) -> getProxy().spawnAfterBreak(new MCCLocation(world, blockPos.getX(), blockPos.getY(), blockPos.getZ()), itemStack, aBoolean)
        );
    }

    @Override
    public void attack(Level level, BlockPos pos, Player player) {
        proxy(
                super::attack,
                param(level, MCCWorld.class),
                param(pos),
                param(player, MCCPlayer.class),
                (world, blockPos, mccPlayer) -> getProxy().attack(new MCCLocation(world, blockPos.getX(), blockPos.getY(), blockPos.getZ()), mccPlayer)
        );
    }

    @Override
    public void tick(ServerLevel level, BlockPos pos, RandomSource random) {
        proxy(
                super::tick,
                param(level, MCCWorld.class),
                param(pos),
                param(random, MCCRandomSource.class),
                (world, blockPos, randomSource) -> getProxy().tick(new MCCLocation(world, blockPos.getX(), blockPos.getY(), blockPos.getZ()), randomSource)
        );
    }

    @Override
    public void randomTick(ServerLevel level, BlockPos pos, RandomSource random) {
        proxy(
                super::randomTick,
                param(level, MCCWorld.class),
                param(pos),
                param(random, MCCRandomSource.class),
                (world, blockPos, randomSource) -> getProxy().randomTick(new MCCLocation(world, blockPos.getX(), blockPos.getY(), blockPos.getZ()), randomSource)
        );
    }

    @Override
    public boolean canSurvive(LevelReader level, BlockPos pos) {
        return proxy(
                super::canSurvive,
                param(level, MCCWorld.class),
                param(pos),
                (world, blockPos) -> {
                    return getProxy().canSurvive(new MCCLocation(world, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                }
        );
    }

    @Override
    public BlockState mirror(Mirror mirror) {
        return proxy(
                super::mirror,
                param(mirror, MCCStateMirror.class),
                (mccMirror) -> {
                    return getProxy().mirror(mccMirror);
                }
        );
    }

    @Override
    public BlockState rotate(Rotation rotation) {
        return proxy(
                super::rotate,
                param(rotation, MCCStateRotation.class),
                (mccRotation) -> {
                    return getProxy().rotate(mccRotation);
                }
        );
    }

    @Override
    public float getDestroyProgress(Player player, BlockGetter level, BlockPos pos) {
        return proxy(
                super::getDestroyProgress,
                param(player, MCCPlayer.class),
                param(level, MCCWorld.class),
                param(pos),
                (mccPlayer, world, blockPos) -> {
                    return getProxy().getBlockHardness(mccPlayer, new MCCLocation(world, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                }
        );
    }

    @Override
    public float getDestroySpeed(BlockGetter level, BlockPos pos) {
        return proxy(
                super::getDestroySpeed,
                param(level, MCCWorld.class),
                param(pos),
                (world, blockPos) -> {
                    return getProxy().getBlockHardness(new MCCLocation(world, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                }
        );
    }

    @Override
    public void onPlace(Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        proxy(
                super::onPlace,
                param(level, MCCWorld.class),
                param(pos),
                param(oldState, MCCBlockState.class),
                param(movedByPiston),
                (world, blockPos, blockState, moved) -> {
                    getProxy().onPlace(new MCCLocation(world, blockPos.getX(), blockPos.getY(), blockPos.getZ()), blockState, moved);
                }
        );
    }

    @Override
    public void onRemove(Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        proxy(
                super::onRemove,
                param(level, MCCWorld.class),
                param(pos),
                param(newState, MCCBlockState.class),
                param(movedByPiston),
                (world, blockPos, blockState, moved) -> {
                    getProxy().onRemove(new MCCLocation(world, blockPos.getX(), blockPos.getY(), blockPos.getZ()), blockState, moved);
                }
        );
    }

    @Override
    public List<ItemStack> getDrops(LootParams.Builder lootParams) {
        return proxy(
                super::getDrops,
                param(lootParams),
                (parameters) -> {
                    ServerLevel world = parameters.getLevel();
                    Vec3 origin = parameters.getParameter(LootContextParams.ORIGIN);
                    ItemStack tool = parameters.getParameter(LootContextParams.TOOL);
                    Entity entity = parameters.getOptionalParameter(LootContextParams.THIS_ENTITY);
                    //TODO Add Support for block entity
                    BlockEntity blockEntity = parameters.getOptionalParameter(LootContextParams.BLOCK_ENTITY);

                    MCCWorld mccWorld = getConversionService().wrap(world, new TypeToken<>() {});

                    return getProxy().getDrops(
                            new MCCLocation(mccWorld, origin.x(), origin.y(), origin.z()),
                            getConversionService().wrap(entity, new TypeToken<>() {}),
                            getConversionService().wrap(tool, new TypeToken<>() {})
                    );
                }
        );
    }

    @Override
    public @Nullable MenuProvider getMenuProvider(Level level, BlockPos pos) {
        MCCMenuProvider<?> menuProvider = getProxy().getMenuProvider(new MCCLocation(conversionService().wrap(level, new TypeToken<>() {}), pos.getX(), pos.getY(), pos.getZ()));
        if (menuProvider == null) {
            return null;
        }
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return conversionService().unwrap(menuProvider.getTitle());
            }

            @Override
            public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                return conversionService().unwrap(menuProvider.getCreatorInstance().createMenuForPlayer(conversionService().wrap(player, new TypeToken<>() {}), menuProvider.getTitle()));
            }
        };
    }

    public void handlePrecipitation(Level level, BlockPos pos, Biome.Precipitation precipitation) {
        getProxy().handlePrecipitation(new MCCLocation(conversionService().wrap(level, new TypeToken<>() {}), pos.getX(), pos.getY(), pos.getZ()), conversionService().wrap(precipitation, new TypeToken<>() {}));
    }

    public void stepOn(Level level, BlockPos pos, Entity entity) {
        getProxy().stepOn(new MCCLocation(conversionService().wrap(level, new TypeToken<>() {}), pos.getX(), pos.getY(), pos.getZ()), conversionService().wrap(entity, new TypeToken<>() {}));
    }

    @Override
    public void entityInside(Level level, BlockPos pos, Entity entity) {
        proxy(
                super::entityInside,
                param(level, MCCWorld.class),
                param(pos),
                param(entity, MCCEntity.class),
                (world, blockPos, mccEntity) -> {
                    getProxy().entityInside(new MCCLocation(world, blockPos.getX(), blockPos.getY(), blockPos.getZ()), mccEntity);
                }
        );
    }

    @Override
    public NoteBlockInstrument instrument() {
        return proxy(
                super::instrument,
                () -> getProxy().getBlockType().getBlockProperties().instrument()
        );
    }

    @Override
    public boolean isProxy() {
        return replaced.containsKey(this);
    }

    private MCCCustomBlockState getProxy() {
        return replaced.get(this);
    }
}
