package de.verdox.mccreativelab.impl.vanilla.mixins;

import de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.ProxyBlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class EntityMixin {
    @Redirect(
            method = "applyEffectsFromBlocks(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;stepOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/Entity;)V"
            )
    )
    public void stepOn(Block instance, Level level, BlockPos pos, BlockState state, Entity entity) {
        if(state instanceof ProxyBlockState proxyBlockState && proxyBlockState.isProxy()) {
            proxyBlockState.stepOn(level, pos, entity);
        }
        else {
            instance.stepOn(level, pos, state, entity);
        }
    }
}
