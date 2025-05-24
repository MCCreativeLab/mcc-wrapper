package de.verdox.mccreativelab.impl.paper.gamefactory;

import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.ItemStack;

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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void fixItemRepairRecipesNotWorkingWithCustomModelData(PrepareItemCraftEvent e){
        if(!(e.getRecipe() instanceof ComplexRecipe complexRecipe) || !e.isRepair())
            return;

        MCCItemType repairedType = null;
        for (ItemStack matrix : e.getInventory().getMatrix()) {
            if(matrix == null) {
                continue;
            }
            MCCItemStack stack = BukkitAdapter.toMcc(matrix);
            if(repairedType == null) {
                repairedType = stack.getType();
            }
            else {
                if(!repairedType.isSame(BukkitAdapter.toMcc(matrix))) {
                    break;
                }
            }
        }
    }
}
