package de.verdox.mccreativelab.impl.vanilla.mixins;

import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltInRegistries.class)
public class BootstrapMixin {
    @Inject(method = "bootStrap", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/registries/BuiltInRegistries;freeze()V"))
    private static void bootStrap(CallbackInfo info) {
        System.out.println("BEFORE FREEZE");
    }
}
