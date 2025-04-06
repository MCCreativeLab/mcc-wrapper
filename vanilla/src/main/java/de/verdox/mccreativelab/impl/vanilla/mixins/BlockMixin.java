package de.verdox.mccreativelab.impl.vanilla.mixins;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(Block.class)
public class BlockMixin {
    @Redirect(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/StateDefinition$Builder;create(Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/StateDefinition$Factory;)Lnet/minecraft/world/level/block/state/StateDefinition;"
            )
    )
    private StateDefinition<Block, BlockState> redirectStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder,
            Function<Block, BlockState> defaultStateSupplier,
            StateDefinition.Factory<Block, BlockState> stateFunction) {
        return builder.create(defaultStateSupplier, ProxyBlockState::new);
    }
}
