package de.verdox.mccreativelab.impl.vanilla.pack;

import de.verdox.mccreativelab.generator.resourcepack.CustomResourcePack;
import de.verdox.mccreativelab.generator.resourcepack.types.gui.ActiveGUI;
import de.verdox.mccreativelab.generator.resourcepack.types.gui.GUIFrontEndBehavior;
import de.verdox.mccreativelab.generator.resourcepack.types.hud.renderer.HudRenderer;
import de.verdox.mccreativelab.generator.resourcepack.types.hud.renderer.HudRendererImpl;
import de.verdox.mccreativelab.generator.resourcepack.types.menu.ActiveMenu;
import de.verdox.mccreativelab.generator.resourcepack.types.menu.MenuBehaviour;
import de.verdox.mccreativelab.generator.resourcepack.types.menu.PlayerKeyInput;
import de.verdox.mccreativelab.platform.GeneratorPlatformHelper;
import de.verdox.mccreativelab.wrapper.annotations.MCCLogic;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.exceptions.OperationNotPossibleOnNMS;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public class VanillaGeneratorHelper implements GeneratorPlatformHelper {
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '§' + "[0-9A-FK-ORX]");

    private final HudRendererImpl hudRenderer = new HudRendererImpl();
    private final CustomResourcePack customResourcePack;

    public VanillaGeneratorHelper(CustomResourcePack customResourcePack) {
        this.customResourcePack = customResourcePack;
        this.hudRenderer.start();
    }

    @Override
    public @NotNull String stripOldColorCodes(@NotNull String input) {
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    @Override
    public @NotNull HudRenderer getHudRenderer() {
        return hudRenderer;
    }

    @Override
    public @Nullable UUID getUUIDOfAudience(Audience audience) {
        if (audience instanceof MCCEntity mccEntity)
            return mccEntity.getUUID();
        else if(audience instanceof MCCWorld world)
            return world.getUUID();
        return null;
    }

    @Override
    public @Nullable CustomResourcePack getPlatformResourcePack() {
        return customResourcePack;
    }

    @Override
    @MCCLogic
    public @NotNull GUIFrontEndBehavior createFrondEndBehavior(ActiveGUI activeGUI) {
        throw new OperationNotPossibleOnNMS("This logic is not implemented on the vanilla platform yet.");
    }

    @Override
    @MCCLogic
    public @NotNull MenuBehaviour createMenuBehavior(MCCPlayer mccPlayer, ActiveMenu activeMenu, BiConsumer<PlayerKeyInput, ActiveMenu> biConsumer, Runnable runnable) {
        //TODO
        throw new OperationNotPossibleOnNMS("This logic is not implemented on the vanilla platform yet.");
    }
}
