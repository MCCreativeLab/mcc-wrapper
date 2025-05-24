package de.verdox.mccreativelab.impl.vanilla.mixins;

import de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.ProxyBlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class MixinBlockStateBase {

    @Inject(
            method = "isRandomlyTicking",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyIsRandomlyTicking(CallbackInfoReturnable<Boolean> cir) {
        if(((Object) this instanceof ProxyBlockState proxyBlockState)) {
            if(proxyBlockState.isProxy()) {
                cir.setReturnValue(proxyBlockState.hasRandomTicks());
            }
        }
    }
}
