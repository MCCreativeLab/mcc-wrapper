package de.verdox.mccreativelab.impl.paper.mixins;

import de.verdox.mccreativelab.impl.paper.plugin.MCCPaperPluginLoader;
import io.papermc.paper.plugin.bootstrap.PluginBootstrapContextImpl;
import io.papermc.paper.plugin.storage.BootstrapProviderStorage;
import io.papermc.paper.plugin.provider.PluginProvider;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// TODO: test this mixin
@SuppressWarnings("UnstableApiUsage")
@Mixin(value = BootstrapProviderStorage.class, targets = "io/papermc/paper/plugin/storage/BootstrapProviderStorage$1")
public class MixinBootstrapProviderStorage {

    @SuppressWarnings("MixinAnnotationTarget")
    @Inject(method = "load*", at = @At(value = "RETURN"))
    public void injectLoad(PluginProvider<PluginBootstrap> provider, PluginBootstrap provided, CallbackInfoReturnable<Boolean> ci, PluginBootstrapContextImpl context) {
        new MCCPaperPluginLoader().bootstrap(context);
    }
}