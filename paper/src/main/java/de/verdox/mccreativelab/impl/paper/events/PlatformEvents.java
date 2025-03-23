package de.verdox.mccreativelab.impl.paper.events;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PlatformEvents {

// TODO: MCCFluidLevelChangeEvent
// TODO: MCCLeavesDecayEvent
// TODO: MCCSculkBloomEvent
// TODO: MCCAreaEffectCloudApplyEvent
// TODO: MCCArrowBodyCountChangeEvent
// TODO: MCCBatToggleSleepEvent
// TODO: MCCExpBottleEvent
// TODO: MCCExplosionPrimeEvent
// TODO: MCCFireworkExplodeEvent
// TODO: MCCHorseJumpEvent
// TODO: MCCPigZombieAngerEvent
// TODO: MCCPlayerLeashEntityEvent
// TODO: MCCPotionSplashEvent
// TODO: MCCSheepRegrowWoolEvent
// TODO: MCCSlimeSplitEvent
// TODO: MCCStriderTemperatureChangeEvent
// TODO: MCCHangingEvent
// TODO: MCCFurnaceExtractEvent
// TODO: MCCWeatherEvent
// TODO: MCCSpawnChangeEvent

    public static void init(Plugin plugin) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new WorldEvents(), plugin);
        pluginManager.registerEvents(new PlayerEvents(), plugin);
        pluginManager.registerEvents(new ItemEvents(), plugin);
        pluginManager.registerEvents(new BellEvents(), plugin);
        pluginManager.registerEvents(new ProjectileEvents(), plugin);
        pluginManager.registerEvents(new ServerEvents(), plugin);
        pluginManager.registerEvents(new VehicleEvents(), plugin);
        pluginManager.registerEvents(new BlockEvents(), plugin);
        pluginManager.registerEvents(new BlockEvents.BlockPistonEvents(), plugin);
        pluginManager.registerEvents(new EntityEvents(), plugin);
        pluginManager.registerEvents(new EntityEvents.EntityCombustEvents(), plugin);
        pluginManager.registerEvents(new EntityEvents.EntityEnterEvents(), plugin);
        pluginManager.registerEvents(new EntityEvents.EntityToggleEvents(), plugin);

    }
}
