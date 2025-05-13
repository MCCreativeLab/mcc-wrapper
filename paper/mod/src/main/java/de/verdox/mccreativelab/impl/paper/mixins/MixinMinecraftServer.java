package de.verdox.mccreativelab.impl.paper.mixins;

import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for injecting into the MinecraftServer class.
 */
@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {

    /**
     * This method is used to stop the server.
     * It is shadowed from the MinecraftServer class.
     */
    @Shadow public abstract void stopServer();

    /**
     * Injects into the runServer method of MinecraftServer to check for the presence of the MCCPaperPlatform plugin.
     * @param ci The callback information.
     */
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;initServer()Z", shift = At.Shift.AFTER), method = "runServer")
    public void injectRunServer(CallbackInfo ci) {
        System.out.println("Minecraft server is starting...");
        if (Bukkit.getPluginManager().getPlugin("MCCPaperPlatform") == null) {
            System.err.println("MCCPaperPlatform plugin not found! Shutting down server.");
            stopServer();
        }
    }
}
