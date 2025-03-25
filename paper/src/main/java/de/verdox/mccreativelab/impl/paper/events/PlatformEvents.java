package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.event.block.MCCFluidLevelChangeEvent;
import de.verdox.mccreativelab.wrapper.event.block.MCCLeavesDecayEvent;
import de.verdox.mccreativelab.wrapper.event.block.MCCSculkBloomEvent;
import de.verdox.mccreativelab.wrapper.event.entity.*;
import de.verdox.mccreativelab.wrapper.event.hanging.MCCHangingEvent;
import de.verdox.mccreativelab.wrapper.event.inventory.MCCFurnaceExtractEvent;
import de.verdox.mccreativelab.wrapper.event.weather.MCCWeatherEvent;
import de.verdox.mccreativelab.wrapper.event.world.MCCSpawnChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.FluidLevelChangeEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SculkBloomEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.weather.WeatherEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PlatformEvents extends EventBase {

    public static void init(Plugin plugin) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new PlatformEvents(), plugin);
        pluginManager.registerEvents(new WorldEvents(), plugin);
        pluginManager.registerEvents(new PlayerEvents(), plugin);
        pluginManager.registerEvents(new ItemEvents(), plugin);
        pluginManager.registerEvents(new BellEvents(), plugin);
        pluginManager.registerEvents(new ProjectileEvents(), plugin);
        pluginManager.registerEvents(new ServerEvents(), plugin);
        pluginManager.registerEvents(new VehicleEvents(), plugin);
        pluginManager.registerEvents(new BlockEvents(), plugin);
        pluginManager.registerEvents(new BlockEvents.Piston(), plugin);
        pluginManager.registerEvents(new EntityEvents(), plugin);
        pluginManager.registerEvents(new EntityEvents.CombustEvents(), plugin);
        pluginManager.registerEvents(new EntityEvents.EntityEnterEvents(), plugin);
        pluginManager.registerEvents(new EntityEvents.ToggleEvents(), plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(FluidLevelChangeEvent event) {
        callEvent(event, new MCCFluidLevelChangeEvent(
                wrap(event.getBlock()),
                event.isCancelled(),
                wrap(event.getNewData())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(LeavesDecayEvent event) {
        callEvent(event, new MCCLeavesDecayEvent(
                        wrap(event.getBlock()),
                        event.isCancelled()
                ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(SculkBloomEvent event) {
        callEvent(event, new MCCSculkBloomEvent(
                        wrap(event.getBlock()),
                        event.isCancelled(),
                        event.getCharge()
                ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(AreaEffectCloudApplyEvent event) {
        callEvent(event, new MCCAreaEffectCloudApplyEvent(
                wrap(event.getEntity()),
                wrap(event.getAffectedEntities()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ArrowBodyCountChangeEvent event) {
        callEvent(event, new MCCArrowBodyCountChangeEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                event.isReset(),
                event.getOldAmount(),
                event.getNewAmount()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BatToggleSleepEvent event) {
        callEvent(event, new MCCBatToggleSleepEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                event.isAwake()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ExpBottleEvent event) {
        callEvent(event, new MCCExpBottleEvent(
                wrap(event.getEntity()),
                wrap(event.getHitEntity()),
                wrap(event.getHitBlock()),
                wrap(event.getHitBlockFace()),
                event.isCancelled(),
                event.getExperience(),
                event.getShowEffect()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ExplosionPrimeEvent event) {
        callEvent(event, new MCCExplosionPrimeEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                event.getRadius(),
                event.getFire()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(FireworkExplodeEvent event) {
        callEvent(event, new MCCFireworkExplodeEvent(
                wrap(event.getEntity()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(HorseJumpEvent event) {
        callEvent(event, new MCCHorseJumpEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                event.getPower()
        ));

        // Why reflection?
        /*if (mccHorseJumpEvent.callEvent()) ReflectionUtils.writeFieldInClass(event, "cancelled", true);*/
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PigZombieAngerEvent event) {
        callEvent(event, new MCCPigZombieAngerEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                wrap(event.getTarget()),
                wrap(event.getNewAnger())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerLeashEntityEvent event) {
        callEvent(event, new MCCPlayerLeashEntityEvent(
                wrap(event.getLeashHolder()),
                wrap(event.getEntity()),
                event.isCancelled(),
                wrap(event.getPlayer()),
                wrap(event.getHand())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PotionSplashEvent event) {
        callEvent(event, new MCCPotionSplashEvent(
                wrap(event.getEntity()),
                wrap(event.getHitEntity()),
                wrap(event.getHitBlock()),
                wrap(event.getHitBlockFace()),
                event.isCancelled(),
                event.isCancelled(), // duplicate
                wrap(event.getAffectedEntities())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(SheepRegrowWoolEvent event) {
        callEvent(event, new MCCSheepRegrowWoolEvent(
                wrap(event.getEntity()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(SlimeSplitEvent event) {
        callEvent(event, new MCCSlimeSplitEvent(
                wrap(event.getEntity()),
                event.isCancelled(),
                event.getCount()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(StriderTemperatureChangeEvent event) {
        callEvent(event, new MCCStriderTemperatureChangeEvent(
                wrap(event.getEntity()),
                event.isShivering(),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(HangingEvent event) {
        callEvent(event, new MCCHangingEvent(
                wrap(event.getEntity())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(FurnaceExtractEvent event) {
        callEvent(event, new MCCFurnaceExtractEvent(
                wrap(event.getBlock()),
                event.getExpToDrop(),
                wrap(event.getPlayer()),
                wrap(event.getItemType()),
                event.getItemAmount()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(WeatherEvent event) {
        callEvent(event, new MCCWeatherEvent(
                wrap(event.getWorld())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(SpawnChangeEvent event) {
        callEvent(event, new MCCSpawnChangeEvent(
                wrap(event.getWorld()),
                wrap(event.getPreviousLocation())
        ));
    }
}
