package de.verdox.mccreativelab.impl.vanilla.mixins;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class ProxyBlockState extends BlockState implements GameProxy {
    public ProxyBlockState(Block block, Reference2ObjectArrayMap<Property<?>, Comparable<?>> reference2ObjectArrayMap, MapCodec<BlockState> mapCodec) {
        super(block, reference2ObjectArrayMap, mapCodec);
    }

    @Override
    public boolean isRandomlyTicking() {
        return proxy(super::isRandomlyTicking, ident(Boolean.class), this::isRandomlyTicking);
    }

    @Override
    public void spawnAfterBreak(ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        return proxy(super::spawnAfterBreak,
                ident(ServerLevel.class),
                ident(BlockPos.class),
                ident(ItemStack.class),
                ident(Boolean.class),
                this::spawnAfterBreak
        );
    }
}
