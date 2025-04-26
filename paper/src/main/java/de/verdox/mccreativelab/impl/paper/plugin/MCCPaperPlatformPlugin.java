package de.verdox.mccreativelab.impl.paper.plugin;

import de.verdox.mccreativelab.generator.resourcepack.types.hud.renderer.HudRenderer;
import de.verdox.mccreativelab.impl.paper.pack.PaperGeneratorHelper;
import de.verdox.mccreativelab.impl.paper.platform.PaperPlatform;
import de.verdox.mccreativelab.impl.paper.platform.commands.RegistryLookUpCommand;
import de.verdox.mccreativelab.platform.GeneratorPlatformHelper;
import de.verdox.mccreativelab.platform.PlatformResourcePack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

public class MCCPaperPlatformPlugin extends JavaPlugin implements Listener {

    private PaperPlatform platform;

    @Override
    public void onLoad() {
        platform = (PaperPlatform) MCCPlatform.getInstance();
        try {
            platform.getResourcePackManager().init(platform);
            GeneratorPlatformHelper.INSTANCE.setup(platform.getResourcePackManager().getHelper(), platformHelper -> {
            });
            platform.getResourcePackManager().getResourcePack().initialize();
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "An error occurred while initializing mcc-platform", e);
            platform.shutdown();
            return;
        }
        if (platform.getResourcePackManager().getHelper() instanceof PaperGeneratorHelper helper) {
            helper.setJavaPlugin(this);
        }
    }

    @Override
    public void onEnable() {
        platform.enableListeners(this);
        Bukkit.getPluginManager().registerEvents(this, this);


        Bukkit.getCommandMap().register("", new RegistryLookUpCommand<>("rpMenu", PlatformResourcePack.INSTANCE.get().getResourcePackMapper().getMenuRegistry().get(), (player, menu) -> menu.createMenuForPlayer(player)));
        Bukkit.getCommandMap().register("", new RegistryLookUpCommand<>("rpGui", PlatformResourcePack.INSTANCE.get().getResourcePackMapper().getGuiRegistry().get(), (player, gui) -> gui.createMenuForPlayer(player)));
        Bukkit.getCommandMap().register("", new RegistryLookUpCommand<>("rpHud", PlatformResourcePack.INSTANCE.get().getResourcePackMapper().getHudRegistry().get(), (player, hud) -> {
            HudRenderer hudRenderer = GeneratorPlatformHelper.INSTANCE.get().getHudRenderer();
            var active = hudRenderer.getActiveHud(player, hud);
            if (active == null) {
                hudRenderer.getOrStartActiveHud(player, hud);
                player.sendMessage(Component.text("Starting hud " + hud.key() + " for player " + player.getPlayerName()));
            } else {
                hudRenderer.stopActiveHud(player, hud);
                player.sendMessage(Component.text("Stopping hud " + hud.key() + " for player " + player.getPlayerName()));
            }
        }));
    }

    @EventHandler
    public void startup(ServerLoadEvent e) {
        if (e.getType().equals(ServerLoadEvent.LoadType.STARTUP)) {
            MCCPlatform.getInstance().triggerLifecycleEvent(MCCPlatform.Lifecycle.SERVER_STARTUP_COMPLETE);
        }
    }

    @EventHandler
    public void afterLastWorldWasLoaded(WorldInitEvent e) {
        if (Bukkit.getWorlds().getFirst().equals(e.getWorld())) {
            MCCPlatform.getInstance().triggerLifecycleEvent(MCCPlatform.Lifecycle.BEFORE_WORLD_LOAD);
        }
    }

    @EventHandler
    public void afterLastWorldWasLoaded(WorldLoadEvent e) {
        if (Bukkit.getWorlds().getLast().equals(e.getWorld())) {
            MCCPlatform.getInstance().triggerLifecycleEvent(MCCPlatform.Lifecycle.AFTER_WORLD_LOAD);
        }
    }
}
