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
// TODO: MCCEntityAirChangeEvent
// TODO: MCCEntityBreakDoorEvent
// TODO: MCCEntityChangeBlockEvent
// TODO: MCCEntityCombustByBlockEvent
// TODO: MCCEntityCombustByEntityEvent
// TODO: MCCEntityCombustEvent
// TODO: MCCEntityDismountEvent
// TODO: MCCEntityDropItemEvent
// TODO: MCCEntityEnterBlockEvent
// TODO: MCCEntityEnterLoveModeEvent
// TODO: MCCEntityEvent
// TODO: MCCEntityInteractEvent
// TODO: MCCEntityMountEvent
// TODO: MCCEntityPickupItemEvent
// TODO: MCCEntityPlaceEvent
// TODO: MCCEntityResurrectEvent
// TODO: MCCEntitySpawnEvent
// TODO: MCCEntityTeleportEvent
// TODO: MCCEntityToggleGlideEvent
// TODO: MCCEntityToggleSwimEvent
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
    }
}
