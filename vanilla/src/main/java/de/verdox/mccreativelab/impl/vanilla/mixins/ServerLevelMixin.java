package de.verdox.mccreativelab.impl.vanilla.mixins;

import de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.ProxyBlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Redirect(
            method = "tickPrecipitation",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;handlePrecipitation(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/biome/Biome$Precipitation;)V"
            )
    )
    private void redirectHandlePrecipitation(Block instance, BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        if(state instanceof ProxyBlockState proxyBlockState && proxyBlockState.isProxy()) {
            proxyBlockState.handlePrecipitation(level, pos, precipitation);
        }
        else {
            instance.handlePrecipitation(state, level, pos, precipitation);
        }
    }
}
