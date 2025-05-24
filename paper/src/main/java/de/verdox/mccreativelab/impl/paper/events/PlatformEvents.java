package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.event.block.MCCFluidLevelChangeEvent;
import de.verdox.mccreativelab.wrapper.event.block.MCCLeavesDecayEvent;
import de.verdox.mccreativelab.wrapper.event.block.MCCSculkBloomEvent;
import de.verdox.mccreativelab.wrapper.event.entity.*;
import de.verdox.mccreativelab.wrapper.event.inventory.MCCFurnaceExtractEvent;
import de.verdox.mccreativelab.wrapper.event.world.MCCSpawnChangeEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.FluidLevelChangeEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SculkBloomEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                BukkitAdapter.toMcc(event.getBlock()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getNewData())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(LeavesDecayEvent event) {
        callEvent(event, new MCCLeavesDecayEvent(
                BukkitAdapter.toMcc(event.getBlock()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(SculkBloomEvent event) {
        callEvent(event, new MCCSculkBloomEvent(
                BukkitAdapter.toMcc(event.getBlock()),
                event.isCancelled(),
                event.getCharge()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(AreaEffectCloudApplyEvent event) {
        callEvent(event, new MCCAreaEffectCloudApplyEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                BukkitAdapter.toMcc(event.getAffectedEntities(), BukkitAdapter::toMcc, ArrayList::new),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ArrowBodyCountChangeEvent event) {
        callEvent(event, new MCCArrowBodyCountChangeEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                event.isReset(),
                event.getOldAmount(),
                event.getNewAmount()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(BatToggleSleepEvent event) {
        callEvent(event, new MCCBatToggleSleepEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                event.isAwake()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ExpBottleEvent event) {
        callEvent(event, new MCCExpBottleEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                BukkitAdapter.toMcc(event.getHitEntity()),
                BukkitAdapter.toMcc(event.getHitBlock()),
                BukkitAdapter.toMcc(event.getHitBlockFace()),
                event.isCancelled(),
                event.getExperience(),
                event.getShowEffect()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(ExplosionPrimeEvent event) {
        callEvent(event, new MCCExplosionPrimeEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                event.getRadius(),
                event.getFire()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(FireworkExplodeEvent event) {
        callEvent(event, new MCCFireworkExplodeEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(HorseJumpEvent event) {
        callEvent(event, new MCCHorseJumpEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                event.getPower()
        ));

        // Why reflection?
        /*if (mccHorseJumpEvent.callEvent()) ReflectionUtils.writeFieldInClass(event, "cancelled", true);*/
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PigZombieAngerEvent event) {
        callEvent(event, new MCCPigZombieAngerEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getTarget()),
                event.getNewAnger()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PlayerLeashEntityEvent event) {
        callEvent(event, new MCCPlayerLeashEntityEvent(
                BukkitAdapter.toMcc(event.getLeashHolder()),
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getHand())
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(PotionSplashEvent event) {
        Map<MCCEntity, Double> affectedEntities = new HashMap<>();
        for (LivingEntity affectedEntity : event.getAffectedEntities()) {
            affectedEntities.put(BukkitAdapter.toMcc(affectedEntity), event.getIntensity(affectedEntity));
        }
        callEvent(event, new MCCPotionSplashEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                BukkitAdapter.toMcc(event.getHitEntity()),
                BukkitAdapter.toMcc(event.getHitBlock()),
                BukkitAdapter.toMcc(event.getHitBlockFace()),
                event.isCancelled(),
                event.isCancelled(), // duplicate
                affectedEntities
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(SheepRegrowWoolEvent event) {
        callEvent(event, new MCCSheepRegrowWoolEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(SlimeSplitEvent event) {
        callEvent(event, new MCCSlimeSplitEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isCancelled(),
                event.getCount()
        ));
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(StriderTemperatureChangeEvent event) {
        callEvent(event, new MCCStriderTemperatureChangeEvent(
                BukkitAdapter.toMcc(event.getEntity()),
                event.isShivering(),
                event.isCancelled()
        ));
    }

    //TODO Has no Handler -> Reference child events
/*    @EventHandler(ignoreCancelled = true)
    public void handle(HangingEvent event) {
        callEvent(event, new MCCHangingEvent(
                BukkitAdapter.toMcc(event.getEntity())
        ));
    }*/

    @EventHandler(ignoreCancelled = true)
    public void handle(FurnaceExtractEvent event) {
        callEvent(event, new MCCFurnaceExtractEvent(
                BukkitAdapter.toMcc(event.getBlock()),
                event.getExpToDrop(),
                BukkitAdapter.toMcc(event.getPlayer()),
                BukkitAdapter.toMcc(event.getItemType().asItemType()),
                event.getItemAmount()
        ));
    }

    //TODO Has no Handler -> Reference child events
/*
    @EventHandler(ignoreCancelled = true)
    public void handle(WeatherEvent event) {
        callEvent(event, new MCCWeatherEvent(
                BukkitAdapter.toMcc(event.getWorld())
        ));
    }
*/

    @EventHandler(ignoreCancelled = true)
    public void handle(SpawnChangeEvent event) {
        callEvent(event, new MCCSpawnChangeEvent(
                BukkitAdapter.toMcc(event.getWorld()),
                BukkitAdapter.toMcc(event.getPreviousLocation())
        ));
    }
}
