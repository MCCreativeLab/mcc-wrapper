package de.verdox.mccreativelab.impl.paper.component.entity;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.impl.paper.plugin.MCCPaperPlatformPlugin;
import de.verdox.mccreativelab.wrapper.component.AbstractComponent;
import de.verdox.mccreativelab.wrapper.component.entity.MCCEntityHiding;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import net.minecraft.world.entity.player.Player;

public class PaperEntityHiding extends AbstractComponent<Player, MCCPlayer> implements MCCEntityHiding {
    public PaperEntityHiding(MCCPlayer player) {
        super(player, new TypeToken<>() {});
    }

    @Override
    public void hide(MCCEntity other) {
        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) BukkitAdapter.toBukkit(getOwner());
        var otherBukkitEntity = BukkitAdapter.toBukkit(other);
        bukkitPlayer.hideEntity(MCCPaperPlatformPlugin.getInstance(), otherBukkitEntity);
    }

    @Override
    public void show(MCCEntity other) {
        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) BukkitAdapter.toBukkit(getOwner());
        var otherBukkitEntity = BukkitAdapter.toBukkit(other);
        bukkitPlayer.showEntity(MCCPaperPlatformPlugin.getInstance(), otherBukkitEntity);
    }

    @Override
    public boolean canSee(MCCEntity other) {
        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) BukkitAdapter.toBukkit(getOwner());
        var otherBukkitEntity = BukkitAdapter.toBukkit(other);
        return bukkitPlayer.canSee(otherBukkitEntity);
    }
}
