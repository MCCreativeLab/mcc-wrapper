package de.verdox.mccreativelab.impl.paper.custom;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CustomItemListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void postHurtEnemy(EntityDamageByEntityEvent e) {
        var bukkitAttacker = e.getDamager();
        var bukkitEnemy = e.getEntity();

        if (!(bukkitAttacker instanceof LivingEntity livingAttacker)) {
            return;
        }
        if (!(bukkitEnemy instanceof LivingEntity)) {
            return;
        }

        var bukkitWeapon = livingAttacker.getActiveItem();

    }
}
