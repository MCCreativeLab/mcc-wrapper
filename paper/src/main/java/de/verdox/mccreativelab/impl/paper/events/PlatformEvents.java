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
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

public class PlatformEvents implements Listener {

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
        pluginManager.registerEvents(new BlockEvents.BlockPistonEvents(), plugin);
        pluginManager.registerEvents(new EntityEvents(), plugin);
        pluginManager.registerEvents(new EntityEvents.EntityCombustEvents(), plugin);
        pluginManager.registerEvents(new EntityEvents.EntityEnterEvents(), plugin);
        pluginManager.registerEvents(new EntityEvents.EntityToggleEvents(), plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(FluidLevelChangeEvent event) {
        MCCFluidLevelChangeEvent mccFluidLevelChangeEvent = new MCCFluidLevelChangeEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getNewData())
        );

        if (mccFluidLevelChangeEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(LeavesDecayEvent event) {
        MCCLeavesDecayEvent mccLeavesDecayEvent = new MCCLeavesDecayEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                event.isCancelled()
        );

        if (mccLeavesDecayEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(SculkBloomEvent event) {
        MCCSculkBloomEvent mccSculkBloomEvent = new MCCSculkBloomEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                event.isCancelled(),
                event.getCharge()
        );

        if (mccSculkBloomEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(AreaEffectCloudApplyEvent event) {
        MCCAreaEffectCloudApplyEvent mccAreaEffectCloudApplyEvent = new MCCAreaEffectCloudApplyEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getAffectedEntities()),
                event.isCancelled()
        );

        if (mccAreaEffectCloudApplyEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ArrowBodyCountChangeEvent event) {
        MCCArrowBodyCountChangeEvent mccArrowBodyCountChangeEvent = new MCCArrowBodyCountChangeEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                event.isReset(),
                event.getOldAmount(),
                event.getNewAmount()
        );

        if (mccArrowBodyCountChangeEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BatToggleSleepEvent event) {
        MCCBatToggleSleepEvent mccBatToggleSleepEvent = new MCCBatToggleSleepEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                event.isAwake()
        );

        if (mccBatToggleSleepEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ExpBottleEvent event) {
        MCCExpBottleEvent mccExpBottleEvent = new MCCExpBottleEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHitEntity()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHitBlock()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHitBlockFace()),
                event.isCancelled(),
                event.getExperience(),
                event.getShowEffect()
        );

        if (mccExpBottleEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ExplosionPrimeEvent event) {
        MCCExplosionPrimeEvent mccExplosionPrimeEvent = new MCCExplosionPrimeEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                event.getRadius(),
                event.getFire()
        );

        if (mccExplosionPrimeEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(FireworkExplodeEvent event) {
        MCCFireworkExplodeEvent mccFireworkExplodeEvent = new MCCFireworkExplodeEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled()
        );

        if (mccFireworkExplodeEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(HorseJumpEvent event) {
        MCCHorseJumpEvent mccHorseJumpEvent = new MCCHorseJumpEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                event.getPower()
        );

        if (mccHorseJumpEvent.callEvent()) ReflectionUtils.writeFieldInClass(event, "cancelled", true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PigZombieAngerEvent event) {
        MCCPigZombieAngerEvent mccPigZombieAngerEvent = new MCCPigZombieAngerEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getTarget()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getNewAnger())
        );

        if (mccPigZombieAngerEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerLeashEntityEvent event) {
        MCCPlayerLeashEntityEvent mccPlayerLeashEntityEvent = new MCCPlayerLeashEntityEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getLeashHolder()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHand())
        );

        if (mccPlayerLeashEntityEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PotionSplashEvent event) {
        MCCPotionSplashEvent mccPotionSplashEvent = new MCCPotionSplashEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHitEntity()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHitBlock()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getHitBlockFace()),
                event.isCancelled(),
                event.isCancelled(), // duplicate
                MCCPlatform.getInstance().getConversionService().wrap(event.getAffectedEntities())
        );

        if (mccPotionSplashEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(SheepRegrowWoolEvent event) {
        MCCSheepRegrowWoolEvent mccSheepRegrowWoolEvent = new MCCSheepRegrowWoolEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled()
        );

        if (mccSheepRegrowWoolEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(SlimeSplitEvent event) {
        MCCSlimeSplitEvent mccSlimeSplitEvent = new MCCSlimeSplitEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isCancelled(),
                event.getCount()
        );

        if (mccSlimeSplitEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(StriderTemperatureChangeEvent event) {
        MCCStriderTemperatureChangeEvent mccStriderTemperatureChangeEvent = new MCCStriderTemperatureChangeEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity()),
                event.isShivering(),
                event.isCancelled()
        );

        if (mccStriderTemperatureChangeEvent.callEvent()) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(HangingEvent event) {
        MCCHangingEvent mccHangingEvent = new MCCHangingEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getEntity())
        );

        mccHangingEvent.callEvent();
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(FurnaceExtractEvent event) {
        MCCFurnaceExtractEvent mccFurnaceExtractEvent = new MCCFurnaceExtractEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getBlock()),
                event.getExpToDrop(),
                MCCPlatform.getInstance().getConversionService().wrap(event.getPlayer()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getItemType()),
                event.getItemAmount()
        );

        mccFurnaceExtractEvent.callEvent();
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(WeatherEvent event) {
        MCCWeatherEvent mccWeatherEvent = new MCCWeatherEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getWorld())
        );

        mccWeatherEvent.callEvent();
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(SpawnChangeEvent event) {
        MCCSpawnChangeEvent mccSpawnChangeEvent = new MCCSpawnChangeEvent(
                MCCPlatform.getInstance().getConversionService().wrap(event.getWorld()),
                MCCPlatform.getInstance().getConversionService().wrap(event.getPreviousLocation())
        );

        mccSpawnChangeEvent.callEvent();
    }
}
