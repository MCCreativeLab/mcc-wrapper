package de.verdox.mccreativelab.impl.vanilla.mixins.proxy;

import com.mojang.serialization.MapCodec;
import de.verdox.mccreativelab.custom.block.MCCCustomBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ProxyBlockState extends BlockState implements GameProxy {

    public static final Map<BlockState, MCCCustomBlockState> replaced = new HashMap<>();

    public ProxyBlockState(Block block, Reference2ObjectArrayMap<Property<?>, Comparable<?>> reference2ObjectArrayMap, MapCodec<BlockState> mapCodec) {
        super(block, reference2ObjectArrayMap, mapCodec);
    }

    @Override
    public boolean isRandomlyTicking() {
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
                param(random, RandomSource.class),
                (world, blockPos, randomSource) -> getProxy().tick(new MCCLocation(world, blockPos.getX(), blockPos.getY(), blockPos.getZ()), randomSource)
        );
    }

    @Override
    public void randomTick(ServerLevel level, BlockPos pos, RandomSource random) {
        proxy(
                super::randomTick,
                param(level, MCCWorld.class),
                param(pos),
                param(random, RandomSource.class),
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
    public BlockState rotate(Rotation rotation) {
        return proxy(
                super::rotate,
                param(rotation),
                (mccRotation) -> {
                    return getProxy().rotate(90);
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
    public void onExplosionHit(ServerLevel level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> dropConsumer) {
        super.onExplosionHit(level, pos, explosion, dropConsumer);
    }

    @Override
    public void entityInside(Level level, BlockPos pos, Entity entity) {
        super.entityInside(level, pos, entity);
    }

    @Override
    public NoteBlockInstrument instrument() {
        return super.instrument();
    }

    @Override
    public boolean isProxy() {
        return replaced.containsKey(this);
    }

    private MCCCustomBlockState getProxy() {
        return replaced.get(this);
    }
}
