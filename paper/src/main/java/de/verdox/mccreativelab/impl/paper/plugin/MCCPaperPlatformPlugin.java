package de.verdox.mccreativelab.impl.paper.plugin;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import de.verdox.mccreativelab.gamefactory.MCCGameFactory;
import de.verdox.mccreativelab.generator.resourcepack.types.hud.renderer.HudRenderer;
import de.verdox.mccreativelab.impl.paper.pack.PaperGeneratorHelper;
import de.verdox.mccreativelab.impl.paper.platform.PaperPlatform;
import de.verdox.mccreativelab.impl.paper.platform.commands.RegistryLookUpCommand;
import de.verdox.mccreativelab.impl.vanilla.entity.NMSEntity;
import de.verdox.mccreativelab.impl.vanilla.entity.types.NMSPlayer;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.impl.vanilla.world.NMSWorld;
import de.verdox.mccreativelab.platform.GeneratorPlatformHelper;
import de.verdox.mccreativelab.platform.PlatformResourcePack;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.vserializer.exception.SerializationException;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInputEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

public class MCCPaperPlatformPlugin extends JavaPlugin implements Listener {
    private static MCCPaperPlatformPlugin INSTANCE;

    public static MCCPaperPlatformPlugin getInstance() {
        return INSTANCE;
    }

    private PaperPlatform platform;

    @Override
    public void onLoad() {
        INSTANCE = this;
        platform = (PaperPlatform) MCCPlatform.getInstance();
        platform.getGameFactory().loadAfterBootstrap();
        Bukkit.getServicesManager().register(MCCPlatform.class, platform, this, ServicePriority.Highest);
        try {
            platform.getResourcePackManager().init(platform);
            GeneratorPlatformHelper.INSTANCE.setup(platform.getResourcePackManager().getHelper(), platformHelper -> {
            });
            platform.getResourcePackManager().getResourcePack().initialize();
        } catch (IOException | SerializationException e) {
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

        Bukkit.getCommandMap().register("", new RegistryLookUpCommand<>("customItem", MCCGameFactory.ITEM_REGISTRY.get(), (player, item) -> {
            MCCItemStack stack = item.createItem();
            player.addItemOrDrop(stack);
        }));

        MCCPlatform.LOGGER.info("Block states: " + Block.BLOCK_STATE_REGISTRY.size());
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

    @EventHandler
    public void onServerTick(ServerTickEndEvent e) {
        NMSPlatform nmsPlatform = (NMSPlatform) MCCPlatform.getInstance();
        long tick = e.getTickNumber();
        nmsPlatform.tickSignal().sink().tryEmitNext(tick);

        for (MCCWorld world : nmsPlatform.getWorlds()) {
            for (MCCEntity entity : world.getEntities()) {
                NMSEntity<?> nmsEntity = (NMSEntity<?>) entity;
                nmsEntity.tickSignal().sink().tryEmitNext(tick);
            }
            for (MCCPlayer player : world.getPlayers()) {
                NMSPlayer nmsPlayer = (NMSPlayer) player;
                ServerPlayer serverPlayer = (ServerPlayer) nmsPlayer.getHandle();
                Input input = serverPlayer.getLastClientInput();
                nmsPlayer.tickSignal().sink().tryEmitNext(tick);
                nmsPlayer.inputSignal().sink().tryEmitNext(new MCCPlayer.Input(tick, input.forward(), input.backward(), input.left(), input.right(), input.jump(), input.shift(), input.sprint()));
            }
            NMSWorld nmsWorld = (NMSWorld) world;
            nmsWorld.tickSignal().sink().tryEmitNext(tick);
        }
    }
}
