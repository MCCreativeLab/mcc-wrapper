package de.verdox.mccreativelab.impl.paper.pack;

import de.verdox.mccreativelab.generator.resourcepack.CustomResourcePack;
import de.verdox.mccreativelab.generator.resourcepack.types.gui.ActiveGUI;
import de.verdox.mccreativelab.generator.resourcepack.types.gui.GUIFrontEndBehavior;
import de.verdox.mccreativelab.generator.resourcepack.types.menu.ActiveMenu;
import de.verdox.mccreativelab.generator.resourcepack.types.menu.MenuBehaviour;
import de.verdox.mccreativelab.generator.resourcepack.types.menu.PlayerKeyInput;
import de.verdox.mccreativelab.impl.vanilla.pack.VanillaGeneratorHelper;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class PaperGeneratorHelper extends VanillaGeneratorHelper {

    private JavaPlugin javaPlugin;

    public PaperGeneratorHelper(CustomResourcePack customResourcePack) {
        super(customResourcePack);
    }

    public void setJavaPlugin(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public @NotNull GUIFrontEndBehavior createFrondEndBehavior(ActiveGUI activeGUI) {
        return new PaperGUIFrontEndBehavior(javaPlugin, activeGUI);
    }

    @Override
    public @NotNull MenuBehaviour createMenuBehavior(MCCPlayer mccPlayer, ActiveMenu activeMenu, BiConsumer<PlayerKeyInput, ActiveMenu> consumer, Runnable onEnd) {
        return new PaperMenuBehavior(mccPlayer, activeMenu, consumer, onEnd);
    }
}
