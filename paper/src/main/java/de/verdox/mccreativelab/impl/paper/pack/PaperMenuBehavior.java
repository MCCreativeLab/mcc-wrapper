package de.verdox.mccreativelab.impl.paper.pack;

import de.verdox.mccreativelab.generator.resourcepack.types.menu.ActiveMenu;
import de.verdox.mccreativelab.generator.resourcepack.types.menu.MenuBehaviour;
import de.verdox.mccreativelab.generator.resourcepack.types.menu.PlayerKeyInput;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.function.BiConsumer;

public class PaperMenuBehavior extends MenuBehaviour implements Listener {
    public PaperMenuBehavior(MCCPlayer player, ActiveMenu activeMenu, BiConsumer<PlayerKeyInput, ActiveMenu> consumer, Runnable onEnd) {
        super(player, activeMenu, consumer, onEnd);
    }

    @Override
    public void close() {
        super.close();
        HandlerList.unregisterAll(this);
    }

    @Override
    protected void capturePlayerInSpectatorMode(MCCPlayer mccPlayer) {
        //TODO:
    }

    @Override
    protected void freePlayerFromSpectatorMode(MCCPlayer mccPlayer) {
        //TODO:
    }

    //TODO: SwapItemEvent -> call handleHotBarScrolling
    //TODO: Player Input Event -> call handleInput
}
