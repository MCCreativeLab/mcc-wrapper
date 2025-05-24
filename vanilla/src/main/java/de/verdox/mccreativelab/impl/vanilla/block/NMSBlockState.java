package de.verdox.mccreativelab.impl.vanilla.block;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStateProperty;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NMSBlockState extends MCCHandle<BlockState> implements MCCBlockState {
    public static final MCCConverter<BlockState, NMSBlockState> CONVERTER = converter(NMSBlockState.class, BlockState.class, NMSBlockState::new, MCCHandle::getHandle);

    public NMSBlockState(BlockState handle) {
        super(handle);
    }

    @Override
    public @NotNull MCCBlockType getBlockType() {
        return MCCPlatform.getInstance().getConversionService().wrap(handle.getBlock(), new TypeToken<>() {});
    }

    @Override
    public String toBlockDataString() {
        return handle.toString();
    }

    @Override
    public float getBlockHardness(MCCPlayer player) {
        Player player1 = conversionService.unwrap(player);
        return player1.getDestroySpeed(handle);
    }

    @Override
    public @NotNull List<MCCItemStack> getDrops(@NotNull MCCLocation mccLocation, @Nullable MCCEntity entity, @Nullable MCCItemStack tool) {
        ItemStack nms = conversionService.unwrap(tool, new TypeToken<>() {});

        ServerLevel serverLevel = conversionService.unwrap(mccLocation.world(), new TypeToken<>() {});
        BlockPos pos = new BlockPos(mccLocation.blockX(), mccLocation.blockY(), mccLocation.blockZ());
        Entity nmsEntity = conversionService.unwrap(entity, new TypeToken<>() {});

        if (tool == null || isPreferredTool(tool)) {
            return Block.getDrops(handle, serverLevel, pos, serverLevel.getBlockEntity(pos), nmsEntity, nms).stream().map(itemStack -> conversionService.wrap(itemStack, new TypeToken<MCCItemStack>() {})).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean isRandomlyTicking() {
        return handle.isRandomlyTicking();
    }

    @Override
    public boolean requiresCorrectToolForDrops() {
        return handle.requiresCorrectToolForDrops();
    }

    @Override
    public <T extends Comparable<T>> boolean hasProperty(MCCBlockStateProperty<T> property) {
        return handle.hasProperty(conversionService.unwrap(property));
    }

    @Override
    public <T extends Comparable<T>> T getValue(MCCBlockStateProperty<T> property) {
        if (!hasProperty(property)) {
            return null;
        }
        return handle.getValue(conversionService.unwrap(property));
    }

    @Override
    public <T extends Comparable<T>> MCCBlockState newState(MCCBlockStateProperty<T> property, T value) {
        return conversionService.wrap(handle.trySetValue(conversionService.unwrap(property, new TypeToken<Property<T>>() {}), conversionService.unwrap(value)), new TypeToken<>() {});
    }
}
